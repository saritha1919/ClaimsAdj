package com.adjudication.pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class CLHomePage extends BasePage {

	public CLHomePage(WebDriver driver) {
		super(driver);
	}
	
	MyLogger log= new MyLogger(CLHomePage.class.getName());
	int timeOutInSeconds=30;
	String clName="ClaimsLite";
	
	@FindBy(xpath="//span[contains(text(),'ClaimsLite')]")
	@CacheLookup
	WebElement txtCLOnBreadCrumb;
	
	WaitUtility wait= new WaitUtility();
	
	/**
	 * This method used to Checks if user navigated to Claims lite page or not.
	 *
	 * @return true, if user navigated to CLaimslite page.
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public boolean isCLPresent() throws Exception{
		wait.waitTillElementVisible(driver, timeOutInSeconds, txtCLOnBreadCrumb);
		log.logInfo("User navigated to ClaimsLite");
		return txtCLOnBreadCrumb.getText().equalsIgnoreCase(clName)?true:false;	
	}
	
	/**
	 * Side menu navigation.
	 *
	 * @param webElement the web element
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void sideMenuNavigation(String webElement) throws Exception{
		wait.waitForSeconds(4);
		driver.findElement(By.xpath(webElement)).click();
	}

}
