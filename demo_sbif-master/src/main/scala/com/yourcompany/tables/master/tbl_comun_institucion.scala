package com.yourcompany.tables.master

import com.huemulsolutions.bigdata.common._
import com.huemulsolutions.bigdata.tables._
import com.huemulsolutions.bigdata.control._
import org.apache.spark.sql.types.StringType

/**
 * Contiene información de instituciones financieras de Chile"
 */
class tbl_comun_institucion(huemulBigDataGov: huemul_BigDataGovernance, Control: huemul_Control) 
        extends huemul_Table(huemulBigDataGov, Control) with Serializable{
   
  /**********   C O L U M N A S   ****************************************/
  val ins_id: huemul_Columns = new huemul_Columns(StringType,true,"Código de institución entregado por SBIF")
                    .setIsPK().setDQ_MinLen(3,"ERR_01").setDQ_MaxLen(3,"ERR_01")
  val ins_nombre: huemul_Columns = new huemul_Columns(StringType,true,"Nombre de la institución entregada por la SBIF")
                    .setDQ_MinLen (5,"ERR_02").setDQ_MaxLen(100,"ERR_02")
  
                    
  /**********   C O N F I G U R A C I O N   D E   L A   T A B L A   ****************************************/
  //Tipo de tabla, Master y Reference son catálogos sin particiones de periodo
  this.setTableType(huemulType_Tables.Reference)
  //Base de Datos en HIVE donde será creada la tabla
  this.setDataBase(huemulBigDataGov.GlobalSettings.MASTER_DataBase)
  //Tipo de archivo que será almacenado en HDFS
  this.setStorageType(huemulType_StorageType.PARQUET)
  //Ruta en HDFS donde se guardará el archivo PARQUET
  this.setGlobalPaths(huemulBigDataGov.GlobalSettings.MASTER_SmallFiles_Path)
  //Ruta en HDFS específica para esta tabla (Globalpaths / localPath)
  this.setLocalPath("sbif/")
  //Frecuencia de actualización
  this.setFrequency(huemulType_Frequency.ANY_MOMENT)
  //nuevo desde version 2.0
  //permite guardar versiones de los datos antes de que se vuelvan a ejecutar los procesos (solo para tablas de tipo master y reference)
  this.setSaveBackup(true)
  //nuevo desde versión 2.1
  //permite asignar un código de error personalizado al fallar la PK
  this.setPK_externalCode("COD_ERROR")
  
  /**********   S E T E O   O P T I M I Z A C I O N   ****************************************/
  //nuevo desde version 2.0
  //indica la cantidad de archivos que generará al guardar el fichero (1 para archivos pequeños
  //de esta forma se evita el problema de los archivos pequeños en HDFS
  this.setNumPartitions(1)
  
  /**********   S E T E O   I N F O R M A T I V O   ****************************************/
  //Nombre del contacto de negocio
  this.setBusiness_ResponsibleName("Nombre del usuario de negocio: Ej. Juan Perez")
  //Nombre del contacto de TI
  this.setIT_ResponsibleName("Nombre del responsable de TI: Ej. Pedro Segura")
  //Descripción de la tabla
  this.setDescription("Contiene información de instituciones de la información de EERR")
  
  
  //**********Atributos adicionales de DataQuality 
  /*
            .setIsPK()         //por default los campos no son PK
            .setIsUnique("COD_ERROR") //por default los campos pueden repetir sus valores
            .setNullable() //por default los campos no permiten nulos
            .setDQ_MinDecimalValue(Decimal.apply(0),"COD_ERROR")
            .setDQ_MaxDecimalValue(Decimal.apply(200.0),"COD_ERROR")
            .setDQ_MinDateTimeValue("2018-01-01","COD_ERROR")
            .setDQ_MaxDateTimeValue("2018-12-31","COD_ERROR")
            .setDQ_MinLen(5,"COD_ERROR")
            .setDQ_MaxLen(100,"COD_ERROR")
  */
  //**********Otros atributos
  /*
            .setDefaultValues("'string'") // "10" // "'2018-01-01'"
  				  .encryptedType("tipo")
  */
  
  //**********Ejemplo para agregar reglas de DataQuality Avanzadas  -->ColumnXX puede ser null si la validacion es a nivel de tabla
  //**************Parametros
  //********************  ColumnXXColumna a la cual se aplica la validacion, si es a nivel de tabla poner null
  //********************  Descripcion de la validacion, ejemplo: "Consistencia: Campo1 debe ser mayor que campo 2"
  //********************  Formula SQL En Positivo, ejemplo1: campo1 > campo2  ;ejemplo2: sum(campo1) > sum(campo2)  
  //********************  CodigoError: Puedes especificar un codigo para la captura posterior de errores, es un numero entre 1 y 999
  //********************  QueryLevel es opcional, por default es "row" y se aplica al ejemplo1 de la formula, para el ejmplo2 se debe indicar "Aggregate"
  //********************  Notification es opcional, por default es "error", y ante la aparicion del error el programa falla, si lo cambias a "warning" y la validacion falla, el programa sigue y solo sera notificado
  //********************  SaveErrorDetails: es opcional, true para guardar el detalle de filas que no cumplen con DQ. valor por default es true
  //********************  DQ_ExternalCode: es opcional, indica el código de DataQuality externo para enlazar con herramientas de gobierno
  //val DQ_NombreRegla: huemul_DataQuality = new huemul_DataQuality(columnaXX,"Descripcion de la validacion", "Campo_1 > Campo_2",1)
  //          .setQueryLevel(huemulType_DQQueryLevel.Row)
  //          .setNotification(huemulType_DQNotification.WARNING_EXCLUDE)
  //          .setSaveErrorDetails(true)
  //**************Adicionalmente, puedes agregar "tolerancia" a la validacion, es decir, puedes especiicar 
  //************** numFilas = 10 para permitir 10 errores (al 11 se cae)
  //************** porcentaje = 0.2 para permitir una tolerancia del 20% de errores
  //************** ambos parametros son independientes (condicion o), cualquiera de las dos tolerancias que no se cumpla se gatilla el error o warning
  //DQ_NombreRegla.setTolerance(numfilas, porcentaje)
  //DQ_NombreRegla.setDQ_ExternalCode("COD_ERROR")
  
 
  //Aplica las definiciones
  this.ApplyTableDefinition()
}