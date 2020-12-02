# Demo Global Settings
Este proyecto contiene todas las rutas y cadenas de conexión utilizadas por los proyectos Demo de Huemul.

## Codificar sin dejar rutas en duro.
GlobalSettings permite a los desarrolladores olvidarse de usar rutas en duro en sus desarrollos, solo se debe invocar la ruta lógica, y en tiempo de ejecución Huemul obtendrá la ruta específica según el ambiente de ejecución (desarrollo, QA, producción, etc).

## Ejemplos
Huemul incluye una serie de rutas basadas en DAMA, por ejemplo para especificar la ruta donde se almacenarán los archivos parquet que sean de tamaño pequeño, se debe especificar para cada ambiente definido en su proyecto.

En el siguiente ejemplo se indican las rutas para los ambientes "production" y "experimental"
```scala
  Global.MASTER_SmallFiles_Path.append(new huemul_KeyValuePath("production","/user/data/production/master/"))
  Global.MASTER_SmallFiles_Path.append(new huemul_KeyValuePath("experimental","/user/data/experimental/master/"))
```

Las rutas y configuraciones incluidas por huemul son las siguientes:

### TEMPORAL SETTING
Ruta usada para almacenar archivos temporales con huemul
* Global.TEMPORAL_Path: Especifica ruta para archivos intermedios en el procesamiento de los datos. 
     
### RAW SETTING
Rutas para almacenamiento de interfaces y archivos originales (en bruto, sin intervención, directamente desde los sistemas)
* Global.RAW_SmallFiles_Path: Especifica ruta para archivos RAW pequeños 
* Global.RAW_BigFiles_Path: Especifica ruta para archivos RAW grandes  
   
### MASTER SETTING
Rutas y Bases de datos en Hive para almacenar los datos resultantes del procesamiento (por lo general en parquet). Por lo general un modelo master corresponde a estructuras en tercera forma normal de un sistema origen específico.
* Global.MASTER_DataBase: Nombre de la base de datos en Hive que contiene la referencia a un archivo parquet en particular.   
* Global.MASTER_SmallFiles_Path: Ruta donde se almacenan los archivos parquet pequeños.
* Global.MASTER_BigFiles_Path: Ruta donde se almacenan los archivos parquet grandes.

### DIM SETTING
Rutas y Bases de datos en Hive para almacenar los modelos de datos dimensionales (estrella y/o copo de nieve). Estos modelos pueden ser creados a partir de los datos masterizados, o directamente desde las fuentes RAW (depende de la estrategia utilizada).
* Global.DIM_DataBase: Nombre de la base de datos en Hive que contiene la referencia a un archivo parquet del modelo dimensional (dimension o tabla de hechos)   
* Global.DIM_SmallFiles_Path: Ruta donde se almacenan los archivos parquet dimensionales pequeños. 
* Global.DIM_BigFiles_Path: Ruta donde se almacenan los archivos parquet dimensionales grandes.

### ANALYTICS SETTING
Rutas y Bases de datos en Hive para almacenar los tablones y estructuras utilizadas para la creación y explotación de modelos de Analytics (regresiones, ML, etc).
* Global.ANALYTICS_DataBase: Nombre de la base de datos en Hive que contiene la referencia a un archivo parquet del modelo analítico.   
* Global.ANALYTICS_SmallFiles_Path: Ruta donde se almacenan los archivos parquet pequeños.
* Global.ANALYTICS_BigFiles_Path: Ruta donde se almacenan los archivos parquet grandes.)
### REPORTING SETTINGG
Rutas y bases de datos en Hive para almacenar las tablas que son utilizadas por las herramientas de reporting tradicional. Este ambiente busca evitar que las herramientas de reporting hagan joins excesivo, y de esta forma mejorar la performance tanto del reporte como del ecosistema completo.
* Global.REPORTING_DataBase: Nombre de la base de datos en Hive que contiene la referencia a un archivo parquet del modelo de reporting.)
* Global.REPORTING_SmallFiles_Path: Ruta donde se almacenan los archivos parquet pequeños.
* Global.REPORTING_BigFiles_Path: Ruta donde se almacenan los archivos parquet grandes.

### SANDBOX SETTING
El ambiente SandBox permite a los usuarios explorar sin romper nada productivo.G
* Global.SANDBOX_DataBase: Nombre de la base de datos en Hive donde están las estructuras creadas por usuarios)
* Global.SANDBOX_SmallFiles_Path: Ruta donde se almacenan los archivos parquet pequeños. 
* Global.SANDBOX_BigFiles_Path: Ruta donde se almacenan los archivos parquet grandes.
   
### DQ_ERROR SETTING
Este ambiente permite consultar los errores y warnings de DataQuality aplicados sobre las tablas.
* Global.DQError_DataBase: Nombre de la base de datos en hive.
* Global.DQError_Path: Ruta donde se almacenan los archivos parquet que contienen el detalle de los errores y warnings de DataQuality)
### OLD VALUE TRACEE
Este ambiente contiene los datos que cambian en las tablas de tipo "master" y "reference"
* Global.MDM_OldValueTrace_DataBase: Nombre de la base de datos en Hive donde los usuarios pueden consultar los cambios de datos de cada tabla / campo.
* Global.MDM_OldValueTrace_Path: Ruta donde se almacenan los archivos parquet con el historial de cambios.

### BACKUP
Este ambiente contiene los backups de archivos parquet sobre tablas de tipo "master" y "reference"
* Global.MDM_Backup_Path: Ruta donde se almacenan los backups de los archivos parquet.