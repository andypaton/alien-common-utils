package com.alien.utils.webdriver;

import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;

public interface CucumberWebDriver extends WebDriver {

    void takeScreenShot(Scenario scenario, String linkText);

}
