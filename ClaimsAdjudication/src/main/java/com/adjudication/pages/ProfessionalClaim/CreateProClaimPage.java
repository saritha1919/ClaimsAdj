package com.adjudication.pages.ProfessionalClaim;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.UploadFile;
import com.adjudication.utils.WaitUtility;

public class CreateProClaimPage extends BasePage{

	public CreateProClaimPage(WebDriver driver) {
		super(driver);
	}
	MyLogger log= new MyLogger(CreateProClaimPage.class.getName());
	
	int timeoutInSeconds=30;
	
	@FindBy(id="uploadfaicon")
	@CacheLookup
	WebElement iconUpload;
	
	WaitUtility wait= new WaitUtility();
	UploadFile file= new UploadFile();
	
	public void clickOnUploadIcon() throws Exception{
		wait.waitTillElementVisible(driver, timeoutInSeconds, iconUpload);
		//driver.findElement(By.id("uploadfaicon")).sendKeys("D:\\CADocuments\\INSTI-female_ICD_onmale_2.txt");
		iconUpload.click();
		file.uploadFile("D:\\CADocuments\\INSTI-female_ICD_onmale_2.txt");
	}

}
