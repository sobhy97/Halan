package Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

public class PageBase 
{
    private WebDriver driver;

	@BeforeTest
    public void teardown()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

}
