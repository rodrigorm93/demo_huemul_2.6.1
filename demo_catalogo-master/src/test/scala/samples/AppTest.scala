package samples

import org.junit._
import Assert._
import com.huemulsolutions.bigdata.common._
import com.huemulsolutions.bigdata.control._
import com.yourcompany.tables.master._

import scala.collection.mutable.ArrayBuffer


@Test
class AppTest {
    val Global: huemul_GlobalPath  = new huemul_GlobalPath()
    Global.GlobalEnvironments = "production, experimental"
    Global.CONTROL_Setting.append(new huemul_KeyValuePath("production",s"file.txt"))
    Global.IMPALA_Setting.append(new huemul_KeyValuePath("production",s"file.txt"))
    Global.TEMPORAL_Path.append(new huemul_KeyValuePath("production",s"/usr/production/temp/"))
    Global.DQError_Path.append(new huemul_KeyValuePath("production",s"/usr/production/temp/"))
    Global.DQError_DataBase.append(new huemul_KeyValuePath("production",s"dqerror_database"))
    Global.setValidationLight()

    val args: Array[String] = new Array[String](1)
    args(0) = "Environment=production,RegisterInControl=false,TestPlanMode=true"
      
    val huemulBigDataGov = new huemul_BigDataGovernance("Pruebas Inicialización de Clases",args,Global)
    val Control = new huemul_Control(huemulBigDataGov,null, huemulType_Frequency.ANY_MOMENT)

    @Test
    def OK(): Unit = assertTrue(true)
        
    
    /****************************************************************************************/
    /**************   tbl_comun_producto_mes  *********************/
    /****************************************************************************************/
     
    @Test
    def test_tbl_comun_producto_mes(): Unit = assertTrue(TEST_tbl_comun_producto_mes)
    def TEST_tbl_comun_producto_mes: Boolean = {
      var SinError = true
      
      try {
        val Master = new tbl_comun_producto(huemulBigDataGov,Control)
        if (Master.Error_isError) {
          println(s"Codigo: ${Master.Error_Code}, Descripción: ${Master.Error_Text}")
          SinError = false
        }
      } catch {
        case e: Exception => 
          SinError = false
          println(e)
      }
      SinError
    }
    
    
    /****************************************************************************************/
    /**************   tbl_comun_negocio  *********************/
    /****************************************************************************************/
     
    @Test
    def test_tbl_comun_negocio(): Unit = assertTrue(TEST_tbl_comun_negocio)
    def TEST_tbl_comun_negocio: Boolean = {
      var SinError = true
      
      try {
        val Master = new tbl_comun_negocio(huemulBigDataGov,Control)
        if (Master.Error_isError) {
          println(s"Codigo: ${Master.Error_Code}, Descripción: ${Master.Error_Text}")
          SinError = false
        }
      } catch {
        case e: Exception => 
          SinError = false
          println(e)
      }
      SinError
    }

    
   

}


