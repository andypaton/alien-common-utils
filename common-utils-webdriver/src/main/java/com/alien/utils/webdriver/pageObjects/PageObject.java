package com.alien.utils.webdriver.pageObjects;

import static com.alien.utils.webdriver.pageObjects.State.ELEMENT_IS_VISIBLE;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {

    protected WebDriver webDriver;

	public static final int ONE_SECOND = 1;
	public static final int FIVE_SECONDS = 5;
	public static final int THIRTY_SECONDS = 30;
	public static final int SIXTY_SECONDS = 60;
    
			
	public PageObject(WebDriver driver){
		this.webDriver = driver;
		PageFactory.initElements(driver, this);
	}
	
    public <T> void waitForPageToLoad(Class<T> clz) {
    	
        for (final Field field : clz.getDeclaredFields()) {
          	if (field.isAnnotationPresent(FindBy.class)) {
        		
                if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).id())) {
             
            	        waitForElement(By.id(field.getAnnotation(FindBy.class).id()), ELEMENT_IS_VISIBLE);
            
                }else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).name())) {
                	
            	        waitForElement(By.name(field.getAnnotation(FindBy.class).name()), ELEMENT_IS_VISIBLE);
            
                }else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).className())) {
             
            	        waitForElement(By.className(field.getAnnotation(FindBy.class).className()), ELEMENT_IS_VISIBLE);
            
                }else if (StringUtils.isNotBlank(field.getAnnotation(FindBy.class).xpath())) {
              
            	        waitForElement(By.xpath(field.getAnnotation(FindBy.class).xpath()), ELEMENT_IS_VISIBLE);
            
                }
        	    }
        }
    }
    
    public void waitForLoad() {
    	
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
                
        WebDriverWait wait = new WebDriverWait(webDriver, THIRTY_SECONDS);
        wait.until(pageLoadCondition);
    }
    
	public <T> WebElement waitForElement(final By locator, State condition) {		
		
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
    
	private ExpectedCondition<WebElement> expectedCondition(State condition, By locator) {
		
		switch (condition) {
		case ELEMENT_IS_VISIBLE:
			return ExpectedConditions.visibilityOfElementLocated(locator);
		case ELEMENT_IS_CLICKABLE:
			return ExpectedConditions.elementToBeClickable(locator);
		default:
			throw new IllegalArgumentException("invalid condition");
		}
	}
	
	public boolean isElementPresent(By by) {  
		
	    webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
	    try {  
	            
	        	webDriver.findElement(by);  
	        return true;  
	    }  
	    catch(NoSuchElementException e) {
	        	
	        return false;  
	    }  
	    finally {
	        	
	        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
	    }  
	}
	
	public void clickUntilElementIsDisplayed(WebElement webElementToClick, WebElement webElementToWaitFor){
		
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start <= Integer.valueOf(SIXTY_SECONDS * 1000)) {
        	try{
        		webElementToClick.click();
		        if (webElementToWaitFor.isDisplayed()){
		            break;
		        }
        	}
        	catch(Exception e){
        		pause(ONE_SECOND);
        	}
        }
	}
}
