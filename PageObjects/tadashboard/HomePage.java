package tadashboard;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {

	@FindBy(xpath = "//a[text()='Repository: ']/span")
	private WebElement txtRepoName;
	
	@FindBy(xpath = "//a[@href='#Welcome']")
	private WebElement lnkWelcome;
	
	public HomePage(WebDriver driver) {
		super(driver);
	}

	public String getRepoName() throws Exception {
		waitForDisplay(txtRepoName, 20);
		return getText(txtRepoName);		
	}
	
	public void checkRepoDisplay(String repoName) throws Exception {
		Assert.assertEquals(getRepoName(), repoName, "Repo is incorrect");
	}
	
	public String getUserLogin() throws Exception {
		waitForDisplay(lnkWelcome, 20);
		return getText(lnkWelcome);
	}
	
	public void checkLoginUserDisplay(String userName) throws Exception {
		Assert.assertEquals(getUserLogin(), userName);
	}
}
