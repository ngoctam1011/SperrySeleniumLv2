package driver;

public class DriverManagerFactory {
	
	public static AbstractDriverManager getManager(DriverType type) {

        AbstractDriverManager driverManager;

        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager(false, false);
                break;
            case CHROME_LOCAL:
                driverManager = new ChromeDriverManager(true, false);
                break;
            case CHROME_LOCAL_HEADLESS:
                driverManager = new ChromeDriverManager(true, true);
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager(false, false);
                break;
            case FIREFOX_LOCAL:
            	driverManager = new FirefoxDriverManager(true, false);
            	break;
            case FIREFOX_LOCAL_HEADLESS:
            	driverManager = new FirefoxDriverManager(true, true);
            	break;
            case IE:
            	driverManager = new InternetExplorerDriverManager(false);
            	break;
            case IE_LOCAL:
            	driverManager = new InternetExplorerDriverManager(true);
            	break;	
            case EDGE:
            	driverManager = new EdgeDriverManager(false);
            	break;
            case EDGE_LOCAL:
            	driverManager = new EdgeDriverManager(true);
            	break;
            default:
                driverManager = new ChromeDriverManager(false, false);
                break;
        }
        return driverManager;

    }
}
