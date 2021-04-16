package com.adjudication.pages.capprofessional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.adjudication.pages.BasePage;
import com.adjudication.pages.capinstitutional.CAPInstitutionalPage;
import com.adjudication.seleniumactions.contracts.IjavaScript;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;


public class CAPProfessionalPage extends BasePage{

	MyLogger log = new MyLogger(CAPProfessionalPage.class.getName());
	
	public CAPProfessionalPage(WebDriver driver) {
		super(driver);
	}

	String tablePend="//table[@id='CAPPendQueTable']/thead/tr[2]/th";
	String tablePayerReview="//table[@id='CAPPayerReviewClaimsListTable']/thead/tr[2]/th";
	String tableOnhold="//table[@id='CAPOnHoldQueTable']/thead/tr[2]/th";
	String tableManagReview="//table[@id='CAPManagementReviewQueTable']/thead/tr[2]/th";
	String tableApproved="//table[@id='CAPPartialAdjudicatedQueTable']/thead/tr[2]/th";
	String tableDenied="//table[@id='CAPDeniedQueTable']/thead/tr[2]/th";
	String tablePrebatch="//table[@id='CAPApprovedQueTable']/thead/tr[2]/th";
	String tableBatchToPay="//table[@id='CAPPayementBatchQueTable']/thead/tr[2]/th[2]";
	String tablePaid="//table[@id='CAPPaidQueTable']/thead/tr[2]/th";
	String batchToPayId="batchClaims";
	
	@FindBy(id = "PendListTab")
	@CacheLookup
	WebElement tabPend;
	
	@FindBy(id = "OnHoldListTab")
	@CacheLookup
	WebElement tabOnhold;
	
	@FindBy(id = "menu_toggle")
	@CacheLookup
	WebElement menuToggle;
	
	@FindBy(id = "ManagementReviewListTab")
	@CacheLookup
	WebElement tabMangementReview;
	
	@FindBy(id = "PayerReviewListTab")
	@CacheLookup
	WebElement tabPayerReview;

	@FindBy(id = "ApprovedListTab")
	@CacheLookup
	WebElement tabApproved;
	
	@FindBy(id = "DeniedListTab")
	@CacheLookup
	WebElement tabDenied;
	
	@FindBy(id = "PreBatchListTab")
	@CacheLookup
	WebElement tabPreBatch;
	
	@FindBy(id = "PaidListTab")
	@CacheLookup
	WebElement tabPaid;

	WaitUtility wait = new WaitUtility();
	IjavaScript js = new JavaScriptExecutor(driver);

	/**
	 * Click on pend tab.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnPendTab() throws Exception{
		wait.waitTillElementVisible(driver, 60, tabPend);
		tabPend.click();
		log.logInfo("Clicked on Pend tab.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tablePend)));
	}
	
	/**
	 * Click on on hold tab.
	 *
	 * @author Saritha
	 * @throws Exception the exception
	 */
	public void clickOnOnHoldTab() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabOnhold);
		tabOnhold.click();
		log.logInfo("Clicked On Onhold Tab");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableOnhold)));
	}

	/**
	 * Click on on management review.
	 *
	 * @author Saritha
	 * @throws Exception the exception
	 */
	public void clickOnOnManagementReview() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabMangementReview);
		tabMangementReview.click();
		log.logInfo("Clicked On Management review Tab");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableManagReview)));
	}


	/**
	 * Click on on payer review.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnOnPayerReview() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabPayerReview);
		tabPayerReview.click();
		log.logInfo("Clicked on payer review tab.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tablePayerReview)));
	}
	
	/**
	 * Click on approved.
	 *
	 * @author Saritha
	 * @throws Exception the exception
	 */
	public void clickOnApproved() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabApproved);
		tabApproved.click();
		log.logInfo("Clicked On Approved Tab");
	    //currently we are in cap prof page. after clicking on approved we will get table.so after getting table i am waiting for table by using BY.
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableApproved)));
	}

	/**
	 * Click on denied.
	 *
	 * @author Saritha
	 * @throws Exception the exception
	 */
	public void clickOnDenied() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabDenied);
		tabDenied.click();
		log.logInfo("Clicked On Denied Tab");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableDenied)));
	}

	/**
	 * Click on pre batch.
	 *
	 * @author Saritha
	 * @throws Exception the exception
	 */
	public void clickOnPreBatch() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabPreBatch);
		tabPreBatch.click();
		log.logInfo("Clicked On Prebatch Tab");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tablePrebatch)));
	}
	
	/**
	 * Click on batch to pay.
	 *
	 * @author Saritha
	 * @throws Exception the exception
	 */
	public void clickOnBatchToPay() throws Exception {
		WebElement tabBatchToPay= driver.findElement(By.id("BatchToPayListTab"));
		wait.waitTillElementVisible(driver, 60, tabBatchToPay);
		tabBatchToPay.click();
		log.logInfo("Clicked On Batch to pay Tab");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableBatchToPay)));
	}
	
	/**
	 * Click on btach claim to pay button.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnBtachClaimToPayButton() throws Exception{
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id(batchToPayId)));
		driver.findElement(By.id(batchToPayId)).click();
		log.logInfo("Clicked On BatchCalim To Pay button Tab");
	}
	
	/**
	 * Click on paid tab.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnPaidTab() throws Exception{
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(menuToggle));
		js.clickOnElement(menuToggle);
		wait.waitTillElementVisible(driver, 60, tabPaid);
		tabPaid.click();
		log.logInfo("Clicked On paid Tab");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tablePaid)));
	}

}
