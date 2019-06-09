package sperry;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.LogWrapper;
import driver.AbstractDriverManager;
import driver.DriverManagerFactory;
import driver.DriverType;


public class NewTest {
	
	protected static final Logger LOG = LogWrapper.createLogger(NewTest.class.getName());
    
	AbstractDriverManager driverManager;
    WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void beforeTest(String browser) {
    	driverManager = DriverManagerFactory.getManager(DriverType.valueOf(browser.toUpperCase()));
    	//driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }

    @Test
    public void launchTestAutomationGuruTest() {
    	LOG.info("Open url");
        driver.get("http://testautomationguru.com");
        LOG.info("Verify");
        Assert.assertEquals("TestAutomationGuru – A technical blog on test automation", driver.getTitle());
    }

   /*@Test
    public void launchGoogleTest() {
        driver.get("https://www.google.com");
        Assert.assertEquals("Google", driver.getTitle());
    }*/

    /*@Test
    public void launchYahooTest() {
        driver.get("https://www.yahoo.com");
        Assert.assertEquals("Yahoo", driver.getTitle());
    }*/
}
