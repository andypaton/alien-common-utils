package com.alien.utils.webdriver.buisinessProcess;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import com.bskyb.acceptance.runtime.AcceptanceTestRuntimeState;
import com.bskyb.acceptance.webdriver.WebDriverUtility;

public abstract class BusinessProcess implements BusinessProcessExecution {

	private static final int WAIT_30_SECS = 30;
    @Autowired
    private AcceptanceTestRuntimeState acceptanceTestRuntimeState;
    @Autowired
    private WebDriverUtility webDriverUtility;

    @Override
    public void execute() {
        runJob();
    }

    public BusinessProcess registerTargetEndpoint(String targetEndpoint) {
        webDriverUtility.registerTargetEndpoint(targetEndpoint, false);
        return this;
    }

    public void closeWebDriver() {
        webDriverUtility.closeDriver();
    }

    public void takeScreenShot() {
    	webDriverUtility.takeScreenShot();
    }
    
    protected void takeScreenShot(String description) {
        webDriverUtility.takeScreenShot(description);
    }

    protected abstract void runJob();

    protected WebDriver getWebDriver() {
        return acceptanceTestRuntimeState.getWebDriver();
    }

    protected void snap(String linkText) {
        acceptanceTestRuntimeState.getWebDriver().takeScreenShot(acceptanceTestRuntimeState.getScenario(), linkText);
    }
    
    protected void waitImplicity(long time) {
    	getWebDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }
    
    protected void waitForElement(By locator) {
        waitForElement(locator, WAIT_30_SECS);
    }

    protected void waitForElement(By locator, int waitInSeconds) {
    	WebDriverWait wait = new WebDriverWait(acceptanceTestRuntimeState.getWebDriver(), waitInSeconds);
    	wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForElementToBecomeVisible(By locator, int waitInSeconds) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), waitInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void waitForElementToBecomeClickable(By locator, int waitInSeconds) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), waitInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected void waitForElementToBePresent(By locator, int waitInSeconds) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), waitInSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }    
}