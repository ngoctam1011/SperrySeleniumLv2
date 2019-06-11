package tadashboard;

import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import common.LogWrapper;
import constant.Constant;
import driver.AbstractDriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;

public class BaseTest {
	
	protected static final Logger LOG = LogWrapper.createLogger(BaseTest.class.getName());
	
	AbstractDriverManager driverManager;
	public WebDriver driver;
    public PageGenerator page;
 
    @BeforeTest
    @Parameters("browser")
    public void beforeTest(String browser) {     	
    	System.setProperty("browser", browser);
    }
 
    
    @BeforeMethod
    
    public void beforeMethod () {
    	String browser = System.getProperty("browser");
    	driverManager = DriverManagerFactory.getManager(DriverType.valueOf(browser.toUpperCase()));
    	driver = driverManager.getDriver();
        //Instantiate the Page Class
    	LOG.info("Open URL");
    	driver.get(Constant.TA_DASHBOARD_URL);
        page = new PageGenerator(driver);
  
    }
 
    @AfterMethod
    public void afterMethod () {
    	driverManager.quitDriver();
    }
    
    @AfterTest
    public void afterTest() {
    	
    }
    
}
