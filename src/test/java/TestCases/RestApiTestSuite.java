package TestCases;
import Locators.ReportConfig;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
@Listeners(Locators.ListenerTest.class)

public class RestApiTestSuite extends ReportConfig {

   @BeforeTest
   public void reportSetup() {
       super.reportSetup("Rest Api Test Suite.html" , "Rest Api");
   }


   @Test(priority=1)
    public void restApiTestCase()
   {
       ExtentTest test = extent.createTest("Rest Api");
       RestApi restApi = new RestApi();
       restApi.getProfileData(test);
   }

    @AfterTest
    public void tearDown() throws IOException {
        super.tearDown("Rest Api Test Suite.html");
    }

}
