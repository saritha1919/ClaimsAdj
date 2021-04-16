package com.adjudication.pages.viewclaims;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.contracts.IAction;
import com.adjudication.seleniumactions.implementation.ActionControls;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class ViewClaimsPage extends BasePage{

	MyLogger log = new MyLogger(ViewClaimsPage.class.getName());
	
	public ViewClaimsPage(WebDriver driver) {
		super(driver);
	}
	
	String tabserviceDetailsXpath="//a[@href='#ClaimServiceDetails']";
	
	@FindBy(xpath="//button[@class='claim_Approve_btn dropdown-toggle move-to-status']")
	@CacheLookup
	WebElement btnApproveDental;
	
	@FindBy(xpath="//button[@class='claim_PreBatch_btn dropdown-toggle']")
	@CacheLookup
	WebElement btnPrebatch;
	
	@FindBy(xpath="//a[@class='dropdown-item move-to-status badge btn-danger']")
	@CacheLookup
	WebElement btnPrebatchDeny;
	
	@FindBy(xpath="//a[@class='dropdown-item move-to-status badge btn-success']")
	@CacheLookup
	WebElement btnPrebatchPay;
	
	@FindBy(xpath="//div[@class='alert ui-pnotify-container alert-success ui-pnotify-shadow']/h4")
	@CacheLookup
	WebElement suceessPopUp1;
	
	@FindBy(xpath="//div[@class='alert ui-pnotify-container alert-success ui-pnotify-shadow']/div[4]")
	@CacheLookup
	WebElement suceessPopUp2;
	
	WaitUtility wait= new WaitUtility();
	IAction action= new ActionControls();
	JavaScriptExecutor js= new JavaScriptExecutor(driver);
 
	/**
	 * This method is used to Click on service details in view claims page.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnServiceDetails() throws Exception{
		wait.waitForSeconds(2);
		WebElement tabserviceDetails=driver.findElement(By.xpath(tabserviceDetailsXpath));
		wait.waitTillElementVisible(driver, 60, tabserviceDetails);
		//js.clickOnElement(tabserviceDetails);
		tabserviceDetails.click();
		log.logInfo("clicked on Service details tab");
	}
	
	/**
	 * Click on pend Button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnPendButton() throws Exception{
		WebElement btnPend=driver.findElement(By.xpath("//button[@class='claim_Pend_btn move-to-status']"));
		wait.waitTillElementVisible(driver, 60, btnPend);
		//btnApprove.click();
		js.clickOnElement(btnPend);
		log.logInfo("Clicked on Pend button");
	}
	
	/**
	 * Click on apporve button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnApporveButton() throws Exception{
		WebElement btnApprove=driver.findElement(By.xpath("//button[@class='claim_Approve_btn move-to-status']"));
		wait.waitTillElementVisible(driver, 60, btnApprove);
		//btnApprove.click();
		js.clickOnElement(btnApprove);
		log.logInfo("Clicked on Approve button");
	}
	
	/**
	 * Click on approve button for dental.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnApproveButtonForDental() throws Exception{
		wait.waitTillElementVisible(driver, 60, btnApproveDental);
		//btnApproveDental.click();
		js.clickOnElement(btnApproveDental);
		log.logInfo("Clicked on Approve button");
	}
	
	/**
	 * Click on deny button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnDenyButton() throws Exception {
		WebElement btnDeny=driver.findElement(By.xpath("//button[@class='claim_Denied_btn move-to-status']"));
		wait.waitTillElementVisible(driver, 60, btnDeny);
		js.clickOnElement(btnDeny);
		//btnDeny.click();
		log.logInfo("Clicked on Deny button");
	}
	
	/**
	 * Click on payer review button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnPayerReviewButton() throws Exception {
		WebElement btnPayerReview=driver.findElement(By.xpath("//button[@class='claim_PayerReview_btn move-to-status']"));
		wait.waitTillElementVisible(driver, 60, btnPayerReview);
		js.clickOnElement(btnPayerReview);
		//btnDeny.click();
		log.logInfo("Clicked on PayerReview button");
	}
	
	/**
	 * Click on management review button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnManagementReviewButton() throws Exception {
		WebElement btnManagementReview=driver.findElement(By.xpath("//button[@class='claim_ManagementReview_btn move-to-status']"));
		wait.waitTillElementVisible(driver, 60, btnManagementReview);
		js.clickOnElement(btnManagementReview);
		//btnDeny.click();
		log.logInfo("Clicked on Management Review button");
	}
	
	/**
	 * Check apporve success msg.
	 *
	 * @param sucessMsg the sucess msg
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkApproveSuccessMsg(String sucessMsg) throws Exception{
		wait.waitTillElementVisible(driver, 120, suceessPopUp1);
		String message=suceessPopUp1.getText();
		message=message+" "+suceessPopUp2.getText();
		System.out.println("Message:"+message);
		System.out.println("hardcode:"+sucessMsg);
		boolean isPresent=false;
		if(message.equals(sucessMsg)) {
			isPresent=true;   
			log.logInfo(sucessMsg);
		}
		else {
			log.logError("Meesage is not matching with "+message);
		}
		return isPresent;
	}

	/**
	 * Check deny warning msg.
	 *
	 * @param warningMsg the warning msg
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkDenyWarningMsg(String warningMsg) throws Exception{
		WebElement warningPopup=driver.findElement(By.xpath("//div[@class='alert ui-pnotify-container alert-warning ui-pnotify-shadow']/div[4]"));
		wait.waitTillElementVisible(driver, 120, warningPopup);
		String message=warningPopup.getText();
		System.out.println("Message:"+message);
		System.out.println("hardcode:"+warningMsg);
		boolean isPresent=false;
		if(message.equals(warningMsg)) {
			isPresent=true;   
			log.logInfo(warningMsg);
		}
		return isPresent;
	}
	
	
	/**
	 * Click on prebatch button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnPrebatchButton() throws Exception{
		wait.waitTillElementVisible(driver, 60, btnPrebatch);
		btnPrebatch.click();
	}
	
	/**
	 * Click on prebatch deny button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnPrebatchDenyButton() throws Exception{
		wait.waitTillElementVisible(driver, 60, btnPrebatchDeny);
		btnPrebatchDeny.click();
	}
	
	/**
	 * Click on prebatch pay button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnPrebatchPayButton() throws Exception{
		wait.waitTillElementVisible(driver, 60, btnPrebatchPay);
		btnPrebatchPay.click();
	}
}
