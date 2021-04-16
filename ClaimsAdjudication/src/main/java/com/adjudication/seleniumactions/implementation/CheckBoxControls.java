package com.adjudication.seleniumactions.implementation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.adjudication.seleniumactions.contracts.ICheckbox;

import net.bytebuddy.asm.Advice.Exit;

public class CheckBoxControls implements ICheckbox{

	public void selectCheckBoxByName(WebDriver driver, String visibleText, List<WebElement> elements) throws Exception {
		for (WebElement webElement : elements) {
			if(webElement.getText().contains(visibleText)){
				webElement.click();
				break;
			}
		}
		
	}

}
