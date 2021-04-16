package com.adjudication.seleniumactions.implementation;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import com.adjudication.seleniumactions.contracts.IAlert;



public class AlertControls implements IAlert{

	private WebDriver driver;
	
	public AlertControls(WebDriver driver) {
		this.driver=driver;
	}
	
	
	public Alert getAlert()
	{
		return driver.switchTo().alert();
	}
	public void acceptAlert() throws Exception {
		getAlert().accept();	
	}

	public void rejectAlert() throws Exception {
		getAlert().dismiss();	
	}

	public String getMessageFromAlert() throws Exception {
		return getAlert().getText();
	}

}
