# demo_sbif
Proyecto ejemplo para la utilización de Huemul BigDataGovernance version 2.6


Los datos del sistema financiero permiten visualizar el estado de resultados y balance de los chilenos a cierta fecha. Este ejemplo permite cargar estos archivos al cluster BigData, estructurarlos y dejarlos disponibles en tablas sobre Hive.

Para ejecutar este ejemplo, se han creado tres ficheros que contienen todo lo necesario, se explican a continuación:

* **demo-sbif.zip:** contiene los datos del proyecto, y archivo ejemplo de configuración para connectionString del modelo de control. Los datos que contiene son los siguientes:
   * NEGOCIO_201806: Datos de negocios identificados para esta demo
   * PRODUCTO_201806: Datos de productos permitidos para esta demo
   * PLANCUENTA_GESTION_001: Asignación de productos y negocios a las cuentas contables.
   * 201806: Carpeta que contiene los datos del sistema financiero para junio del 2018
   * 201807: Carpeta que contiene los datos del sistema financiero para julio del 2018
* **huemul_install_sbif.sh:** Shell para crear directorios en HDFS y copiar archivo RAW.
* **huemul_drivers.zip:** Drivers utilizados por Huemul, contiene lo siguiente:
   * huemul-bigdatagovernance-2.6.jar: Framework Huemul BigData 
   * huemul-sql-decode-1.0.jar: Componente de Huemul para obtener traza 
   * postgresql-9.4.1212.jar: Driver para conexión con modelo de control implementado en PostgreSQL
   * ojdbc7.jar: Driver para conexión con modelo de control implementado en Oracle
   * mysql-connector-java-5.1.48.jar: Driver para conexión con modelo de control implementado en MySQL
   * mssql-jdbc-7.2.1.jre8.jar: Driver para conexión con modelo de control implementado en SQL Server.

Los ficheros pueden ser descargados desde el siguiente link:
www.huemulsolutions.com/ /ejemplos/sbif_26/demo-sbif.zip
www.huemulsolutions.com/ /ejemplos/sbif_26/huemul_install_sbif.sh
www.huemulsolutions.com/ /ejemplos/sbif_26/huemul-drivers.zip


## Ejecución process_mensual   
Se ejecuta con el siguiente comando:

```shell
spark-submit --master local --jars huemul-bigdatagovernance-2.6.jar,huemul-sql-decode-1.0.jar,demo_settings-2.3.jar,demo_catalogo-2.6.jar,postgresql-9.4.1212.jar --class com.yourcompany.sbif.process_mensual  demo_sbif-2.6.jar environment=production,ano=2018,mes=6
```
