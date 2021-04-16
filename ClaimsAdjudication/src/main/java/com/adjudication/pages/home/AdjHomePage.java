package com.adjudication.pages.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.contracts.IAction;
import com.adjudication.seleniumactions.implementation.ActionControls;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;


public class AdjHomePage extends BasePage{

	MyLogger log= new MyLogger(AdjHomePage.class.getName());
	
	public AdjHomePage(WebDriver driver) {
		super(driver);
	}

	int timeOutInSec=30;
	
	@FindBy(xpath="//a[@id='Module_Toggle']/i")
	@CacheLookup
	WebElement imgModduleToggle;

	@FindBy(xpath="(//div[@class='toggle1 '])[2]")
	@CacheLookup
	WebElement imgClaimsLite;
	

	@FindBy(xpath="//span[contains(text(),'Adjudication')]")
	@CacheLookup
	WebElement txtAdjOnMenu;
	
	@FindBy(xpath="(//li[@role='presentation']/a)[1]")
	@CacheLookup
	WebElement btnLogOut;

	
	WaitUtility wait= new WaitUtility();
	IAction action= new ActionControls();
	
	/**
	 * This method is used to Click on module toggle.
	 *
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public void clickOnModuleToggle() throws Exception{
		wait.waitTillElementBecomeClickable(driver, timeOutInSec, imgModduleToggle);
		imgModduleToggle.click();
		wait.waitForSeconds(2);
		log.logInfo("Clicked on moddule toggle");
	}
	
	/**
	 * This method is used to Click on claims lite from navigation bar.
	 *
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public CLHomePage clickOnClaimsLite() throws Exception{
		wait.waitTillElementVisible(driver, timeOutInSec, imgClaimsLite);
		action.moveToElementAndClick(imgClaimsLite, driver);
		log.logInfo("Clicked on claims lite from module toggle.");
		//wait.waitForSeconds(4);
		wait.waitTillElementInvisible(driver, 60, txtAdjOnMenu);
		return new CLHomePage(driver);
	}
	
	
	/**
	 *This method is used to  Click on log out.
	 *
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public void clickOnLogOut() throws Exception{
		wait.waitTillElementVisible(driver, 40, btnLogOut);
		btnLogOut.click();
		log.logInfo("Cliked on logout button");
	}
	
}
