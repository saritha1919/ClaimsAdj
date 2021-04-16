package com.adjudication.seleniumactions.contracts;

import org.openqa.selenium.WebElement;

public interface IjavaScript {

	public void executeJavaScript(String scriptToExecute) throws Exception;
	
	public void scrollDown(int x, int y) throws Exception;
	
	public String executeJavaScriptWithReturnValue(String scriptToExecute) throws Exception;
	
	public void scrollIntoView(WebElement element) throws Exception;
	
	public void clickOnElement(WebElement element) throws Exception;
}
