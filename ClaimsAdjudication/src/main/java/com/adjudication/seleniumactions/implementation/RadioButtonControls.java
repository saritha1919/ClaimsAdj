package com.adjudication.seleniumactions.implementation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.adjudication.seleniumactions.contracts.IRadioButtonControls;



public class RadioButtonControls implements IRadioButtonControls{

	@Override
	public void selectByName(WebDriver driver, String name,List<WebElement> radios) throws Exception {
	
		    for (WebElement radio : radios) {
		        if (radio.getText().equals(name)) {
		            radio.click();
		        }
		    }
	}

}
