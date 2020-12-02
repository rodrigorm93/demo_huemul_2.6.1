package com.yourcompany.tables.master

/*
instrucciones (parte a):
   1. Crear una clase en el packete que contiene las tablas (com.yourcompany.tables.master) con el nombre "tbl_comun_planCuenta"
   2. copiar el codigo desde estas instrucciones hasta ***    M A S T E R   P R O C E S S     *** y pegarlo en "tbl_comun_planCuenta"
   3. Revisar detalladamente la configuracion de la tabla
      3.1 busque el texto "[[LLENAR ESTE CAMPO]]" y reemplace la descripcion segun corresponda 
   4. seguir las instrucciones "parte b"
*/

import com.huemulsolutions.bigdata.common._
import com.huemulsolutions.bigdata.control._
import com.huemulsolutions.bigdata.tables._
import com.huemulsolutions.bigdata.dataquality._
import org.apache.spark.sql.types._


class tbl_sbif_planCuenta(huemulBigDataGov: huemul_BigDataGovernance, Control: huemul_Control) 
                  extends huemul_Table(huemulBigDataGov, Control) with Serializable {
  /**********   C O L U M N A S   ****************************************/
  val planCuenta_id: huemul_Columns = new huemul_Columns (StringType, true, "código del plan de cuentas")
            .setIsPK().setDQ_MinLen(7,"ERROR_01").setDQ_MaxLen(7,"ERROR_01")  
  
  val planCuenta_Nombre: huemul_Columns = new huemul_Columns (StringType, true, "Nombre de la cuenta")
            .setMDM_EnableOldValue().setMDM_EnableDTLog().setMDM_EnableProcessLog()  
            .setDQ_MinLen(5,"ERROR_02").setDQ_MaxLen(100,"ERROR_02")  
  
  val negocio_id: huemul_Columns = new huemul_Columns (StringType, false, "Asignación del negocio de la cuenta cuentable")
            .setMDM_EnableOldValue().setMDM_EnableDTLog().setMDM_EnableProcessLog().setNullable() 
  
  val producto_id: huemul_Columns = new huemul_Columns (StringType, false, "Asignación del Producdto de la cuenta cuentable")
            .setMDM_EnableOldValue().setMDM_EnableDTLog().setMDM_EnableProcessLog().setNullable() 
  
  val planCuenta_Concepto: huemul_Columns = new huemul_Columns (StringType, false, "Indica el tipo de concepto que calcula (colocacion, provision, etc)")
            .setMDM_EnableOldValue().setMDM_EnableDTLog().setMDM_EnableProcessLog().setNullable() 


  /**********   I N T E G R I D A D   R E F E R E N C I A L   ****************************************/
  val itbl_comun_negocio = new tbl_comun_negocio(huemulBigDataGov,Control)
  val fktbl_comun_negocio: huemul_Table_Relationship = new huemul_Table_Relationship(itbl_comun_negocio, true)
            .setExternalCode("ERROR_FK_NEGOCIO").setNotification(huemulType_DQNotification.WARNING_EXCLUDE)
  fktbl_comun_negocio.AddRelationship(itbl_comun_negocio.negocio_id, negocio_id)
  
  val itbl_comun_producto = new tbl_comun_producto(huemulBigDataGov,Control)
  val fktbl_comun_producto: huemul_Table_Relationship = new huemul_Table_Relationship(itbl_comun_producto, true)
            .setExternalCode("ERROR_FK_PRODUCTO").setNotification(huemulType_DQNotification.ERROR)
  fktbl_comun_producto.AddRelationship(itbl_comun_producto.producto_id, producto_id)
  
  
  /**********   D A T A   Q U A L I T Y   ****************************************/
  val DQ_CuentaCorta: huemul_DataQuality = new huemul_DataQuality(planCuenta_Nombre,"Largo del texto entre 5 y 10"
              , "length(planCuenta_Nombre) <= 10",1).setNotification(huemulType_DQNotification.WARNING)
  
  /**********   C O N F I G U R A C I O N   D E   L A   T A B L A   ****************************************/
  //Tipo de tabla, Master y Reference son catalogos sin particiones de periodo
  this.setTableType(huemulType_Tables.Reference)
  //Base de Datos en HIVE donde sera creada la tabla
  this.setDataBase(huemulBigDataGov.GlobalSettings.MASTER_DataBase)
  //Tipo de archivo que sera almacenado en HDFS
  this.setStorageType(huemulType_StorageType.PARQUET)
  //Ruta en HDFS donde se guardara el archivo PARQUET
  this.setGlobalPaths(huemulBigDataGov.GlobalSettings.MASTER_SmallFiles_Path)
  //Ruta en HDFS especifica para esta tabla (Globalpaths / localPath)
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
  //Nombre del contacto de TI
  this.setDescription("[[LLENAR ESTE CAMPO]]")
  //Nombre del contacto de negocio
  this.setBusiness_ResponsibleName("[[LLENAR ESTE CAMPO]]")
  //Nombre del contacto de TI
  this.setIT_ResponsibleName("[[LLENAR ESTE CAMPO]]")
   
  /**********   D A T A   Q U A L I T Y   ****************************************/
  //DataQuality: maximo numero de filas o porcentaje permitido, dejar comentado o null en caso de no aplicar
  //this.setDQ_MaxNewRecords_Num(null)  //ej: 1000 para permitir maximo 1.000 registros nuevos cada vez que se intenta insertar
  //this.setDQ_MaxNewRecords_Perc(null) //ej: 0.2 para limitar al 20% de filas nuevas
    
  /**********   S E G U R I D A D   ****************************************/
  //Solo estos package y clases pueden ejecutar en modo full, si no se especifica todos pueden invocar
  this.WhoCanRun_executeFull_addAccess("process_planCuenta", "com.yourcompany.sbif")
  //Solo estos package y clases pueden ejecutar en modo solo Insert, si no se especifica todos pueden invocar
  //this.WhoCanRun_executeOnlyInsert_addAccess("[[MyclassName]]", "[[my.package.path]]")
  //Solo estos package y clases pueden ejecutar en modo solo Update, si no se especifica todos pueden invocar
  //this.WhoCanRun_executeOnlyUpdate_addAccess("[[MyclassName]]", "[[my.package.path]]")
  

  

  //**********Atributos adicionales de DataQuality 
  /*
            .setIsPK("COD_ERROR")         //por default los campos no son PK
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
    
  //**********Ejemplo para aplicar DataQuality de Integridad Referencial
  
 
  
    
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
    
  this.ApplyTableDefinition()
}

