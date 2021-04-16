package com.adjudication.test.home;

import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.adjudication.base.PropertyFile;
import com.adjudication.base.TestBase;
import com.adjudication.drivermanager.DriverManager;
import com.adjudication.pages.ProfessionalClaim.CreateProClaimPage;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.home.CLHomePage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.test.login.LoginTest;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;


public class HomePageTest extends TestBase{
	
	MyLogger log= new MyLogger(LoginTest.class.getName());
	
	LinkedHashMap<String, String> filterData;
	
	LoginPage loginpage;
	AdjHomePage homePage;
	CLHomePage clHomePage;
	CreateProClaimPage createProClaimPage;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	GenericMethods genericMethods;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(){
		try {
			
			loginpage= new LoginPage(DriverManager.getWebDriver());
			elementsPropForSidebar= new PropertyFile(environment.ElementsInAdjSideMenuPath());
			elementsProp=new PropertyFile(environment.CommonElementsPath());
			createProClaimPage= new CreateProClaimPage(DriverManager.getWebDriver());
			genericMethods= new GenericMethods(DriverManager.getWebDriver());
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}
		
	}
	
	
	/**
	 * This method is used to keep Common actions across test cases in perticular class.
	 * 
	 * @author Saritha
	 */
	public void commonActions() {
		try {
			homePage=loginpage.loginByPassingCred(environment.username(), environment.password());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void navigateToClaimsLite() throws Exception{
	     try {
	    	 commonActions();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}	
	}

}
