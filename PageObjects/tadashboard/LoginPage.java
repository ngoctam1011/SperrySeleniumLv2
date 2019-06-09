package tadashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends BasePage {

	Select cbRepo = new Select(driver.findElement(By.id("repository")));
	
	@FindBy(id = "username")
	private WebElement txtUsername;
	
	@FindBy(id = "password")
	private WebElement txtPassword;
	
	@FindBy(xpath = "//div[@class='btn-login']")
	private WebElement btnLogin;
	
	public LoginPage(WebDriver driver) {
        super(driver);
    }
	
	public HomePage login (String username, String password, String repo) throws Exception{
		
		selectByValue(cbRepo, repo);
		enter(txtUsername, username);
		enter(txtPassword, password);
		click(btnLogin);
		
		return (HomePage) GetInstance(HomePage.class).waitForPageLoaded();
    }
	
	
	
}
