package com.alien.utils.webdriver.drivers;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;

import com.alien.utils.webdriver.CucumberWebDriver;

import cucumber.api.Scenario;

public class ChromeDriverForCucumber extends ChromeDriver implements CucumberWebDriver {

    private static final String IMAGE_PNG = "image/png";
    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_PATH = "./drivers/chromedriver";

    static {

        final String absolutePath = Thread.currentThread().getContextClassLoader().getResource(CHROME_DRIVER_PATH)
                .getPath();

        final File chromeDriverFile = new File(absolutePath);

        if (chromeDriverFile.exists() && !chromeDriverFile.canExecute()) {

            chromeDriverFile.setExecutable(true);

        }

        System.setProperty(WEBDRIVER_CHROME_DRIVER, absolutePath);

    }

    @Override
    public void takeScreenShot(Scenario scenario, String linkText) {

        final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, IMAGE_PNG);

    }

}
