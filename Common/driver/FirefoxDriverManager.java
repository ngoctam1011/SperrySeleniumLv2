package driver;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Utilities;


public class FirefoxDriverManager extends AbstractDriverManager {

	private GeckoDriverService geckoDriverService;
	private final File geckoDriverExe;
	private boolean isLocal;
	private boolean isHeadless;
	
	FirefoxDriverManager(boolean isLocal, boolean isHeadless) {
        final String path = Utilities.getProjectPath() + "\\Executables\\geckodriver.exe";
        geckoDriverExe = new File(path);
        System.setProperty("webdriver.gecko.driver", path);
        this.isLocal = isLocal;
		this.isHeadless = isHeadless;
    }
	
	@Override
	protected void startService() {
		if (geckoDriverService == null) {
            try {
                geckoDriverService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(geckoDriverExe)
                        .usingAnyFreePort()
                        .build();
                geckoDriverService.start();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            System.out.println("GeckoDriverService Started");
        }	
	}

	@Override
	protected void stopService() {
		if (null != geckoDriverService && geckoDriverService.isRunning()) {
            geckoDriverService.stop();
            System.out.println("GeckoDriverService Stopped");
        }
	}

	@Override
	protected String createDriver() {
		final FirefoxOptions options = new FirefoxOptions()
                .setLogLevel(FirefoxDriverLogLevel.ERROR); //to stop the debug spam
        // add additional options here as required
        
        if (!isLocal) {
			driver = new RemoteWebDriver(getGridUrl(), options);
		} else {
			if (isHeadless) {
				options.addArguments("--headless", "--disable-gpu");
			}
			driver = new FirefoxDriver(geckoDriverService, options);
		}
        System.out.println("FirefoxDriver Started");
        return DesiredCapabilities.firefox().getBrowserName();
	}

}
