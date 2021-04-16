package com.adjudication.test.login;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.adjudication.base.PropertyFile;
import com.adjudication.base.TestBase;
import com.adjudication.drivermanager.DriverManager;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.reports.TestListener;
import com.adjudication.utils.MyLogger;

@Listeners(TestListener.class)
public class LoginTest extends TestBase{

	MyLogger log= new MyLogger(LoginTest.class.getName());
	
	List<String> mandatoryMessages;
	LoginPage loginpage;
	PropertyFile msgProp;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(){
		try {
			
			loginpage= new LoginPage(DriverManager.getWebDriver());
			msgProp=new PropertyFile(environment.MsgPropertyPath());
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * This menthod is used to test login functionality with valid credentials.
	 * 
	 * @author Saritha Modiam
	 */
	@Test(groups = { "SmokeTest" })
	public void loginWithValidCred(){
		//Printing method name or test case name in logger file.
		log.logInfo("loginWithValidCred");
		try {
			loginpage.loginByPassingCred(environment.username(), environment.password());
			Assert.assertTrue(loginpage.isLoginSuccessfull(environment.username()));
			log.logInfo("Verified login functionality.");
		} catch (Exception e) {
			e.printStackTrace();
		Assert.fail(e.getMessage());
		log.logError(e.getMessage());
		}
	}
	
	/**
	 * 
	 * This method is used to test if user tries to login with out providing username, password.
	 * @author Saritha Modiam
	 */
	@Test(groups = { "RegressionTest" })
	public void loginWithEmptyFields(){
		log.logInfo("loginWithEmptyFields");
		try {
			loginpage.enterUsernamePassword("", "");
			loginpage.clickOnSubmitButton();
			mandatoryMessages= new ArrayList<>();
			System.out.println("Message:"+msgProp.getProperty("USERNAME"));
			mandatoryMessages.add(msgProp.getProperty("USERNAME"));
			mandatoryMessages.add(msgProp.getProperty("PASSWORD"));
			Assert.assertTrue(loginpage.checkLogInMandatoryMessage(mandatoryMessages));
			
		} catch (Exception e) {
			e.printStackTrace();
		Assert.fail(e.getMessage());
		log.logError(e.getMessage());
		
		}
	}
	
	/**
	 * 
	 * This method is used to test if user tries to login with invalid credentails.
	 * 
	 * @author Saritha Modiam
	 */
	@Test(groups = { "RegressionTest" })
	public void loginWithInvalidCred(){
		log.logInfo("loginWithInvalidCred");
		try {
			loginpage.enterUsernamePassword("abc@test.com","adcd");
			loginpage.clickOnSubmitButton();
			Assert.assertTrue(loginpage.waitAndCheckForWrongUsernameAndPasswordMessage(msgProp.getProperty("WRONG_USERNAME_PASSWORD")));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());//if we don't write this test case is treating like pass even tough it is failed.
			log.logError(e.getMessage());
		}
	}

	/**
	 * 
	 * This method is used to test if user tries to login with out providing password.
	 * 
	 * @author Saritha Modiam
	 */
	@Test(groups = { "RegressionTest" })
	public void loginWithOutPassword(){
		log.logInfo("loginWithOutPassword");
		try {
			loginpage.enterUsernamePassword(environment.username(),"");
			loginpage.clickOnSubmitButton();
			mandatoryMessages= new ArrayList<>();
			mandatoryMessages.add(msgProp.getProperty("PASSWORD"));
			Assert.assertTrue(loginpage.checkLogInMandatoryMessage(mandatoryMessages));
			
		} catch (Exception e) {
			e.printStackTrace();
		Assert.fail(e.getMessage());
		log.logError(e.getMessage());
		}
	}
	
	/**
	 *
	 * This method is used to test if user tries to login with out username
	 * 
	 * @author Saritha Modiam
	 */
	@Test(groups = { "RegressionTest" })
	public void loginWithOutUsername(){
		log.logInfo("loginWithOutUsername");
		try {
			loginpage.enterUsernamePassword("","abcd");
			loginpage.clickOnSubmitButton();
			mandatoryMessages= new ArrayList<>();
			//getting message from MandatoryMessageInLogin enum
			mandatoryMessages.add(msgProp.getProperty("USERNAME"));
			Assert.assertTrue(loginpage.checkLogInMandatoryMessage(mandatoryMessages));
			
		} catch (Exception e) {
			e.printStackTrace();
		Assert.fail(e.getMessage());
		log.logError(e.getMessage());
		}
	}
	
	/**
	 *
	 * This method is used to test if user tries to login with incorrect email format
	 * 
	 * @author Saritha Modiam
	 */
	
	@Test(groups = { "RegressionTest" })
	public void loginWithIncorrectEmailAdd() {
		log.logInfo("loginWithOutUsername");
		try {
			loginpage.enterUsernamePassword("srajagopal", "");
			loginpage.clickOnSubmitButton();
			mandatoryMessages = new ArrayList<>();
			// getting message from MandatoryMessageInLogin enum
			mandatoryMessages.add(msgProp.getProperty("WRONG_USERNAME"));
			Assert.assertTrue(loginpage.checkLogInMandatoryMessage(mandatoryMessages));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}
}
