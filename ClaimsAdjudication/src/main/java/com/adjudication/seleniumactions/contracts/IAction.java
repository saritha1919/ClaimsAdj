package com.adjudication.seleniumactions.contracts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface IAction {
	
	public void moveToElementAndClick(WebElement element,WebDriver driver) throws Exception;
	
	public void dragAndDrop(WebElement source, WebElement destination,WebDriver driver) throws Exception;

}
