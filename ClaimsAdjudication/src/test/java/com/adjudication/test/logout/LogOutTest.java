package com.adjudication.test.logout;

import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.adjudication.base.TestBase;
import com.adjudication.drivermanager.DriverManager;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.utils.MyLogger;


public class LogOutTest extends TestBase{
	
MyLogger log= new MyLogger(LogOutTest.class.getName());
	
	List<String> mandatoryMessages;
	LoginPage loginpage;
	AdjHomePage adjHomePage;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(){
		try {
			
			loginpage= new LoginPage(DriverManager.getWebDriver());
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}
		
	}
	
	@Test(groups = { "SmokeTest" })
	public void logOutTest() {
		try {
			loginpage.loginByPassingCred(environment.username(), environment.password());
			adjHomePage= new AdjHomePage(DriverManager.getWebDriver());
			adjHomePage.clickOnLogOut();
		    Assert.assertTrue(loginpage.isLoginPagePresent());
		} catch (Exception e) {
		}
	}

}
