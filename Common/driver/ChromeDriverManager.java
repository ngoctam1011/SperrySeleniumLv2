package driver;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Utilities;


public class ChromeDriverManager extends AbstractDriverManager {

	private ChromeDriverService chromeDriverService;
	private final File chromedriverExe;
	private boolean isLocal;
	private boolean isHeadless;

	ChromeDriverManager(boolean isLocal, boolean isHeadless) {
		final String path = Utilities.getProjectPath() + "\\Executables\\chromedriver.exe";
		chromedriverExe = new File(path);
		System.setProperty("webdriver.chrome.driver", path);
		this.isLocal = isLocal;
		this.isHeadless = isHeadless;
	}

	@Override
	protected void startService() {
		if (chromeDriverService == null) {
			try {
				chromeDriverService = new ChromeDriverService.Builder()
						.usingDriverExecutable(chromedriverExe)
						.usingAnyFreePort().build();
				chromeDriverService.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void stopService() {
		if (chromeDriverService != null && chromeDriverService.isRunning())
			chromeDriverService.stop();
	}

	@Override
	protected String createDriver() {
		//"--disable-infobars: info bar with message 'Chrome is being controlled by automated test software'
		final ChromeOptions options = new ChromeOptions().addArguments("--start-maximized", "--disable-infobars","--no-sandbox","--disable-extensions");
		options.setCapability("chrome.verbose", false);
		// add additional required options here
		if (!isLocal) {
			System.out.println(getGridUrl().toString());
			driver = new RemoteWebDriver(getGridUrl(), options);
		} else {
			if (isHeadless) {
				options.addArguments("--headless", "--disable-gpu");
			}
			driver = new ChromeDriver(chromeDriverService, options);
			//driver.navigate().to(getGridUrl());
		}
		System.out.println("ChromeDriver Started");
		return DesiredCapabilities.chrome().getBrowserName();
	}

}
