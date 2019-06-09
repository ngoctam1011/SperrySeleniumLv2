package driver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.Utilities;

import java.io.File;
import java.io.IOException;

public class EdgeDriverManager extends AbstractDriverManager {
    private EdgeDriverService edgeDriverService;
    private final File edgeDriverExe;
    private boolean isLocal;

    EdgeDriverManager(boolean isLocal) {
        final String path = Utilities.getProjectPath() + "\\Executables\\MicrosoftWebDriver.exe";
        edgeDriverExe = new File(path);
        System.setProperty("webdriver.edge.driver", path);
        this.isLocal = isLocal;
    }

    @Override
    public void startService() {
        if (null == edgeDriverService) {
            try {
                edgeDriverService = new EdgeDriverService.Builder()
                        .usingDriverExecutable(edgeDriverExe)
                        .usingAnyFreePort()
                        .build();
                edgeDriverService.start();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            System.out.println("EdgeDriverService Started");
        }
    }

    @Override
    public void stopService() {
        if (null != edgeDriverService && edgeDriverService.isRunning()) {
            edgeDriverService.stop();
            System.out.println("EdgeDriverService Stopped");
        }
    }

    @Override
    public String createDriver() {
        final EdgeOptions options = new EdgeOptions();
        if (!isLocal) {
			driver = new RemoteWebDriver(getGridUrl(), options);
		} else {
			driver = new EdgeDriver(edgeDriverService, options);
		}
        System.out.println("EdgeDriver Started");
        return DesiredCapabilities.edge().getBrowserName();
    }
}