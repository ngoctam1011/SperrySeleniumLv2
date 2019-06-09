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
    //public WebDriverWait wait;
    public PageGenerator page;
 
    @BeforeTest
    @Parameters("browser")
    public void beforeTest(String browser) {
        
    	driverManager = DriverManagerFactory.getManager(DriverType.valueOf(browser.toUpperCase()));
    	driver = driverManager.getDriver();
        //wait = new WebDriverWait(driver,Constant.DRIVER_TIMEOUT);
 
    }
 
    @BeforeMethod
    public void beforeMethod () {
        //Instantiate the Page Class
    	LOG.info("Open URL");
    	driver.get(Constant.TA_DASHBOARD_URL);
        page = new PageGenerator(driver);
  
    }
 
    @AfterMethod
    public void teardown () {
    	driverManager.quitDriver();
    }
}
