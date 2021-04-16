package com.adjudication.drivermanager;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	//protected WebDriver driver;
	
	public abstract void createWebDriver();
	
	public void quitWebDriver(){
		if(driver!=null){
			driver.get().quit();
			driver=null;
		}
	}
	
	public static void setDriver(WebDriver driverRef) {
		driver.set(driverRef);
	}
	
	public static WebDriver getWebDriver(){
	    //createWebDriver();
		return driver.get();
	}

}
