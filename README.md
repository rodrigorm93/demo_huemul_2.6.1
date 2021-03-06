﻿# Demo Huemul 2.6.1

## Construido con 🛠️

- [Apache Maven 3.6.3](https://maven.apache.org/download.cgi) - Manejador de dependencias
- [Java version: 1.8.0_271](https://www.oracle.com/technetwork/es/java/javase/downloads/index.html) - Java Platform, Standard Edition Development Kit (JDK)
- OS name: "windows 10"

##  Ejecución del código ⚙️

_Se deben ejecutar en el siguiente orden:_

* com.yourcompany.catalogo.process_negocio: carga los datos de negocios.
* com.yourcompany.catalogo.process_producto: carga los datos de productos.
* com.yourcompany.sbif.process_mensual: carga los siguientes datos:
    * process_institucion: carga datos de instituciones en tabla maestra. Esta ingesta permite ver el tratamiento de datos semi-estructurados
    * process_institucion_mes: carga datos de instituciones con foto mensual
    * process_planCuenta: carga datos del plan de cuentas en tabla maestra
    * process_planCuenta_mes: carga datos del plan de cuentas con foto mensual
    * process_eerr_mes: carga los datos de estados de resultados y balances de todos los bancos en forma mensual

### Ejecución process_negocio
```
spark-submit --master local --jars huemul-bigdatagovernance-2.6.1.jar,huemul-sql-decode-1.0.jar,demo_settings-2.6.1.jar,demo_catalogo-2.6.1.jar,postgresql-9.4.1212.jar --class com.yourcompany.catalogo.process_negocio  demo_sbif-2.6.1.jar environment=production,ano=2018,mes=6

```

### Ejecución process_producto
```
spark-submit --master local --jars huemul-bigdatagovernance-2.6.1.jar,huemul-sql-decode-1.0.jar,demo_settings-2.6.1.jar,demo_catalogo-2.6.1.jar,postgresql-9.4.1212.jar --class com.yourcompany.catalogo.process_producto  demo_sbif-2.6.1.jar environment=production,ano=2018,mes=6

```

### Ejecución process_mensual
```
spark-submit --master local --jars huemul-bigdatagovernance-2.6.1.jar,huemul-sql-decode-1.0.jar,demo_settings-2.6.1.jar,demo_catalogo-2.6.1.jar,postgresql-9.4.1212.jar --class com.yourcompany.sbif.process_mensual  demo_sbif-2.6.1.jar environment=production,ano=2018,mes=6

```

## Wiki 📖

Más informacion [Huemul](http://www.huemulsolutions.com/posts/Ejemplo-Sistema-Financiero-Chile/)
