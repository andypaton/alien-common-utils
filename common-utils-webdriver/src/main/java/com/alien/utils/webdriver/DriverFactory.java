package com.alien.utils.webdriver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.bskyb.acceptance.webdriver.drivers.ChromeDriverForCucumber;
import com.bskyb.acceptance.webdriver.drivers.FirefoxDriverForCucumber;
import com.bskyb.acceptance.webdriver.drivers.HtmlUnitDriverForCucumber;
import com.bskyb.acceptance.webdriver.drivers.PhantomJSDriverForCucumber;
import com.bskyb.acceptance.webdriver.drivers.RemoteWebDriverForCucumber;

@Component
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

    @Value("${web.driver:firefox}")
    private String webDriver;

    @Value("${web.batch.admin.remote.hub:}")
    private String hub;

    public CucumberWebDriver getInstance() throws IOException, URISyntaxException {

        CucumberWebDriver driver;

        if (System.getProperty("web.driver") != null) {
            webDriver = System.getProperty("web.driver");
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
