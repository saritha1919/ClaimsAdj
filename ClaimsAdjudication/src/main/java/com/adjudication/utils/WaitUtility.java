package com.adjudication.utils;

import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitUtility {
	
	public void waitForSeconds(int timeoutISeconds) throws Exception{
		Thread.sleep(timeoutISeconds*1000);
	}

	public WebDriverWait getWaitDriver(WebDriver driver, int timeoutInSeconds)
	{
		WebDriverWait wait=new WebDriverWait(driver, timeoutInSeconds);
		return wait;
	}
	public void waitTillElementVisible(WebDriver driver, int timeoutInSeconds,WebElement element){
		
		getWaitDriver(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOf(element));
	}
	
    public void waitTillElementsVisible(WebDriver driver, int timeoutInSeconds,List<WebElement> elements){
    	     elements.stream()
    	        .filter(WebElement::isDisplayed)
    	        .findFirst()
    	        .orElse(null);
	}
	
	public void waitTillAlertIsPresent(WebDriver driver,int timeoutInSeconds){
		getWaitDriver(driver, timeoutInSeconds).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitTillElementBecomeClickable(WebDriver driver, int timeoutInSeconds,WebElement element){
		getWaitDriver(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForTextToAppear(WebDriver driver, int timeoutInSeconds, final WebElement element){
		getWaitDriver(driver, timeoutInSeconds).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver){
				return element.getAttribute("value").length()!=0;
			}
		});
	}
	
	public void waitTillElementInvisible(WebDriver driver, int timeoutInSeconds,WebElement element) {
			getWaitDriver(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOf(element));
		
	}
	
    public void waitTillEleClassChanges(WebDriver driver,int timeoutInSeconds,String xpath,String classValue) {
    	getWaitDriver(driver, timeoutInSeconds).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
            	WebElement element = driver.findElement(By.xpath(xpath));
            	String classValueInApp = element.getAttribute("class");
                System.out.println("class:"+classValue);
                if (classValueInApp.equals(classValue))
                    return true;
                else
                    return false;
            }
        });
    }
	
    public void waitTillTextInvisible(WebDriver driver,int timeoutInSeconds,WebElement element,String text) {
    	getWaitDriver(driver, timeoutInSeconds).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element,text)));
           
    }
    
    public void waitTilCountChanges(WebDriver driver,int timeoutInSeconds,String xpath) {
    	getWaitDriver(driver, timeoutInSeconds).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                int elementCount = driver.findElements(By.xpath(xpath)).size();
                System.out.println("count:"+elementCount);
                if (elementCount > 1)
                    return true;
                else
                    return false;
            }
        });
    }
    
    public void waitTilCountBecameOne(WebDriver driver,int timeoutInSeconds,String xpath) {
    	getWaitDriver(driver, timeoutInSeconds).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                int elementCount = driver.findElements(By.xpath(xpath)).size();
                System.out.println("count:"+elementCount);
                if (elementCount == 1)
                    return true;
                else
                    return false;
            }
        });
    }
}
