package com.adjudication.seleniumactions.implementation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.adjudication.seleniumactions.contracts.IjavaScript;

public class JavaScriptExecutor implements IjavaScript {

	private WebDriver driver;
	
	public JavaScriptExecutor(WebDriver driver) {
		this.driver=driver;
	}
	
	private JavascriptExecutor getJSEngine(){
		return (JavascriptExecutor) driver;
	}
	
	@Override
	public void executeJavaScript(String scriptToExecute) throws Exception {
		getJSEngine().executeScript(scriptToExecute);
		
	}

	@Override
	public void scrollDown(int x, int y) throws Exception {
		String scriptToExecute=String.format("window.scrollBy(%d,%d)", x,y);
		getJSEngine().executeScript(scriptToExecute);
	}

	@Override
	public String executeJavaScriptWithReturnValue(String scriptToExecute) throws Exception {
		return (String)getJSEngine().executeScript(scriptToExecute);
	}

	@Override
	public void scrollIntoView(WebElement element) throws Exception {
		getJSEngine().executeScript("arguments[0].scrollIntoView(true);", element);	
	}

	@Override
	public void clickOnElement(WebElement element) throws Exception {
	  getJSEngine().executeScript("arguments[0].click();", element);	
	}

	public String getDisplyValue(WebElement ele) {
		return (String) getJSEngine().executeScript("return arguments[0].style.display;", ele);
		
	}
	
	

}
