package TestCases;
import org.testng.annotations.Test;

public class loginPageTest extends Testbase{
	
	@Test
	public void enterUserCredentials()
	{
		loginPage.loginPage("ahmed97","123456");

	}

}
