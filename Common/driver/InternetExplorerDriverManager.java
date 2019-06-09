package driver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Utilities;

import java.io.File;
import java.io.IOException;

public class InternetExplorerDriverManager extends AbstractDriverManager {

    private InternetExplorerDriverService internetExplorerDriverService;
    private final File internetExplorerDriverExe;
    private boolean isLocal;

    InternetExplorerDriverManager(boolean isLocal) {
        final String path = Utilities.getProjectPath() + "\\Executables\\IEDriverServer.exe";
        internetExplorerDriverExe = new File(path);
        System.setProperty("webdriver.ie.driver", path);
        this.isLocal = isLocal;
    }

    @Override
    public void startService() {
        if (null == internetExplorerDriverService) {
            try {
                internetExplorerDriverService = new InternetExplorerDriverService.Builder()
                        .usingDriverExecutable(internetExplorerDriverExe)
                        .usingAnyFreePort()
                        .build();
                internetExplorerDriverService.start();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            System.out.println("InternetExplorerDriverService Started");
        }
    }

    @Override
    public void stopService() {
        if (null != internetExplorerDriverService && internetExplorerDriverService.isRunning()) {
            internetExplorerDriverService.stop();
            System.out.println("InternetExplorerDriverService Stopped");
        }
    }

    @Override
    public String createDriver() {
        final InternetExplorerOptions options = new InternetExplorerOptions();
        //add required options here
        if (!isLocal) {
			driver = new RemoteWebDriver(getGridUrl(), options);
		} else {
			driver = new InternetExplorerDriver(internetExplorerDriverService, options);
		}
        System.out.println("InternetExplorerDriver Started");
        return DesiredCapabilities.internetExplorer().getBrowserName();
    }
}