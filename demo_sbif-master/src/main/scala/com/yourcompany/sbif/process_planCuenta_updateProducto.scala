package com.yourcompany.sbif

import com.huemulsolutions.bigdata.common._
import com.huemulsolutions.bigdata.control._
import com.yourcompany.settings._
import com.yourcompany.tables.master._
import com.yourcompany.sbif.datalake.raw_planCuentaAsigna

object process_planCuenta_updateProducto {
  def main(args: Array[String]): Unit = {
    val huemulBigDataGov  = new huemul_BigDataGovernance(s"Masterizacion tabla tbl_comun_institucion - ${this.getClass.getSimpleName}", args, globalSettings.Global)

    /** ************* PARAMETROS **********************/
    val param_ano = huemulBigDataGov.arguments.GetValue("ano", null, "Debe especificar el parametro año, ej: ano=2017").toInt
    val param_mes = huemulBigDataGov.arguments.GetValue("mes", null, "Debe especificar el parametro mes, ej: mes=12").toInt
    val param_numArchivo = huemulBigDataGov.arguments.GetValue("numArchivo", null, "Debe especificar el parametro numArchivo, ej: numArchivo=001")
    
    val Control = new huemul_Control(huemulBigDataGov, null, huemulType_Frequency.ANY_MOMENT)    
    
    try {             
      /*************** AGREGAR PARAMETROS A CONTROL **********************/
      Control.AddParamYear("param_ano", param_ano)
      Control.AddParamMonth("param_mes", param_mes)
      Control.AddParamInformation("param_numArchivo", param_numArchivo)


      /** ************* ABRE RAW DESDE DATALAKE **********************/
      val DF_RAW = new raw_planCuentaAsigna(huemulBigDataGov, Control)
      if (!DF_RAW.open("DF_RAW", Control, param_ano, param_mes, 0, 0, 0, 0, param_numArchivo))       
        Control.RaiseError(s"error encontrado, abortar: ${DF_RAW.Error.ControlError_Message}")
      
      DF_RAW.DataFramehuemul.DataFrame.show()
      
      /*********************************************************/
      /*************** LOGICAS DE NEGOCIO **********************/
      /*********************************************************/
      //instancia de clase tbl_comun_institucion 
      val huemulTable = new tbl_sbif_planCuenta(huemulBigDataGov, Control)
      
      Control.NewStep("Generar Logica de Negocio")
      huemulTable.DF_from_RAW(DF_RAW, "DF_DatosActualiza")
      
      Control.NewStep("Asocia columnas de la tabla con nombres de campos de SQL")
      huemulTable.planCuenta_id.setMapping("planCuenta_id")
      huemulTable.negocio_id.setMapping("negocio_id")
      huemulTable.producto_id.setMapping("producto_id")
      huemulTable.planCuenta_Concepto.setMapping("concepto")
      
      Control.NewStep("Ejecuta Proceso")    
      if (!huemulTable.executeSelectiveUpdate("FinalSaved",null))
        Control.RaiseError(s"User: Error al intentar masterizar instituciones (${huemulTable.Error_Code}): ${huemulTable.Error_Text}")
      
      DF_RAW.DataFramehuemul.DataFrame.unpersist()
      Control.FinishProcessOK
    } catch {
      case e: Exception =>
        Control.Control_Error.GetError(e, this.getClass.getName, null)
        Control.FinishProcessError()

    }
    
    huemulBigDataGov.close()
  }
}