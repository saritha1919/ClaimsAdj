package com.adjudication.pages.viewclaims.serviceDetails;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class ServiceDetailsPage extends BasePage{

	MyLogger log = new MyLogger(ServiceDetailsPage.class.getName());
	
	@FindBy(xpath="//tbody[@id='serviceLinesTBody']/tr[1]/td[21]/button[1]")
	@CacheLookup
	WebElement btnExpandServDetails;
	
	@FindBy(xpath="//tbody[@id='serviceLinesTBody']/tr[1]/td[22]/button[1]")
	@CacheLookup
	WebElement btnExpandServDetailsInInsti;
	
	@FindBy(xpath="//tbody[@id='serviceLinesTBody']/tr[1]/td[22]/button[1]")
	@CacheLookup
	WebElement btnExpandServDetailsInDental;
	
	public ServiceDetailsPage(WebDriver driver) {
		super(driver);
	}

	JavaScriptExecutor js= new JavaScriptExecutor(driver);
	WaitUtility wait= new WaitUtility();

	/**
	 * This method is used to Click on expand button in service details.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnExpandBtn() throws Exception{
		js.scrollIntoView(btnExpandServDetails);
		wait.waitTillElementBecomeClickable(driver, 40, btnExpandServDetails);
		btnExpandServDetails.click();
		log.logInfo("Clicked on Sevice expand button");
	}
	
	/**
	 * Click on expand btn in insti.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnExpandBtnInInsti() throws Exception{
		js.scrollIntoView(btnExpandServDetailsInInsti);
		wait.waitTillElementVisible(driver, 40, btnExpandServDetailsInInsti);
		btnExpandServDetailsInInsti.click();
		log.logInfo("Clicked on Sevice expand button");
	}

	/**
	 * Click on expand btn in dental.
	 *
	 * @throws Exception the exception
	 */
	public void clickOnExpandBtnInDental() throws Exception{
		js.scrollIntoView(btnExpandServDetailsInDental);
		wait.waitTillElementBecomeClickable(driver, 40, btnExpandServDetailsInDental);
		btnExpandServDetailsInDental.click();	
		log.logInfo("Clicked on Sevice expand button");
	}
	
	
}
