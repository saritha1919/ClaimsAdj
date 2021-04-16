package com.adjudication.seleniumactions.contracts;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface IdropDown {

	public void selectViaVisibleText(WebElement element, String visibleText) throws Exception;
	
	public void selectViaVisibleTextFromList(WebDriver driver,String xpath, String visibleText) throws Exception;
	
	public void selectViaValue(WebElement element, String value) throws Exception;
	public void selectViaIndex(WebElement element,int index) throws Exception;
	
	public boolean isMultiple(WebElement element) throws Exception;
	
	public List<WebElement> getAllOptions(WebElement element) throws Exception;
	
	public List<WebElement> getAllSelectedOptions(WebElement element) throws Exception;
	
	public WebElement getFirstSelectedOption(WebElement element) throws Exception;
	
	public void selectViaVisibleIndexFromList(List<WebElement> elements, int index) throws Exception;
	
}
