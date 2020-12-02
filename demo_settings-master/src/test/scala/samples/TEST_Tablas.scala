package samples

import org.junit._
import Assert._
import com.huemulsolutions.bigdata.common._
import com.huemulsolutions.bigdata.control._
import com.huemulsolutions.bigdata.common.huemul_BigDataGovernance
import com.huemulsolutions.bigdata.tables._

@Test
class AppTest {
    val args: Array[String] = new Array[String](1)
    args(0) = "Environment=production,RegisterInControl=false,TestPlanMode=true"
      
    //val huemulLib = new huemul_BigDataGovernance("Pruebas Inicialización de Clases",args,com.yourcompany.settings.globalSettings.Global)
    //val Control = new huemul_Control(huemulLib,null, huemulType_Frequency.MONTHLY)
      
     @Test
    def OK() = assertTrue(true)


}


