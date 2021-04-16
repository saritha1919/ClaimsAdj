package com.adjudication.seleniumactions.contracts;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ICheckbox {
	
	public void selectCheckBoxByName( WebDriver driver,  String visibleText,List<WebElement> elements) throws Exception;

}
