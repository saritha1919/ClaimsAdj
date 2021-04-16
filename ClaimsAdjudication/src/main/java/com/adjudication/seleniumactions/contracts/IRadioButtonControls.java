package com.adjudication.seleniumactions.contracts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface IRadioButtonControls {

	public void selectByName ( WebDriver driver,  String name,java.util.List<WebElement> radios) throws Exception;
}
