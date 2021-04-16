package com.adjudication.pages.viewclaims.serviceDetails;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.contracts.IdropDown;
import com.adjudication.seleniumactions.implementation.DropDownControls;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class ServiceDetailsExpansionPage extends BasePage {

	public ServiceDetailsExpansionPage(WebDriver driver) {
		super(driver);
	}

	String groupCodedropDownXpath;
	String reasonCodedropDownXpath;
	int dropDownxpathNumber;
	int servicePageNumberForDropDownXpath;
	MyLogger log = new MyLogger(ServiceDetailsExpansionPage.class.getName());
	boolean isPresent = false;
	int buttonNumber;
	int totalButtons;
	int totalRowsInServiceLine;
	int totalCancelBtnInServiceLine;
	int check = 0;
	boolean isMsgPresent=false;
    int totalWarningMessages;
	
	String txtDropDownStatus;
	WebElement cancelBtn;
	WebElement addBtn;
    WebElement txtBoxReason;
	
	@FindBy(xpath = "//*[@id=\"ProfProceduresSection\"]/div[2]/div[2]/div[3]/button[2]")
	@CacheLookup
	WebElement btnSaveValidate;

	@FindBy(xpath = "//*[@id=\"InstServicelineModalFotter\"]/div[2]/div[3]/button[2]")
	@CacheLookup
	WebElement btnSaveValidateInsti;
	
	@FindBy(xpath = "//div[@id='DentalServicalDetailModalFotter']/div[2]/div[3]/button[2]")
	@CacheLookup
	WebElement btnSaveValidateDental;

	@FindBy(xpath = "//fieldset[@id='ProfProceduresSection']/div[2]/div[2]/div[2]/button")
	@CacheLookup
	List<WebElement> lstOfButtons;

	@FindBy(xpath = "//div[@id='InstServicelineModalFotter']/div[2]/div[2]/button")
	@CacheLookup
	List<WebElement> lstOfButtonsInsti;
	
	@FindBy(xpath = "//div[@id='DentalServicalDetailModalFotter']/div[2]/div[2]/button")
	@CacheLookup
	List<WebElement> lstOfButtonsDental;
	
	
	@FindBy(xpath = "//div[@id='loadingSample']")
	@CacheLookup
	WebElement loadingSymbol;

	@FindBy(xpath = "//div[@class='alert ui-pnotify-container alert-success ui-pnotify-shadow']/h4")
	@CacheLookup
	WebElement suceessPopUp;

	@FindBy(xpath = "ui-pnotify-text")
	@CacheLookup
	List<WebElement> warningPopUps;
	
	JavaScriptExecutor js = new JavaScriptExecutor(driver);
	WaitUtility wait = new WaitUtility();
	IdropDown dropDown=new DropDownControls();

	/**
	 * Checks if is next button present.
	 *
	 * @return true, if is next button present
	 */
	public boolean isNextButtonPresent() {
		try {
			WebElement ele = driver
					.findElement(By.xpath("//button[@class='btn btn-xs  nextbuttonfooter serviceLineNav pull-left']"));
			if (js.getDisplyValue(ele) == "block") {
				isPresent = true;
			}
			return isPresent;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}


	/**
	 * This method is used to Click on save validate.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSaveValidatePro() throws Exception {
		btnSaveValidate.click();
		wait.waitTillElementInvisible(driver, 120, loadingSymbol);
	}
	
	/**
	 * This method is used to Click on save validate.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSaveValidateInsti() throws Exception {
		btnSaveValidateInsti.click();
		//wait.waitForSeconds(2);
		wait.waitTillElementInvisible(driver, 120, loadingSymbol);
	}

	
	/**
	 * This method is used to Click on save validate.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnSaveValidateDental() throws Exception {
		btnSaveValidateDental.click();
		wait.waitTillElementInvisible(driver, 120, loadingSymbol);
	}
	/**
	 * This method is used to Wait and check for success pop up.
	 *
	 * @param sucessMsg the sucess msg
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean waitAndCheckForSuccessPopUp(String sucessMsg) throws Exception {
		wait.waitTillElementVisible(driver, 60, suceessPopUp);
		boolean isPresent = false;
		String message = suceessPopUp.getText();
		if (message.equals(sucessMsg)) {
			isPresent = true;
			log.logInfo(sucessMsg);
		}
		wait.waitTillElementInvisible(driver, 120, suceessPopUp);
		return isPresent;
	}

	/**
	 * Gets the no of service lines count.
	 *
	 * @return the no of service lines count
	 * @throws Exception the exception
	 */
	public int getNoOfServiceLinesCountPro() throws Exception {
		wait.waitTillElementsVisible(driver, 60, lstOfButtons);
		return lstOfButtons.size();
	}
	
	/**
	 * Gets the no of service lines count for institutional
	 *
	 * @return the no of service lines count
	 * @throws Exception the exception
	 */
	public int getNoOfServiceLinesCountInInsti() throws Exception {
		wait.waitTillElementsVisible(driver, 60, lstOfButtonsInsti);
		return lstOfButtonsInsti.size();
	}
	
	/**
	 * Gets the no of service lines count for Dental
	 *
	 * @return the no of service lines count
	 * @throws Exception the exception
	 */
	public int getNoOfServiceLinesCountInDental() throws Exception {
		wait.waitTillElementsVisible(driver, 60, lstOfButtonsDental);
		return lstOfButtonsDental.size();
	}
	
	public int getNumOfRowsInAdjustmentTable(int servicePageNum) throws Exception{
		List<WebElement> NumOfRowsInAdjustmentTable=driver.findElements(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr"));
		wait.waitTillElementsVisible(driver, 60,NumOfRowsInAdjustmentTable );
		return NumOfRowsInAdjustmentTable.size();
	}
	
	/**
	 * This method is used to Nav to next service line page.
	 *
	 * @param totalButtons      the total buttons
	 * @param currentPageNumber the current page number
	 * @throws Exception the exception
	 */
	public void navToNextpage(int totalButtons, int currentPageNumber) throws Exception {
		buttonNumber = currentPageNumber + 1;
		// if we have only one button no need of clicking on next button. it will not be
		// present.
		// After last button also no need of clickin on next button. it will not be
		// present.
		if (totalButtons > 1 && totalButtons != currentPageNumber) {
			driver.findElement(By.id("modalfooterbutton_" + buttonNumber + "")).click();
		}
	}

	/**
	 * Check and remove CO 45.
	 *
	 * @throws Exception the exception
	 */
	public void checkAndRemoveCO45(int serviceLinePageNumber) throws Exception {
			//total rows present in table.
			totalRowsInServiceLine = driver.findElements(By.xpath("(//table[@id='AdjustementTable'])[" + serviceLinePageNumber + "]/tbody/tr")).size();
				//total cancel buttons present in table.
				totalCancelBtnInServiceLine = driver.findElements(By.xpath("(//table[@id='AdjustementTable'])[" + serviceLinePageNumber + "]/tbody/tr/td[6]/i")).size();
				//if we have only one record in table we will not have cancel button.
				if (totalCancelBtnInServiceLine >= 2) {
					for (int j = 1; j <=totalCancelBtnInServiceLine; j++) {
						String groupCode=driver.findElement(By.xpath("((//table[@id='AdjustementTable'])["+serviceLinePageNumber+"]/tbody/tr/td[1]/span/span/span/span[1])["+j+"]")).getText();
						String reasonCode=driver.findElement(By.xpath("((//table[@id='AdjustementTable'])["+serviceLinePageNumber+"]/tbody/tr/td[2]/span/span/span/span[1])["+j+"]")).getText();
						if(groupCode.equals("CO") && reasonCode.equals("45")) {
							cancelBtn = driver.findElement(By.xpath("(//table[@id='AdjustementTable'])[" + serviceLinePageNumber+ "]/tbody/tr[" + j + "]/td[6]/i"));
							wait.waitTillElementVisible(driver, 60, cancelBtn);
							cancelBtn.click();
							break;
						}
					}
		
				} 
				//if we have one record we have click on add button to get cancel button.
				else {
					addBtn = driver.findElement(By.xpath("(//table[@id='AdjustementTable'])[" + serviceLinePageNumber + "]/tbody/tr[" + totalRowsInServiceLine + "]/td[5]/a"));
					wait.waitTillElementVisible(driver, 60, addBtn);
					addBtn.click();
					wait.waitForSeconds(2);
					//to change the focus from cancel button to reason textbox. to perform click operation cancel icon is hiding from tool tip.
					txtBoxReason=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+serviceLinePageNumber+"]/tbody/tr[2]/td[5]/input"));
					wait.waitTillElementVisible(driver, 30, txtBoxReason);
					//wait.waitTillElementVisible(driver, 30, cancelBtn);
					js.clickOnElement(txtBoxReason);
					//txtBoxReason.click();
					cancelBtn = driver.findElement(By.xpath("(//table[@id='AdjustementTable'])[" + serviceLinePageNumber + "]/tbody/tr[1]/td[6]/i"));
					js.clickOnElement(cancelBtn);
				}
		}
	
	/**
	 * This method is used to select Click and select status.
	 *
	 * @param pageNumber the page number
	 * @param status the status
	 * @throws Exception the exception
	 */
	public void selectStatus(int pageNumber,String status) throws Exception{
		boolean isPresent = false;
		int count=0;
		// clicking of status drop down in service details.
		driver.findElement(By.id("select2-select_paymentstatus"+pageNumber+"-container")).click();
		wait.waitForSeconds(1);
		// provide status value in drop down search. stale element exception is coming
		// if we provide xpath in POM style.
			txtDropDownStatus = "//ul[@id='select2-select_paymentstatus"+pageNumber+"-results']/li";
			while (!isPresent) {
				int statusValueCount=driver.findElements(By.xpath(txtDropDownStatus)).size();
				if(statusValueCount>=3) {
					dropDown.selectViaVisibleTextFromList(driver, txtDropDownStatus, status);
					isPresent=true;
				}
				count++;
				if(count==10) {
					break;
				}
			}
			
		log.logInfo("Provided status as "+status);
	}
	

	
	/**
	 * This method is used to Check warning messages.
	 *
	 * @param messages the messages
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkWarningMessages(List<String> messages) throws Exception {
		totalWarningMessages=messages.size();
		wait.waitTillElementsVisible(driver, 60, warningPopUps);
		List<String> expectedWarningMsgs=new ArrayList<String>();
		expectedWarningMsgs.add(driver.findElement(By.xpath("(//div[@class='ui-pnotify-text'])[1]")).getText());
		expectedWarningMsgs.add(driver.findElement(By.xpath("(//div[@class='ui-pnotify-text'])[2]")).getText());
		if(warningPopUps.size()>2) {
			expectedWarningMsgs.add(driver.findElement(By.xpath("(//div[@class='ui-pnotify-text'])[3]")).getText());
		}
		for (int i = 0; i <expectedWarningMsgs.size(); i++) {
			System.out.println("war msg1:"+expectedWarningMsgs.get(i));
			for (int j = 0; j < totalWarningMessages; j++) {
				if (expectedWarningMsgs.get(i).equalsIgnoreCase(messages.get(j))) {
					System.out.println("msg:"+messages.get(j));
				     check++;	
				     break;
				}
			}
		}
		if(check==totalWarningMessages) {
			isMsgPresent=true;
			log.logInfo("Warning Messages are validated");
		}
		
		return isMsgPresent;
	}
	
	/**
	 * Click on add button.
	 *
	 * @param serviceLinePageNum the service line page num
	 * @param lastRowNum the last row num
	 */
	public void clickOnAddButton(int serviceLinePageNum,int lastRowNum) {
		WebElement addButton=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+serviceLinePageNum+"]/tbody/tr["+lastRowNum+"]/td[5]/a"));
		wait.waitTillElementVisible(driver, 60, addButton);
		addButton.click();
	}
	
	/**
	 * Sets the adjustment info insti.
	 *
	 * @param groupCode the group code
	 * @param reasonCode the reason code
	 * @param adjustmentAmount the adjustment amount
	 * @param servicePageNum the service page num
	 * @param rowNum the row num
	 * @throws Exception the exception
	 */
	public void setAdjustmentInfoInsti(String groupCode,String reasonCode,int adjustmentAmount,int servicePageNum,int rowNum) throws Exception{
		WebElement groupCodeTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[1]/span/span/span"));
		wait.waitTillElementVisible(driver, 60, groupCodeTextbox);
		groupCodeTextbox.click();
		//because by default we will have one row. we are going to add by clicking on add new so row number will become 2 but we xpath will start from 1.
		dropDownxpathNumber=rowNum-1;
		//because xpath is statring with zero but service page number is 1. that's we are doing minus 1
		servicePageNumberForDropDownXpath=servicePageNum-1;
		//initially we will get loading.., after that data will display. so we are waiting to load data.every time we have to check 
		//data loaded or not. that's why we are passing xapth and checking in wait method with some interval of time preriod.
		groupCodedropDownXpath="//ul[@id='select2-Claims_Institutional_837_ServiceLine_Institutional_837_"+servicePageNumberForDropDownXpath+"__Adjustments_"+dropDownxpathNumber+"__AdjustmentGroupcode-results']/li";
		System.out.println("group code xpath"+groupCodedropDownXpath);
		wait.waitTilCountChanges(driver,30,groupCodedropDownXpath);
	    //here we passing xpath because if we pass webelement sometimes data not loaded yet. that's why we are loading in dropdown class.
	    dropDown.selectViaVisibleTextFromList(driver,groupCodedropDownXpath,groupCode);
		WebElement reasonCodeTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[2]/span/span/span/span/span"));
		reasonCodedropDownXpath="//ul[@id='select2-Claims_Institutional_837_ServiceLine_Institutional_837_"+servicePageNumberForDropDownXpath+"__Adjustments_"+dropDownxpathNumber+"__AdjustmentReason-results']/li";
		System.out.println("group code xpath"+reasonCodedropDownXpath);
		wait.waitTillElementVisible(driver, 60, reasonCodeTextbox);
		reasonCodeTextbox.click();
		 wait.waitTilCountChanges(driver,30,reasonCodedropDownXpath);
		dropDown.selectViaVisibleTextFromList(driver,reasonCodedropDownXpath,reasonCode);
		WebElement adjustmentTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[4]/input"));
		wait.waitTillElementVisible(driver, 60, adjustmentTextbox);
		adjustmentTextbox.click();
		adjustmentTextbox.sendKeys(String.valueOf(adjustmentAmount));
		log.logInfo("Provided "+groupCode+reasonCode+adjustmentAmount);
	}
	
	/**
	 * Sets the adjustment info pro.
	 *
	 * @param groupCode the group code
	 * @param reasonCode the reason code
	 * @param adjustmentAmount the adjustment amount
	 * @param servicePageNum the service page num
	 * @param rowNum the row num
	 * @throws Exception the exception
	 */
	public void setAdjustmentInfoPro(String groupCode,String reasonCode,int adjustmentAmount,int servicePageNum,int rowNum) throws Exception{
		wait.waitForSeconds(2);
		WebElement groupCodeTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[1]/span/span/span"));
		wait.waitTillElementVisible(driver, 60, groupCodeTextbox);
		groupCodeTextbox.click();
		//because by default we will have one row. we are going to add by clicking on add new so row number will become 2 but we xpath will start from 1.
		dropDownxpathNumber=rowNum-1;
		//because xpath is statring with zero but service page number is 1. that's we are doing minus 1
		servicePageNumberForDropDownXpath=servicePageNum-1;
		//initially we will get loading.., after that data will display. so we are waiting to load data.every time we have to check 
		//data loaded or not. that's why we are passing xapth and checking in wait method with some interval of time preriod.
		groupCodedropDownXpath="//ul[@id='select2-Claims_Professional_ServiceLine_Professional_837_"+servicePageNumberForDropDownXpath+"__Adjustments_"+dropDownxpathNumber+"__AdjustmentGroupcode-results']/li";
		wait.waitTilCountChanges(driver,30,groupCodedropDownXpath);
	    //here we passing xpath because if we pass webelement sometimes data not loaded yet. that's why we are loading in dropdown class.
	    dropDown.selectViaVisibleTextFromList(driver,groupCodedropDownXpath,groupCode);
		WebElement reasonCodeTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[2]/span/span/span/span/span"));
		reasonCodedropDownXpath="//ul[@id='select2-Claims_Professional_ServiceLine_Professional_837_"+servicePageNumberForDropDownXpath+"__Adjustments_"+dropDownxpathNumber+"__AdjustmentReason-results']/li";
		wait.waitTillElementVisible(driver, 60, reasonCodeTextbox);
		reasonCodeTextbox.click();
		 wait.waitTilCountChanges(driver,30,reasonCodedropDownXpath);
		dropDown.selectViaVisibleTextFromList(driver,reasonCodedropDownXpath,reasonCode);
		WebElement adjustmentTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[4]/input"));
		wait.waitTillElementVisible(driver, 60, adjustmentTextbox);
		adjustmentTextbox.click();
		adjustmentTextbox.sendKeys(String.valueOf(adjustmentAmount));
		WebElement ReasonTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[5]/input"));
		wait.waitTillElementVisible(driver, 60, ReasonTextbox);
		ReasonTextbox.click();
		log.logInfo("Provided "+groupCode+reasonCode+adjustmentAmount);
	}

	
	/**
	 * Sets the adjustment info dental.
	 *
	 * @param groupCode the group code
	 * @param reasonCode the reason code
	 * @param adjustmentAmount the adjustment amount
	 * @param servicePageNum the service page num
	 * @param rowNum the row num
	 * @throws Exception the exception
	 */
	public void setAdjustmentInfoDental(String groupCode,String reasonCode,int adjustmentAmount,int servicePageNum,int rowNum) throws Exception{
		WebElement groupCodeTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[1]/span/span/span"));
		wait.waitTillElementVisible(driver, 60, groupCodeTextbox);
		groupCodeTextbox.click();
		//because by default we will have one row. we are going to add by clicking on add new so row number will become 2 but we xpath will start from 1.
		dropDownxpathNumber=rowNum-1;
		//because xpath is statring with zero but service page number is 1. that's we are doing minus 1
		servicePageNumberForDropDownXpath=servicePageNum-1;
		//initially we will get loading.., after that data will display. so we are waiting to load data.every time we have to check 
		//data loaded or not. that's why we are passing xapth and checking in wait method with some interval of time preriod.
		groupCodedropDownXpath="//ul[@id='select2-Claims_Dental_837_ServiceLine2400_837_224A2_"+servicePageNumberForDropDownXpath+"__Adjustments_"+dropDownxpathNumber+"__AdjustmentGroupcode-results']/li";
		//initially we will get loading.., after that data will display. so we are waiting to load data.every time we have to check 
		//data loaded or not. that's why we are passing xapth and checking in wait method with some interval of time preriod.
		System.out.println("goup code xpath:"+groupCodedropDownXpath);
	    wait.waitTilCountChanges(driver,30,groupCodedropDownXpath);
	    //here we passing xpath because if we pass webelement sometimes data not loaded yet. that's why we are loading in dropdown class.
	    dropDown.selectViaVisibleTextFromList(driver,groupCodedropDownXpath,groupCode);
		WebElement reasonCodeTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[2]/span/span/span/span/span"));
		wait.waitTillElementVisible(driver, 60, reasonCodeTextbox);
		reasonCodeTextbox.click();
		reasonCodedropDownXpath="//ul[@id='select2-Claims_Dental_837_ServiceLine2400_837_224A2_"+servicePageNumberForDropDownXpath+"__Adjustments_"+dropDownxpathNumber+"__AdjustmentReason-results']/li";
		 wait.waitTilCountChanges(driver,30,reasonCodedropDownXpath);
		dropDown.selectViaVisibleTextFromList(driver,reasonCodedropDownXpath,reasonCode);
		WebElement adjustmentTextbox=driver.findElement(By.xpath("(//table[@id='AdjustementTable'])["+servicePageNum+"]/tbody/tr["+rowNum+"]/td[4]/input"));
		wait.waitTillElementVisible(driver, 60, adjustmentTextbox);
		adjustmentTextbox.click();
		adjustmentTextbox.clear();
		adjustmentTextbox.sendKeys(String.valueOf(adjustmentAmount));
		log.logInfo("Provided "+groupCode+reasonCode+adjustmentAmount);
	}
	
	/**
	 * Gets the claim line amount pro.
	 *
	 * @param pageNumber the page number
	 * @return the claim line amount pro
	 * @throws Exception the exception
	 */
	public int getClaimLineAmountPro(int pageNumber) throws Exception{
		String claimLineAmount=driver.findElement(By.xpath("(//input[@id='Claim_Line'])["+pageNumber+"]")).getAttribute("value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(claimLineAmount.substring(0,claimLineAmount.length()-3));
		
	}
	
	/**
	 * Gets the total adjust amount pro.
	 *
	 * @param pageNumber the page number
	 * @return the total adjust amount pro
	 * @throws Exception the exception
	 */
	public int getTotalAdjustAmountPro(int pageNumber) throws Exception{
		//to get total adjustment amount id in elements starting with zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Professional.ServiceLine_Professional_837["+textBoxid+"].AdjudicationAmount.TotalAdjustmentAmount";
		//we have to add return keyword to the script to get output result.
		String totalAdjustAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(totalAdjustAmount.substring(0,totalAdjustAmount.length()-3));
		
	}
	
	/**
	 * Gets the deductible amount pro.
	 *
	 * @param pageNumber the page number
	 * @return the deductible amount pro
	 * @throws Exception the exception
	 */
	public int getDeductibleAmountPro(int pageNumber) throws Exception{
		//to get deductable amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Professional.ServiceLine_Professional_837["+textBoxid+"].AdjudicationAmount.TotalDeductibleAmount";
		//we have to add return keyword to the script to get output result.
		String deductibleAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(deductibleAmount.substring(0,deductibleAmount.length()-3));
		
	}
	
	/**
	 * Gets the coinsurance amount pro.
	 *
	 * @param pageNumber the page number
	 * @return the coinsurance amount pro
	 * @throws Exception the exception
	 */
	public int getCoinsuranceAmountPro(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Professional.ServiceLine_Professional_837["+textBoxid+"].AdjudicationAmount.TotalCoInsuranceAmount";
		//we have to add return keyword to the script to get output result.
		String coinsuranceAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(coinsuranceAmount.substring(0,coinsuranceAmount.length()-3));
		
	}
	
	/**
	 * Gets the copay amount pro.
	 *
	 * @param pageNumber the page number
	 * @return the copay amount pro
	 * @throws Exception the exception
	 */
	public int getCopayAmountPro(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Professional.ServiceLine_Professional_837["+textBoxid+"].AdjudicationAmount.TotalCopayAmount";
		//we have to add return keyword to the script to get output result.
		String copayAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(copayAmount.substring(0,copayAmount.length()-3));
		
	}
	
	/**
	 * Gets the total payment amount pro.
	 *
	 * @param pageNumber the page number
	 * @return the total payment amount pro
	 * @throws Exception the exception
	 */
	public int getTotalPaymentAmountPro(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Professional.ServiceLine_Professional_837["+textBoxid+"].AdjudicationAmount.TotalPaymentAmount";
		//we have to add return keyword to the script to get output result.
		String copayAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(copayAmount.substring(0,copayAmount.length()-3));
		
	}
	
	/**
	 * Gets the claim line amount insti.
	 *
	 * @param pageNumber the page number
	 * @return the claim line amount insti
	 * @throws Exception the exception
	 */
	public int getClaimLineAmountInsti(int pageNumber) throws Exception{
		String claimLineAmount=driver.findElement(By.xpath("(//input[@id='Claim_Line'])["+pageNumber+"]")).getAttribute("value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(claimLineAmount.substring(0,claimLineAmount.length()-3));
		
	}
	
	/**
	 * Gets the total adjust amount insti.
	 *
	 * @param pageNumber the page number
	 * @return the total adjust amount insti
	 * @throws Exception the exception
	 */
	public int getTotalAdjustAmountInsti(int pageNumber) throws Exception{
		//to get total adjustment amount id in elements starting with zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Institutional_837.ServiceLine_Institutional_837["+textBoxid+"].AdjudicationAmount.TotalAdjustmentAmount";
		//we have to add return keyword to the script to get output result.
		String totalAdjustAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(totalAdjustAmount.substring(0,totalAdjustAmount.length()-3));
		
	}
	
	/**
	 * Gets the deductible amount insti.
	 *
	 * @param pageNumber the page number
	 * @return the deductible amount insti
	 * @throws Exception the exception
	 */
	public int getDeductibleAmountInsti(int pageNumber) throws Exception{
		//to get deductable amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Institutional_837.ServiceLine_Institutional_837["+textBoxid+"].AdjudicationAmount.TotalDeductibleAmount";
		//we have to add return keyword to the script to get output result.
		String deductibleAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(deductibleAmount.substring(0,deductibleAmount.length()-3));
		
	}
	
	/**
	 * Gets the coinsurance amount insti.
	 *
	 * @param pageNumber the page number
	 * @return the coinsurance amount insti
	 * @throws Exception the exception
	 */
	public int getCoinsuranceAmountInsti(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Institutional_837.ServiceLine_Institutional_837["+textBoxid+"].AdjudicationAmount.TotalCoInsuranceAmount";
		//we have to add return keyword to the script to get output result.
		String coinsuranceAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(coinsuranceAmount.substring(0,coinsuranceAmount.length()-3));
		
	}
	
	/**
	 * Gets the copay amount insti.
	 *
	 * @param pageNumber the page number
	 * @return the copay amount insti
	 * @throws Exception the exception
	 */
	public int getCopayAmountInsti(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Institutional_837.ServiceLine_Institutional_837["+textBoxid+"].AdjudicationAmount.TotalCopayAmount";
		//we have to add return keyword to the script to get output result.
		String copayAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(copayAmount.substring(0,copayAmount.length()-3));
		
	}
	
	/**
	 * Gets the total payment amount insti.
	 *
	 * @param pageNumber the page number
	 * @return the total payment amount insti
	 * @throws Exception the exception
	 */
	public int getTotalPaymentAmountInsti(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Institutional_837.ServiceLine_Institutional_837["+textBoxid+"].AdjudicationAmount.TotalPaymentAmount";
		//we have to add return keyword to the script to get output result.
		String copayAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(copayAmount.substring(0,copayAmount.length()-3));
		
	}
	
	/**
	 * Gets the claim line amount dental.
	 *
	 * @param pageNumber the page number
	 * @return the claim line amount dental
	 * @throws Exception the exception
	 */
	public int getClaimLineAmountDental(int pageNumber) throws Exception{
		String claimLineAmount=driver.findElement(By.xpath("(//input[@id='Claim_Line'])["+pageNumber+"]")).getAttribute("value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(claimLineAmount.substring(0,claimLineAmount.length()-3));
		
	}
	
	/**
	 * Gets the total adjust amount dental.
	 *
	 * @param pageNumber the page number
	 * @return the total adjust amount dental
	 * @throws Exception the exception
	 */
	public int getTotalAdjustAmountDental(int pageNumber) throws Exception{
		//to get total adjustment amount id in elements starting with zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Dental_837.ServiceLine2400_837_224A2["+textBoxid+"].AdjudicationAmount.TotalAdjustmentAmount";
		//we have to add return keyword to the script to get output result.
		String totalAdjustAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(totalAdjustAmount.substring(0,totalAdjustAmount.length()-3));
		
	}
	
	/**
	 * Gets the deductible amount dental.
	 *
	 * @param pageNumber the page number
	 * @return the deductible amount dental
	 * @throws Exception the exception
	 */
	public int getDeductibleAmountDental(int pageNumber) throws Exception{
		//to get deductable amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Dental_837.ServiceLine2400_837_224A2["+textBoxid+"].AdjudicationAmount.TotalDeductibleAmount";
		//we have to add return keyword to the script to get output result.
		String deductibleAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(deductibleAmount.substring(0,deductibleAmount.length()-3));
		
	}
	
	/**
	 * Gets the coinsurance amount dental.
	 *
	 * @param pageNumber the page number
	 * @return the coinsurance amount dental
	 * @throws Exception the exception
	 */
	public int getCoinsuranceAmountDental(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Dental_837.ServiceLine2400_837_224A2["+textBoxid+"].AdjudicationAmount.TotalCoInsuranceAmount";
		//we have to add return keyword to the script to get output result.
		String coinsuranceAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(coinsuranceAmount.substring(0,coinsuranceAmount.length()-3));
		
	}
	
	/**
	 * Gets the copay amount dental.
	 *
	 * @param pageNumber the page number
	 * @return the copay amount dental
	 * @throws Exception the exception
	 */
	public int getCopayAmountDental(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Dental_837.ServiceLine2400_837_224A2["+textBoxid+"].AdjudicationAmount.TotalCopayAmount";
		//we have to add return keyword to the script to get output result.
		String copayAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(copayAmount.substring(0,copayAmount.length()-3));
		
	}
	
	/**
	 * Gets the total payment amount dental.
	 *
	 * @param pageNumber the page number
	 * @return the total payment amount dental
	 * @throws Exception the exception
	 */
	public int getTotalPaymentAmountDental(int pageNumber) throws Exception{
		//to get coinsurance amount id in elements starting woth zero. that's why we are doing minus 1 from page number.
		int textBoxid=pageNumber-1;
		String nameOfinputTag="Claims_Dental_837.ServiceLine2400_837_224A2["+textBoxid+"].AdjudicationAmount.TotalPaymentAmount";
		//we have to add return keyword to the script to get output result.
		String copayAmount=js.executeJavaScriptWithReturnValue("return document.getElementsByName(\""+nameOfinputTag+"\")[0].value");
		//output we will get like "10.00" we need to only value 10. so we are taking substring.
		return Integer.parseInt(copayAmount.substring(0,copayAmount.length()-3));
		
	}
	
	

}
