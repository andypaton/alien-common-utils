package com.alien.utils.webdriver.pageObjects;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class PageObject {

	public static final int ONE_SECOND = 1;
	public static final int FIVE_SECONDS = 5;
	public static final int THIRTY_SECONDS = 30;
	public static final int SIXTY_SECONDS = 60;
	public static final String ELEMENT_IS_CLICKABLE = "elementIsClickable";
	public static final String ELEMENT_IS_VISIBLE = "elementIsVisible";
    
    public <T> void waitForPageToLoad(Class<T> clz, final WebDriver webDriver) {
        for (final Field field : clz.getDeclaredFields()) {
        	if (field.isAnnotationPresent(FindBy.class)) {
                if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).id())) {
                	waitForElement(webDriver, By.id(field.getAnnotation(FindBy.class).id()), ELEMENT_IS_VISIBLE);
                }
                else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).name())) {
                	waitForElement(webDriver, By.name(field.getAnnotation(FindBy.class).name()), ELEMENT_IS_VISIBLE);
                }
                else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).className())) {
                	waitForElement(webDriver, By.className(field.getAnnotation(FindBy.class).className()), ELEMENT_IS_VISIBLE);
                } 
                else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).xpath())) {
                	waitForElement(webDriver, By.xpath(field.getAnnotation(FindBy.class).xpath()), ELEMENT_IS_VISIBLE);
                }
        	}
        }
    }
    
	public <T> WebElement waitForElement(final WebDriver webDriver, final By locator, String condition) {
		
		new FluentWait<WebDriver>(webDriver)
				.withTimeout(SIXTY_SECONDS, TimeUnit.SECONDS)
				.pollingEvery(FIVE_SECONDS, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.until(expectedCondition(condition, locator));
		 
		return webDriver.findElement(locator);
	}
	
	public PageObject pause() {
		return pause(ONE_SECOND);
	}
	
	public PageObject pause(int timeInSeconds) {
		try {TimeUnit.SECONDS.sleep(timeInSeconds);} 
		catch (InterruptedException e) {e.printStackTrace();}
		return this;
	}
    
	private ExpectedCondition<WebElement> expectedCondition(String condition, By locator) {

		switch (condition) {
		case ELEMENT_IS_VISIBLE:
			return ExpectedConditions.visibilityOfElementLocated(locator);
		case ELEMENT_IS_CLICKABLE:
			return ExpectedConditions.elementToBeClickable(locator);
		default:
			throw new IllegalArgumentException("invalid condition");
		}
	}
	
	public boolean isElementPresent(WebDriver webDriver, By by)  
	     {  
	        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
	        try  
	        {  
	            webDriver.findElement(by);  
	            return true;  
	        }  
	        catch(NoSuchElementException e)  
	        {  
	            return false;  
	        }  
	       finally  
	       {  
	           webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
	       }  
	}
}
