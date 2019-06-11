package tadashboard;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Stopwatch;

import common.LogWrapper;
import constant.Constant;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

public class BasePage extends PageGenerator {

	protected static final Logger LOG = LogWrapper.createLogger(BaseTest.class.getName());

	public BasePage(WebDriver driver) {
		super(driver);
	}

	// Custom wait in BasePage and all page classes

	public BasePage waitForPageLoaded() {
		WebDriverWait wait = new WebDriverWait(this.driver, Constant.DRIVER_TIMEOUT);
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
		return this;
	}

	public WebElement waitForPresent(By locator, int timeOutInSeconds) throws Exception {
		WebElement element = null;
		//LOG.info(String.format("Wait for control %s to be present in DOM with timeOut %s", locator.toString(),timeOutInSeconds));
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception error) {
			LOG.error(String.format("Has error with control '%s': %s", locator.toString(), error.getMessage()));
			throw error;
		}
		return element;
	}

	public List<WebElement> waitForAllElementsPresent(By locator, int timeOutInSeconds) throws Exception {
		List<WebElement> elements = null;
		//LOG.info(String.format("Wait for all controls %s to be present in DOM with timeOut %s", locator.toString(),timeOutInSeconds));
		try {
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (Exception error) {
			LOG.error(String.format("Has error with control '%s': %s", locator.toString(), error.getMessage()));
			throw error;
		}
		return elements;
	}

	private WebElement waitForDisplayUsingByLocator(By locator, int timeOutInSeconds) throws Exception {
		WebElement element = null;
		try {
			//LOG.info(String.format("Wait for control %s to be displayed with timeOut: %s", locator.toString(),timeOutInSeconds));
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception error) {
			error.printStackTrace();
			LOG.error(String.format("Has error with control '%s': %s", locator.toString(), error.getMessage()));
			throw error;
		}
		return element;
	}

	private WebElement waitUntilElementDisplayed(final WebElement webElement, int timeOutInSeconds) throws Exception {
		//LOG.info(String.format("Wait for control %s to be displayed with timeOut: %s", webElement.toString(),timeOutInSeconds));
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		ExpectedCondition<Boolean> elementIsDisplayed = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver arg0) {
				try {
					webElement.isDisplayed();
					return true;
				} catch (NoSuchElementException e) {
					return false;
				} catch (StaleElementReferenceException f) {
					return false;
				}
			}
		};

		wait.until(elementIsDisplayed);

		try {
			webElement.isDisplayed();
			return webElement;
		} catch (Exception error) {
			error.printStackTrace();
			LOG.error("ERROR: " + error.getMessage());
			throw error;
		}
	}

	public <T> WebElement waitForDisplay(T elementAttr, int timeOutInSeconds) throws Exception {
		try {
			if (elementAttr.getClass().getName().contains("By")) {
				return waitForDisplayUsingByLocator((By) elementAttr, timeOutInSeconds);
			} else {
				return waitUntilElementDisplayed((WebElement) elementAttr, timeOutInSeconds);
			}
		} catch (Exception error) {
			LOG.error("Error: " + error.getMessage());
			return null;
		}
	}

	public void waitForInvisibility(By locator, int timeOutInSeconds) throws Exception {
		try {
			//LOG.info(String.format("Wait for control %s to be invisibled", locator.toString()));
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception error) {
			LOG.error(String.format("Has error with control '%s': %s", locator.toString(), error.getMessage()));
			throw error;
		}
	}

	public List<WebElement> waitForAllElementsDisplay(By locator, int timeOutInSeconds) throws Exception {
		List<WebElement> elements = null;
		try {
			//LOG.info(String.format("Wait for all controls %s to be visibled", locator.toString()));
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (Exception error) {
			LOG.error(String.format("Has error with control '%s': %s", locator.toString(), error.getMessage()));
			throw error;
		}
		return elements;
	}

	public WebElement waitForClickable(By locator, int timeOutInSeconds) throws Exception {
		WebElement element = null;
		try {
			//LOG.info(String.format("Wait for control %s to be clickabled", locator.toString()));
			WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception error) {
			LOG.error(String.format("Has error with control '%s': %s", locator.toString(), error.getMessage()));
			throw error;
		}
		return element;
	}

	// Click Method by using JAVA Generics (Be able to use both By or Webelement)
	public <T> void click(T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			driver.findElement((By) elementAttr).click();
		} else {
			((WebElement) elementAttr).click();
		}
	}

	// Write Text by using JAVA Generics (Be able to use both By or Webelement)
	public <T> void enter(T elementAttr, String text) {
		if (elementAttr.getClass().getName().contains("By")) {
			driver.findElement((By) elementAttr).sendKeys(text);
		} else {
			((WebElement) elementAttr).sendKeys(text);
		}
	}

	// Read Text by using JAVA Generics (Be able to use both By or Webelement)
	public <T> String getText(T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			return driver.findElement((By) elementAttr).getText();
		} else {
			return ((WebElement) elementAttr).getText();
		}
	}

	public <T> void selectByValue(T elementAttr, String value) {
		if (elementAttr.getClass().getName().contains("By")) {
			new Select(driver.findElement((By) elementAttr)).selectByValue(value);
		} else {
			((Select) elementAttr).selectByValue(value);
		}
	}

	public boolean isAlertPresent(int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Alert waitForAlert(int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void clickOkInAlert(int timeOutInSeconds) {

		waitForAlert(timeOutInSeconds).accept();
	}

	public void clickCancelInAlert(int timeOutInSeconds) {

		waitForAlert(timeOutInSeconds).dismiss();
	}

	public void promptInputPressOK(String inputMessage, int timeOutInSeconds) {
		Alert alert = waitForAlert(timeOutInSeconds);
		alert.sendKeys(inputMessage);
		alert.accept();

	}

	public String getAlertMessage(int timeOutInSeconds) {
		Alert alert = waitForAlert(timeOutInSeconds);
		return alert.getText();
	}

	// Close popup if exists
	public void handlePopup(By by) throws InterruptedException {
		List<WebElement> popup = driver.findElements(by);
		if (!popup.isEmpty()) {
			popup.get(0).click();
			Thread.sleep(200);
		}
	}

}
