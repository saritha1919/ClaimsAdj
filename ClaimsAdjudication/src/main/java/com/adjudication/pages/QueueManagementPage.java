package com.adjudication.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.adjudication.seleniumactions.contracts.IjavaScript;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class QueueManagementPage extends BasePage {

	MyLogger log = new MyLogger(QueueManagementPage.class.getName());

	public QueueManagementPage(WebDriver driver) {
		super(driver);
	}

	WebElement viewIcon;
	String claimNumber;
	int rowNumber;
	int getTDCount;

	WaitUtility wait = new WaitUtility();
	IjavaScript js = new JavaScriptExecutor(driver);

	/**
	 * Click on view claims.
	 *
	 * @param tableId the table id
	 * @throws Exception the exception
	 */
	public void clickOnViewClaims(String tableId) throws Exception {
		wait.waitForSeconds(2);
		boolean isPresent = false;
		int count=0;
		//sometimes before filter we are grtting tdcount. that's why we are running loop to check the count is present among 13,14,15
		while (!isPresent) {
			List<WebElement> firstRowTds = driver
					.findElements(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr['1']/td"));
			wait.waitTillElementsVisible(driver, 60, firstRowTds);
			getTDCount = firstRowTds.size();
			System.out.println("TDCount:" + getTDCount);
			if (getTDCount == 14) {
				viewIcon = driver.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td[14]/a"));
				isPresent=true;
			} else if (getTDCount == 15) {
				viewIcon = driver.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td[15]/a"));
				isPresent=true;
			} else if (getTDCount == 13) {
				viewIcon = driver.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td[13]/a"));
				isPresent=true;
			}
			count++;
			if(count==10) {
				break;
			}
		}
		wait.waitTillElementVisible(driver, 60, viewIcon);
		js.clickOnElement(viewIcon);
		WebElement imgLoadingSymbol=driver.findElement(By.xpath("//div[@id=\"fullBodyContainer\"]/div/img"));
		log.logInfo("Clicked on View icon");
		// commonElements.click(viewIcon);
		wait.waitTillElementInvisible(driver, 120, imgLoadingSymbol);
	}

	public void clickOnViewClaims(String tableId,int td) throws Exception {
		wait.waitForSeconds(2);
		viewIcon = driver.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td["+td+"]/a"));
		wait.waitTillElementVisible(driver, 60, viewIcon);
		js.clickOnElement(viewIcon);
		WebElement imgLoadingSymbol=driver.findElement(By.xpath("//div[@id=\"fullBodyContainer\"]/div/img"));
		log.logInfo("Clicked on View icon");
		// commonElements.click(viewIcon);
		wait.waitTillElementInvisible(driver, 120, imgLoadingSymbol);
		
	}
	/**
	 * Gets the claim number.
	 *
	 * @param tableId the table id
	 * @return the claim number
	 * @throws Exception the exception
	 */
	public String getClaimNumber(String tableId) throws Exception {
		wait.waitTilCountBecameOne(driver, 60, "(//table[@id='" + tableId + "'])[1]/tbody/tr");
		WebElement claimNumberEle = driver
				.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td[1]"));
		wait.waitTillElementVisible(driver, 60, claimNumberEle);
		return claimNumberEle.getText();

	}

	/**
	 * Gets the claim number from prebatch.
	 *
	 * @param tableId the table id
	 * @return the claim number from prebatch
	 * @throws Exception the exception
	 */
	public String getClaimNumberFromPrebatch(String tableId) throws Exception {
		wait.waitTilCountBecameOne(driver, 60, "(//table[@id='" + tableId + "'])[1]/tbody/tr");
		WebElement claimNumberEle = driver
				.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td[2]"));
		wait.waitTillElementVisible(driver, 60, claimNumberEle);
		return claimNumberEle.getText();

	}

	/**
	 * Select claim from prebatch.
	 *
	 * @param tableId the table id
	 * @throws Exception the exception
	 */
	public void selectClaimFromPrebatch(String tableId) throws Exception{
		wait.waitTilCountBecameOne(driver, 60, "(//table[@id='" + tableId + "'])[1]/tbody/tr");
		WebElement checkBox = driver
				.findElement(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td/div/input"));
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//table[@id='" + tableId + "'])[1]/tbody/tr[1]/td/div/input")));
		checkBox.click();
		log.logInfo("Claim selected from prebatch");
	}
	
	/**
	 * Select claim from batch to pay with out filter.
	 *
	 * @param tbodyId the tbody id
	 * @throws Exception the exception
	 */
	public void selectClaimFromBatchToPayWithOutFilter(String tbodyId) throws Exception{
		WebElement checkBox;
		try {
			checkBox = driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input"));
			wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input")));
			checkBox.click();
		}catch (StaleElementReferenceException e) {
			checkBox = driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input"));
			wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input")));
			checkBox.click();
			log.logInfo("Claim selected from bact to pay");
		}
		
	}
	
	/**
	 * Select claim from batch to pay with filter.
	 *
	 * @param tbodyId the tbody id
	 * @throws Exception the exception
	 */
	public void selectClaimFromBatchToPayWithFilter(String tbodyId) throws Exception{
		wait.waitTilCountBecameOne(driver, 60, "//tbody[@id='"+tbodyId+"']/tr");
		WebElement checkBox;
		try {
			checkBox = driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input"));
			wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input")));
			checkBox.click();
		}catch (StaleElementReferenceException e) {
			checkBox = driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input"));
			wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[1]/div/input")));
			checkBox.click();
			log.logInfo("Claim selected from bact to pay");
		}
		
	}
	
	/**
	 * Gets the batch id from batch to pay with filter.
	 *
	 * @param tbodyId the tbody id
	 * @return the batch id from batch to pay with filter
	 * @throws Exception the exception
	 */
	public String getBatchIdFromBatchToPayWithFilter(String tbodyId) throws Exception{
		System.out.println("xpath for count://tbody[@id='"+tbodyId+"']/tr");
		wait.waitTilCountBecameOne(driver, 60, "//tbody[@id='"+tbodyId+"']/tr");
		WebElement batchIdElement;
		String batchId; 
		try {
			batchIdElement=driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[2]"));
		wait.waitTillElementVisible(driver, 60, batchIdElement);
		batchId=batchIdElement.getText();
		}catch (StaleElementReferenceException e) {
			batchIdElement=driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[2]"));
			wait.waitTillElementVisible(driver, 60, batchIdElement);
			batchId=batchIdElement.getText();
		}
		return batchId;
	}
	
	/**
	 * Gets the batch id from batch to pay with O ut filter.
	 *
	 * @param tbodyId the tbody id
	 * @return the batch id from batch to pay with O ut filter
	 * @throws Exception the exception
	 */
	public String getBatchIdFromBatchToPayWithOUtFilter(String tbodyId) throws Exception{
		WebElement batchIdElement;
		String batchId; 
		try {
		batchIdElement=driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[2]"));
		wait.waitTillElementVisible(driver, 60, batchIdElement);
		batchId=batchIdElement.getText();
		}catch (StaleElementReferenceException e) {
			batchIdElement=driver.findElement(By.xpath("//tbody[@id='"+tbodyId+"']/tr[1]/td[2]"));
			wait.waitTillElementVisible(driver, 60, batchIdElement);
			batchId=batchIdElement.getText();
		}
		return batchId;
	}
	
	/**
	 * Gets the batch id from paid with filter.
	 *
	 * @param tableId the table id
	 * @return the batch id from paid with filter
	 * @throws Exception the exception
	 */
	public String getBatchIdFromPaidWithFilter(String tableId) throws Exception{
		wait.waitTilCountBecameOne(driver, 60, "//table[@id='"+tableId+"']/tbody/tr");
		WebElement batchIdElement;
		batchIdElement=driver.findElement(By.xpath("//table[@id='"+tableId+"']/tbody/tr[1]/td[1]"));
		wait.waitTillElementVisible(driver, 60, batchIdElement);
		return batchIdElement.getText();
	}

	/**
	 * Click on view batch.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnViewBatch() throws Exception{
		wait.waitForSeconds(2);
		WebElement viewBatchIcon;
		try {
		viewBatchIcon=driver.findElement(By.cssSelector(".btn.btn-xs.btn-info.claim-list-btn"));
		wait.waitTillElementVisible(driver, 60, viewBatchIcon);
		viewBatchIcon.click();
		}catch (StaleElementReferenceException e) {
			viewBatchIcon=driver.findElement(By.cssSelector(".btn.btn-xs.btn-info.claim-list-btn"));
			wait.waitTillElementVisible(driver, 60, viewBatchIcon);
			viewBatchIcon.click();
		}
		wait.waitForSeconds(2);
	}
	
	/**
	 * Click on EFT payment button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnEFTPaymentButton() throws Exception{
		WebElement eftPaymentButton=driver.findElement(By.xpath("//button[contains(text(),'EFT Payment')]"));
		wait.waitTillElementVisible(driver, 60, eftPaymentButton);
		eftPaymentButton.click();
		log.logInfo("Clicked on EFT payment button.");
	}
	
	/**
	 * Click on sign off button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSignOffButton() throws Exception{
		WebElement eftPaymentButton=driver.findElement(By.xpath("//button[contains(text(),'Sign Off')]"));
		wait.waitTillElementVisible(driver, 60, eftPaymentButton);
		eftPaymentButton.click();
		log.logInfo("Clicked on Sign Off button.");
	}
	
	/**
	 * Click EFT payment checkbox.
	 *
	 * @throws Exception the exception
	 */
	public void clickEFTPaymentCheckbox() throws Exception{
		WebElement eftPaymentCheckbox=driver.findElement(By.xpath("//input[@value='ERA']"));
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='ERA']")));
		eftPaymentCheckbox.click();
		log.logInfo("Clicked on EFT payment checkbox.");
		wait.waitForSeconds(1);
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='BatchToPayTbody']/tr[1]/td")));
	}
	
	/**
	 * Click on signed off checkbox.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSignedOffCheckbox() throws Exception{
		WebElement signedOffCheckbox=driver.findElement(By.xpath("//input[@value='SIGNED']"));
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='SIGNED']")));
		signedOffCheckbox.click();
		log.logInfo("Clicked on Signed off checkbox.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='BatchToPaySignedTbody']/tr[1]/td")));
	}

	/**
	 * Click on sent for payment checkbox.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSentForPaymentCheckbox() throws Exception{
		WebElement sentForPaymentCheckbox=driver.findElement(By.xpath("//input[@value='GENERATED']"));
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='GENERATED']")));
		sentForPaymentCheckbox.click();
		log.logInfo("Clicked on sent for payment checkbox.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='BatchToPayTbody']/tr[1]/td")));
	}
	
	
	/**
	 * Click on move to paid button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnMoveToPaidButton() throws Exception{
		WebElement btnMoveToPaid=driver.findElement(By.xpath("//button[contains(text(),'Move to Paid')]"));
		wait.waitTillElementVisible(driver, 60, btnMoveToPaid);
		btnMoveToPaid.click();
		log.logInfo("Clciked on Move To Paid button.");
	}
	
	/**
	 * Click on send for payment button.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSendForPaymentButton() throws Exception{
		WebElement btnSendForPayment=driver.findElement(By.xpath("//button[contains(text(),'Send for Payment')]"));
		wait.waitTillElementVisible(driver, 60, btnSendForPayment);
		btnSendForPayment.click();
		log.logInfo("Clciked on Send for Payment button.");
		wait.getWaitDriver(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Download')]")));
	}	
	
	/**
	 * Click on download in doownload GP files.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnDownloadInDoownloadGPFiles() throws Exception{
		WebElement lnkDownload=driver.findElement(By.xpath("//span[contains(text(),'Download')]"));
		js.clickOnElement(lnkDownload);
		log.logInfo("Clicked on Download GP file option.");
	}
	
	/**
	 * Checks if is download GP file model present.
	 *
	 * @return true, if is download GP file model present
	 * @throws Exception the exception
	 */
	public boolean isDownloadGPFileModelPresent() throws Exception{
		 try{
			 driver.findElement(By.id("closeModal"));
			 log.logInfo("Download GP file model is present.");
	            return true;
	        }
	        catch(NoSuchElementException e){
	        	log.logError("Download GP file model is not present.");
	            return false;
	        }
	}
	
	/**
	 * Close download GP file model.
	 *
	 * @throws Exception the exception
	 */
	public void closeDownloadGPFileModel() throws Exception{
		 driver.findElement(By.id("closeModal")).click();
		 log.logInfo("Model got closed");
	}
	
	/**
	 * Checks if is EOP letter present.
	 *
	 * @return true, if is EOP letter present
	 * @throws Exception the exception
	 */
	public boolean isEOPLetterPresent() throws Exception{
		 try{
			WebElement eopLetterEle= driver.findElement(By.xpath("//a[contains(text(),'EOP Letter')]"));
			 wait.waitTillElementVisible(driver, 60, eopLetterEle);
			 log.logInfo("EOP Letter is present.");
	            return true;
	        }
	        catch(NoSuchElementException e){
	        	log.logError("EOP letter is not present.");
	            return false;
	        }
	}
	
	/**
	 * Checks if is 835 file present.
	 *
	 * @return true, if is 835 file present
	 * @throws Exception the exception
	 */
	public boolean is835FilePresent() throws Exception{
		 try{
			WebElement FileEle= driver.findElement(By.xpath("//a[contains(text(),'835 File')]"));
			 wait.waitTillElementVisible(driver, 60, FileEle);
			 log.logInfo("835 file is present.");
	            return true;
	        }
	        catch(NoSuchElementException e){
	        	log.logError("835 file  is not present.");
	            return false;
	        }
	}
	
	/**
	 * Checks if is IDN letter present.
	 *
	 * @return true, if is IDN letter present
	 * @throws Exception the exception
	 */
	public boolean isIDNLetterPresent() throws Exception{
		 try{
			 WebElement idnLetter=driver.findElement(By.xpath("//a[contains(text(),'IDN Letter')]"));
			 wait.waitTillElementVisible(driver, 60, idnLetter);
			 log.logInfo("IDN Letter is present.");
	            return true;
	        }
	        catch(NoSuchElementException e){
	        	log.logError("IDN Letter  is not present.");
	            return false;
	        }
	}
	
	
	/**
	 * Gets the size.
	 *
	 * @param element the element
	 * @return the size
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public int getSize(String element) throws Exception{
		return driver.findElements(By.xpath(element)).size();
	}
	
	/**
	 * Select reason to pend and description
	 *
	 * @throws Exception the exception
	 */
	public void selectReasonToPend() throws Exception{
		WebElement txtBoxreason=driver.findElement(By.id("select2-ReasonCategoryIDModal-container"));
		wait.waitTillElementVisible(driver, 60, txtBoxreason);
		txtBoxreason.click();
		WebElement reason=driver.findElement(By.xpath("//ul[@id='select2-ReasonCategoryIDModal-results']/li[1]"));
		wait.waitTillElementVisible(driver, 60, reason);
		reason.click();
		driver.findElement(By.id("ReasonCategoryDescriptionModal_validation")).sendKeys("Reason for moving to pend bucket from prebatch");
		log.logInfo("Provided reason and description for pend");
	}
	
	/**
	 * Click on add button in reason to pend.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnAddInReasonToPend() throws Exception{
		WebElement btnAdd=driver.findElement(By.id("sendToStatus_btn"));
		wait.waitTillElementVisible(driver, 60, btnAdd);
		js.clickOnElement(btnAdd);
		//btnAdd.click();
		log.logInfo("Clicked on Add button");
	}
	
	/**
	 * Select reason to management review.
	 *
	 * @throws Exception the exception
	 */
	public void selectReasonToManagementReview() throws Exception{
		WebElement txtBoxreason=driver.findElement(By.id("select2-ReasonCategoryIDModal-container"));
		wait.waitTillElementVisible(driver, 60, txtBoxreason);
		txtBoxreason.click();
		WebElement reason=driver.findElement(By.xpath("//ul[@id='select2-ReasonCategoryIDModal-results']/li[1]"));
		wait.waitTillElementVisible(driver, 60, reason);
		reason.click();
		driver.findElement(By.id("ReasonCategoryDescriptionModal_validation")).sendKeys("Reason for moving to Management review bucket from prebatch");
		log.logInfo("Provided reason and description for management review");
	}
	
	/**
	 * Click mngt review in reason to management review.
	 *
	 * @throws Exception the exception
	 */
	public void clickMngtReviewInReasonToManagementReview() throws Exception{
		WebElement btnAdd=driver.findElement(By.id("sendToStatus_btn"));
		wait.waitTillElementVisible(driver, 60, btnAdd);
		js.clickOnElement(btnAdd);
		//btnAdd.click();
		log.logInfo("Clicked on Management review button in reason for management review pop up");
	}
	
	/**
	 * Select reason to payer review.
	 *
	 * @throws Exception the exception
	 */
	public void selectReasonToPayerOrMgmtReview() throws Exception{
		WebElement txtBoxreason=driver.findElement(By.id("select2-ReasonCategoryIDModal-container"));
		wait.waitTillElementVisible(driver, 60, txtBoxreason);
		txtBoxreason.click();
		WebElement reason=driver.findElement(By.xpath("//ul[@id='select2-ReasonCategoryIDModal-results']/li[1]"));
		wait.waitTillElementVisible(driver, 60, reason);
		reason.click();
		driver.findElement(By.id("ReasonCategoryDescriptionModal_validation")).sendKeys("Reason for moving to Payer review bucket from prebatch");
		log.logInfo("Provided reason and description for payer review");
	}
	
	
	/**
	 * Click payer review in reason to payer review.
	 *
	 * @throws Exception the exception
	 */
	public void clickReviewInReasonToPayerOrMgmtReview() throws Exception{
		WebElement btnAdd=driver.findElement(By.xpath("//form[@id='Adj_Reason_Category_form']/div/div/div[3]/button[1]"));
		//WebElement btnAdd=driver.findElement(By.id("sendToStatus_btn"));
		wait.waitTillElementVisible(driver, 60, btnAdd);
		//js.clickOnElement(btnAdd);
		btnAdd.click();
		log.logInfo("Clicked on review button in reason for payer review pop up");
	}
}
