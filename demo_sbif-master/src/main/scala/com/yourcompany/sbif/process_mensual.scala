package com.yourcompany.sbif

object process_mensual {
  def main(args: Array[String]): Unit = {
    process_institucion.main(args)
    process_institucion_mes.main(args)
    
    process_planCuenta.main(args)
    process_planCuenta_mes.main(args)
    
    process_eerr_mes.main(args)
  }
}