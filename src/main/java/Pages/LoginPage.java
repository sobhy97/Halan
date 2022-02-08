package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LoginPage {

    private WebDriver driver;
    private By userName = By.id("ctl00_CPHContainer_txtUserLogin");
    private By passw0rd = By.id("ctl00_CPHContainer_txtPassword");
    private By loginBttn = By.id("ctl00_CPHContainer_btnLoginn");
    public String invalidText = "Userid or Password did Not Match !!";


    public LoginPage(WebDriver driver)
    {
        this.driver= driver;
    }

    public void loginPage(String name , String pass)
    {
        driver.findElement(userName).sendKeys(name);
        driver.findElement(passw0rd).sendKeys(pass);
        driver.findElement(loginBttn).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Assert.assertEquals(invalidText,"Userid or Password did Not Match !!");

    }
}
