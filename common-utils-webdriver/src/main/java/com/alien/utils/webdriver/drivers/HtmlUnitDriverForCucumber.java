package com.alien.utils.webdriver.drivers;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alien.utils.webdriver.CucumberWebDriver;

import cucumber.api.Scenario;

public class HtmlUnitDriverForCucumber extends HtmlUnitDriver implements CucumberWebDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlUnitDriverForCucumber.class);

    public HtmlUnitDriverForCucumber(boolean jsEnabled) {

        super(jsEnabled);

    }

    @Override
    public void takeScreenShot(Scenario scenario, String linkText) {

        LOGGER.info("Screenshots not supported by HtmlUnitDriver");

    }

}
