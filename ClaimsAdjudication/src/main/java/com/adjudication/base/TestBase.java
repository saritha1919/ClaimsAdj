package com.adjudication.base;

import java.util.concurrent.TimeUnit;

import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.adjudication.config.Environment;
import com.adjudication.drivermanager.DriverManager;
import com.adjudication.drivermanager.DriverManagerFactory;
import com.adjudication.utils.MyLogger;


public class TestBase {
	
MyLogger log= new MyLogger(TestBase.class.getName());
	
	public Environment environment;
	DriverManager driverManager;
	
	public  void initialization() throws Exception {
		String browserType = environment.browser().trim();
		String url=environment.url().trim();
		driverManager=DriverManagerFactory.getDriverManager(browserType);
		driverManager.createWebDriver();
		DriverManager.getWebDriver().manage().deleteAllCookies();
		DriverManager.getWebDriver().manage().window().maximize();
		DriverManager.getWebDriver().get(url);
	}
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment"})
	public void beforeTest(@Optional("test")String environemnt) throws Exception {
       ConfigFactory.setProperty("env", environemnt);
       environment = ConfigFactory.create(Environment.class);
       initialization();
       PropertyConfigurator.configure("log4j.properties");
    }
	
	
	/*public void setUp() {
		try {
			initialization();
			//Loading log4j property file
			PropertyConfigurator.configure("log4j.properties");
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}
		
	}*/
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(){
		DriverManager.getWebDriver().quit();
	}

}
