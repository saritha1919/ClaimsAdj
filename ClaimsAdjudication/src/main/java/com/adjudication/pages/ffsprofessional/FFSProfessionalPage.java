package com.adjudication.pages.ffsprofessional;

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

public class FFSProfessionalPage extends BasePage{

	MyLogger log = new MyLogger(FFSProfessionalPage.class.getName());
	
	public FFSProfessionalPage(WebDriver driver) {
		super(driver);
	}
	
	String tablePend="//table[@id='FFSPendQueTable']/thead/tr[2]/th";
	String tableOnhold="//table[@id='FFSOnHoldQueTable']/thead/tr[2]/th";
	String tablePayerReview="//table[@id='FFSPayerReviewClaimsListTable']/thead/tr[2]/th";
	String tableManagReview="//table[@id='FFSManagementReviewQueTable']/thead/tr[2]/th";
	String tableApproved="//table[@id='FFSPartialAdjudicatedQueTable']/thead/tr[2]/th";
	String tableDenied="//table[@id='FFSDeniedQueTable']/thead/tr[2]/th";
	String tablePrebatch="//table[@id='FFSApprovedQueTable']/thead/tr[2]/th";
	String tableBatchToPay="//table[@id='FFSPayementBatchQueTable']/thead/tr[2]/th[2]";
	String tablePaid="//table[@id='FFSPaidQueTable']/thead/tr[2]/th";
	String batchToPayId="FFSbatchClaims";
	
	@FindBy(id = "PendListTab")
	@CacheLookup
	WebElement tabPend;
	
	@FindBy(id = "OnHoldListTab")
	@CacheLookup
	WebElement tabOnhold;
	
	@FindBy(id = "menu_toggle")
	@CacheLookup
	WebElement menuToggle;

	@FindBy(id = "PayerReviewListTab")
	@CacheLookup
	WebElement tabPayerReview;
	
	@FindBy(id = "ManagementReviewListTab")
	@CacheLookup
	WebElement tabMangementReview;

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
	
	@FindBy(xpath = "//div[@id='loadingSample']")
	@CacheLookup
	WebElement imgLoadingSymbol;

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
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnOnHoldTab() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabOnhold);
		tabOnhold.click();
		log.logInfo("Clicked on OnHold tab.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableOnhold)));
	}

	/**
	 * Click on on management review.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnOnManagementReview() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabMangementReview);
		tabMangementReview.click();
		log.logInfo("Clicked on management review tab.");
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
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnApproved() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabApproved);
		tabApproved.click();
		log.logInfo("Clicked on Approved tab.");
	    //currently we are in cap prof page. after clicking on approved we will get table.so after getting table i am waiting for table by using BY.
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableApproved)));
	}

	/**
	 * Click on denied.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnDenied() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabDenied);
		tabDenied.click();
		log.logInfo("Clicked on denied tab.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableDenied)));
	}

	/**
	 * Click on pre batch.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnPreBatch() throws Exception {
		wait.waitTillElementVisible(driver, 60, tabPreBatch);
		tabPreBatch.click();
		log.logInfo("Clicked on prebatch tab.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tablePrebatch)));
	}
	
	/**
	 * Click on batch to pay.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnBatchToPay() throws Exception {
		WebElement tabBatchToPay= driver.findElement(By.id("BatchToPayListTab"));
		wait.waitTillElementVisible(driver, 60, tabBatchToPay);
		tabBatchToPay.click();
		log.logInfo("Clicked on batch to pay tab.");
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
		log.logInfo("Clicked on batchClaimToPay button.");
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
		log.logInfo("Clicked on paid tab.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(tablePaid)));
	}
	
}
