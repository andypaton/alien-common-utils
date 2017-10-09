package com.alien.utils.webdriver;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public final class WebDriverFactory {

    private static final int WAIT = 30;

    private static WebDriver WEB_DRIVER = null;
    
    private static final String CHROME_DRIVER_PATH = "./drivers/chrome_driver/chromedriver";
    private static final String GECKO_DRIVER_PATH = "./drivers/geckodriver";
    
    private static final String HTMLUNIT = "htmlunit";
    private static final String FIREFOX = "firefox";
    private static final String CHROME = "chrome";
    private static final String PHANTOMJS = "phantom";

//    private static boolean useChrome = false;
    
    private static String webDriver;
    private static String hub;


    public static WebDriver getWebDriver(){
    	
        if (System.getProperty("web.driver") != null) {
            webDriver = System.getProperty("web.driver");
        }
                
        if (WEB_DRIVER == null) {
        	
        	switch (webDriver){
        	
        	case CHROME: WEB_DRIVER = getChromeWebDriver(); break;
        	case FIREFOX: WEB_DRIVER = getFirefoxWebDriver(); break;
        	
        	default: WEB_DRIVER = getFirefoxWebDriver();
        	        	
        	}

        	WEB_DRIVER.manage().window().maximize();

//        	WEB_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        	WEB_DRIVER.manage().timeouts().pageLoadTimeout(WAIT, TimeUnit.SECONDS);
        	
        	WEB_DRIVER.manage().timeouts().setScriptTimeout(WAIT, TimeUnit.SECONDS);
        }

        return WEB_DRIVER;
    }

    public static void clear() {
        WEB_DRIVER.quit();
        WEB_DRIVER = null;
    }
    
    public static boolean webDriverExists() {
        return (WEB_DRIVER != null);
    }
    
    private static String getAbsolutePath(String relativePath){
    	return Thread.currentThread().getContextClassLoader().getResource(relativePath).getPath();
    }
    
    private static WebDriver getChromeWebDriver(){
    	

        final File chromeDriverFile = new File(getAbsolutePath(CHROME_DRIVER_PATH));
        if (chromeDriverFile.exists() && !chromeDriverFile.canExecute()) {
            chromeDriverFile.setExecutable(true);
        }
    	
        System.setProperty("webdriver.chrome.driver", getAbsolutePath(CHROME_DRIVER_PATH));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(capabilities);
        //return new ChromeDriver();
    }
    
    private static WebDriver getFirefoxWebDriver(){
    	
        System.setProperty("webdriver.gecko.driver", getAbsolutePath(GECKO_DRIVER_PATH));

    	FirefoxProfile profile = new FirefoxProfile();
    	profile.setAlwaysLoadNoFocusLib(true);
    	return new FirefoxDriver(profile);
    }
}
