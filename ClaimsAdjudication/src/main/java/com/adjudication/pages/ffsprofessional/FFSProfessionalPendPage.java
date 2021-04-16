package com.adjudication.pages.ffsprofessional;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.contracts.IAction;
import com.adjudication.seleniumactions.contracts.IjavaScript;
import com.adjudication.seleniumactions.implementation.ActionControls;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.GenerateRandomData;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class FFSProfessionalPendPage extends BasePage {

	MyLogger log = new MyLogger(FFSProfessionalPendPage.class.getName());
	
	public FFSProfessionalPendPage(WebDriver driver) {
		super(driver);
	}
	
	
	WebElement viewIcon;
	String claimNumber;
	int rowNumber;
	int getTDCount;
	
	@FindBy(xpath="//table[@id='FFSPendQueTable']/tbody/tr/td[14]/a")
	@CacheLookup
	List<WebElement> imgViewIcons;
	
	@FindBy(xpath="//div[@id='loadingSample']")
	@CacheLookup
	WebElement imgLoadingSymbol;
	
	
	WaitUtility wait= new WaitUtility();
	IAction action= new ActionControls();
	GenerateRandomData random= new GenerateRandomData();
	IjavaScript js= new JavaScriptExecutor(driver);
	
	public String clickOnRandomViewIcon() throws Exception{
		wait.waitForSeconds(2);
		rowNumber=random.getRandomNumberWithRange(1,imgViewIcons.size());
		//if we have patient with out COB details count is 15 and with cob count is 14.
		getTDCount=driver.findElements(By.xpath("//table[@id='FFSPendQueTable']/tbody/tr["+rowNumber+"]/td")).size();
		claimNumber=driver.findElement(By.xpath("//table[@id='FFSPendQueTable']/tbody/tr["+rowNumber+"]/td[1]")).getText();
	    if(getTDCount==14) {
	    	viewIcon=driver.findElement(By.xpath("//table[@id='FFSPendQueTable']/tbody/tr["+rowNumber+"]/td[14]/a"));
	    }
	    else if(getTDCount==15) {
			viewIcon=driver.findElement(By.xpath("//table[@id='FFSPendQueTable']/tbody/tr["+rowNumber+"]/td[15]/a"));
		}
		wait.waitTillElementBecomeClickable(driver, 30, viewIcon);
		js.clickOnElement(viewIcon);
		//commonElements.click(viewIcon);
		wait.waitTillElementInvisible(driver,120, imgLoadingSymbol);
		return claimNumber;
	}
	
	
	
}
