package com.adjudication.seleniumactions.implementation;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.adjudication.seleniumactions.contracts.IAction;



public class ActionControls implements IAction{
	
	Actions action;

	private void getAction(WebDriver driver) {
		action= new Actions(driver);
	}
	
	public void moveToElementAndClick(WebElement element,WebDriver driver) throws Exception {
		getAction(driver);
		action.moveToElement(element).click().perform();
	}

	public void dragAndDrop(WebElement source, WebElement destination,WebDriver driver) throws Exception {
		Actions act = new Actions(driver);
		/*act.clickAndHold(source).build().perform();
		act.moveToElement(destination).build().perform();
		act.release(destination).build().perform();*/
		act.dragAndDropBy(source, 200, 10);
	}

	
}
