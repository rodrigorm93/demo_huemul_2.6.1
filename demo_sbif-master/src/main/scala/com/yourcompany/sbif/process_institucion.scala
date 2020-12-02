package com.yourcompany.sbif

import com.yourcompany.sbif.datalake._
import com.yourcompany.tables.master._
import com.huemulsolutions.bigdata.common._
import com.huemulsolutions.bigdata.control._
import java.util.Calendar
import com.yourcompany.settings._

object process_institucion extends Serializable {
    /**
   * Este cÃ³digo se ejecuta cuando se llama el JAR desde spark2-submit. el cÃ³digo estÃ¡ preparado para hacer reprocesamiento masivo.
  */
  def main(args : Array[String]) {
    //CreaciÃ³n API
    val huemulBigDataGov  = new huemul_BigDataGovernance(s"Carga tabla tbl_institucion - ${this.getClass.getSimpleName}", args, globalSettings.Global)
    
    /*************** PARAMETROS **********************/
    var param_ano = huemulBigDataGov.arguments.GetValue("ano", null, "Debe especificar el parÃ¡metro aÃ±o, ej: ano=2017").toInt
    var param_mes = huemulBigDataGov.arguments.GetValue("mes", null, "Debe especificar el parÃ¡metro mes, ej: mes=12").toInt
    val param_numMeses = huemulBigDataGov.arguments.GetValue("num_meses", "1").toInt
    
    
    /*************** CICLO REPROCESO MASIVO **********************/
    var i: Int = 1
    val Fecha = huemulBigDataGov.setDateTime(param_ano, param_mes, 1, 0, 0, 0)
    
    while (i <= param_numMeses) {
      param_ano = huemulBigDataGov.getYear(Fecha)
      param_mes = huemulBigDataGov.getMonth(Fecha)
      println(s"Procesando AÃ±o $param_ano, Mes $param_mes ($i de $param_numMeses)")

      //Ejecuta cÃ³digo
      val FinOK = procesa_master(huemulBigDataGov, null, param_ano, param_mes)
      
      if (FinOK)
        i+=1
      else
        i = param_numMeses + 1
        
      Fecha.add(Calendar.MONTH, 1)      
    }
    
    huemulBigDataGov.close()

  }
  
  /**
    masterización de archivo instituciones_mes <br>
    param_ano: aÃ±o de los datos  <br>
    param_mes: mes de los datos  <br>
   */
  def procesa_master(huemulBigDataGov: huemul_BigDataGovernance, ControlParent: huemul_Control, param_ano: Integer, param_mes: Integer): Boolean = {
    val Control = new huemul_Control(huemulBigDataGov, ControlParent, huemulType_Frequency.MONTHLY)    
    
    try {             
      /*************** AGREGAR PARAMETROS A CONTROL **********************/
      Control.AddParamYear("param_ano", param_ano)
      Control.AddParamMonth("param_mes", param_mes)
      
      Control.NewStep("Abre DataLake")
      val DF_RAW = new raw_institucion_mes(huemulBigDataGov, Control)
      if (!DF_RAW.open("DF_RAW", Control, param_ano, param_mes, 0, 0, 0, 0))       
        Control.RaiseError(s"error encontrado, abortar: ${DF_RAW.Error.ControlError_Message}")
      
      DF_RAW.DataFramehuemul.DataFrame.show()
      /*********************************************************/
      /*************** LOGICAS DE NEGOCIO **********************/
      /*********************************************************/
      //instancia de clase institucion_mes_test 
      val MasterTable = new tbl_comun_institucion(huemulBigDataGov, Control)
      
      Control.NewStep("Generar Lógica de Negocio")
      MasterTable.DF_from_SQL("FinalRAW"
                          , s"""SELECT institucion_id
                                      ,institucion_Nombre
                               FROM DF_RAW""")
      
      //Quita persistencia de RAW Data
      DF_RAW.DataFramehuemul.DataFrame.unpersist()
      
      Control.NewStep("Asocia columnas de la tabla con nombres de campos de SQL")
      MasterTable.ins_id.setMapping("institucion_id")
      MasterTable.ins_nombre.setMapping("institucion_Nombre")
      //Si los campos SQL se llamaran igual a las columnas de la tabla, podría asignar automáticamente
      //con el méetodo MasterTable.setMappingAuto()
                  
      Control.NewStep("Ejecuta Proceso")      
      if (!MasterTable.executeFull("FinalSaved")) 
        Control.RaiseError(s"User: Error al intentar masterizar instituciones (${MasterTable.Error_Code}): ${MasterTable.Error_Text}")
      
      Control.FinishProcessOK
    } catch {
      case e: Exception =>
        Control.Control_Error.GetError(e, this.getClass.getName, null)
        Control.FinishProcessError()

    }
    
    Control.Control_Error.IsOK()
  }
}