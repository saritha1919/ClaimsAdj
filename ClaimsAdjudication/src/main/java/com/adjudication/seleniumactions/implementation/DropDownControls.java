package com.adjudication.seleniumactions.implementation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Optional;

import com.adjudication.seleniumactions.contracts.IdropDown;
import com.adjudication.utils.WaitUtility;



public class DropDownControls implements IdropDown{

	int count=0;
	WaitUtility wait= new WaitUtility();
	public Select getdropdown(WebElement element){
		return new Select(element);
	}
	
	
	@Override
	public void selectViaVisibleText(WebElement element, String visibleText) throws Exception {
		getdropdown(element).selectByVisibleText(visibleText);
	}
	
	@Override
	public void selectViaVisibleTextFromList(WebDriver driver,String xpath, String visibleText) throws Exception {
		List<WebElement> elements=driver.findElements(By.xpath(xpath));
		System.out.println("drop down count:"+elements.size());
		try {
		for (WebElement webElement : elements) {
				if(webElement.getText().toLowerCase().contains(visibleText.toLowerCase())){
					wait.waitForSeconds(2);
					webElement.click();
					count=1;
					break;
				}
			}
		}catch (StaleElementReferenceException e) {
			elements=driver.findElements(By.xpath(xpath));
					for (WebElement webElement : elements) {
				if(webElement.getText().contains(visibleText)){
					wait.waitForSeconds(2);
					webElement.click();
					count=1;
					break;
				}
					}
			}
		if(count==0) {
			throw new Exception("Value is not present in drop down");
		}
	}
	
	@Override
	public void selectViaVisibleIndexFromList(List<WebElement> elements, int index) throws Exception {
			elements.get(index).click();
	}

	@Override
	public void selectViaValue(WebElement element, String value) throws Exception {
		getdropdown(element).selectByValue(value);
		
	}

	@Override
	public void selectViaIndex(WebElement element, int index) throws Exception {
		getdropdown(element).selectByIndex(index);
		
	}

	@Override
	public boolean isMultiple(WebElement element) throws Exception {
		return getdropdown(element).isMultiple();
	}

	@Override
	public List<WebElement> getAllOptions(WebElement element) throws Exception {
		return getdropdown(element).getOptions();
	}

	@Override
	public List<WebElement> getAllSelectedOptions(WebElement element) throws Exception {
		return getdropdown(element).getAllSelectedOptions();
	}

	@Override
	public WebElement getFirstSelectedOption(WebElement element) throws Exception {
		return getdropdown(element).getFirstSelectedOption();
	}

}
