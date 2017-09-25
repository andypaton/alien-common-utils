package com.alien.utils.webdriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.alien.utils.webdriver.drivers.ChromeDriverForCucumber;
import com.alien.utils.webdriver.drivers.FirefoxDriverForCucumber;
import com.alien.utils.webdriver.drivers.HtmlUnitDriverForCucumber;
import com.alien.utils.webdriver.drivers.PhantomJSDriverForCucumber;
import com.alien.utils.webdriver.drivers.RemoteWebDriverForCucumber;

public class DriverFactory {

    private static final int WAIT = 30;

    private static final String REMOTE_FIREFOX = "remote_firefox";

    private static final String REMOTE_CHROME = "remote_chrome";

    private static final String REMOTE_EDGE = "remote_edge";

    private static final String REMOTE_INTERNET_EXPLORER = "remote_ie";

    private static final String HTMLUNIT = "htmlunit";

    private static final String FIREFOX = "firefox";

    private static final String CHROME = "chrome";

    private static final String PHANTOMJS = "phantom";

    private String webDriver;
    private String hub;

    public CucumberWebDriver getInstance() throws IOException, URISyntaxException {

        CucumberWebDriver driver;

        if (System.getProperty("web.driver") != null) {
            webDriver = System.getProperty("webdriver");
        }
        
        if (System.getProperty("web.batch.admin.remote.hub") != null) {
            hub = System.getProperty("web.batch.admin.remote.hub");
        }

        if (CHROME.equals(webDriver)) {
            driver = new ChromeDriverForCucumber();
        } else if (FIREFOX.equals(webDriver)) {
            driver = new FirefoxDriverForCucumber();
        } else if (HTMLUNIT.equals(webDriver)) {
            driver = new HtmlUnitDriverForCucumber(true);
        } else if (REMOTE_CHROME.equals(webDriver)) {
            driver = new RemoteWebDriverForCucumber(new URL(hub), DesiredCapabilities.chrome());
        } else if (REMOTE_FIREFOX.equals(webDriver)) {
            driver = new RemoteWebDriverForCucumber(new URL(hub), DesiredCapabilities.firefox());
        } else if (REMOTE_EDGE.equals(webDriver)) {
            driver = new RemoteWebDriverForCucumber(new URL(hub), DesiredCapabilities.edge());
        } else if (REMOTE_INTERNET_EXPLORER.equals(webDriver)) {
            driver = new RemoteWebDriverForCucumber(new URL(hub), DesiredCapabilities.internetExplorer());
        } else if (PHANTOMJS.equals(webDriver)) {
            driver = PhantomJSDriverForCucumber.getPhantomDriver();
        } else {
            driver = new FirefoxDriverForCucumber();
        }

        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(WAIT, TimeUnit.SECONDS);

        return driver;
    }
}
