package com.adjudication.pages.globalsearch;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.contracts.IAction;
import com.adjudication.seleniumactions.implementation.ActionControls;
import com.adjudication.utils.GenerateRandomData;
import com.adjudication.utils.WaitUtility;

public class GlobalSearchPage extends BasePage{

	public GlobalSearchPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//table[@id='GlobalSearchQueueTable']/tbody/tr/td[15]/a")
	@CacheLookup
	List<WebElement> imgViewIcons;
	
	@FindBy(xpath="//div[@id='loadingSample']")
	@CacheLookup
	WebElement imgLoadingSymbol;	
	
	WaitUtility wait= new WaitUtility();
	IAction action= new ActionControls();
	GenerateRandomData random= new GenerateRandomData();
	
	/**
	 * Click on view icon.
	 *
	 * @return the string
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public String clickOnViewIcon() throws Exception{
		int number=random.getRandomNumberWithRange(1,imgViewIcons.size());
		WebElement viewIcon=driver.findElement(By.xpath("//table[@id='GlobalSearchQueueTable']/tbody/tr["+number+"]/td[15]/a"));
		String claimNumber=driver.findElement(By.xpath("//table[@id='GlobalSearchQueueTable']/tbody/tr["+number+"]/td[1]")).getText();
		wait.waitTillElementBecomeClickable(driver, 30, viewIcon);
		viewIcon.click();
		wait.waitTillElementInvisible(driver,120, imgLoadingSymbol);
		return claimNumber;
	}
}
