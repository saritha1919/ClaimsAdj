package com.adjudication.pages.login;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import com.adjudication.pages.BasePage;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;


public class LoginPage extends BasePage{

	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	MyLogger log= new MyLogger(LoginPage.class.getName());
	
	String userLogin="Sign in To Your Account";
	
	@FindBy(id="userId")
	@CacheLookup
	WebElement txtUserName;
	
	@FindBy(id="Password")
	@CacheLookup
	WebElement txtPassword;
	
	@FindBy(id="btnSubmit")
	@CacheLookup
	WebElement btnSubmit;
	
	@FindBy(xpath="//div[@class='form-horizontal']/fieldset/p")
	@CacheLookup
	WebElement txtOnloginPage;
	
	@FindBy(xpath="//li[@class='dropdown']/a")
	@CacheLookup
	WebElement txtLoggedInUserName;
	
	@FindBy(xpath="//div[@class='validation-summary-errors']/ul/li")
	@CacheLookup
	WebElement wrongUsernamePassword;
	
	@FindBy(id="loadingSample")
	@CacheLookup
	WebElement imgLoading;
	
	WaitUtility wait= new WaitUtility();

	/**
	 * Checks if is login page present.
	 *
	 * @return true, if is login page present
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public boolean isLoginPagePresent() throws Exception{
		wait.waitTillElementVisible(driver, 10, txtOnloginPage);
		return txtOnloginPage.getText().equalsIgnoreCase(userLogin)?true:false;
	}
	
	/**
	 * 
	 * This method is used for login into application by passing emailid and password. 
	 * User will navigate to home page.
	 *
	 * @param userName the email id of the user
	 * @param password the password of the user
	 * @throws Exception the exception
	 * 
	 * @author Saritha Modiam
	 */
	public AdjHomePage loginByPassingCred(String userName,String password) throws Exception{
		enterUsernamePassword(userName,password);
		clickOnSubmitButton();
		wait.waitTillElementInvisible(driver, 30, imgLoading);
		//wait.waitForSeconds(5);
		return new AdjHomePage(driver);
	}
	
	/**
	 * Enter username,password.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public void enterUsernamePassword(String userName,String password) throws Exception{
		wait.waitTillElementVisible(driver, 60, txtUserName);
		txtUserName.sendKeys(userName);
		wait.waitTillElementVisible(driver, 60, txtPassword);
		txtPassword.sendKeys(password);
		log.logInfo("Provided Username and  password as "+userName+","+password);
	}
	
	/**
	 * Click on submit button.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnSubmitButton() throws Exception{
		btnSubmit.click();
		log.logInfo("Clicked on Submit button");
	}
	
	/**
	 * 
	 * This method is used for checking whether login is successfull or not.
	 *
	 * @return the true if user login succesfully.
	 * @throws Exception the exception
	 * 
	 * @author Saritha Modiam
	 */
	public boolean isLoginSuccessfull(String userName) throws Exception{
		wait.waitTillElementVisible(driver, 60, txtLoggedInUserName);
		userName=userName.toLowerCase();
		return userName.contains(txtLoggedInUserName.getText().toLowerCase())?true:false;	
	}
	
	/**
	 * 
	 * This menthod we will use for checking mandatory messages in login page by passing expecting mandatory messages.
	 *
	 * @param messages the List of mandatory messages which are expecting to test.
	 * @return true, if all messages are present in the page.
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public boolean checkLogInMandatoryMessage(List<String> messages) throws Exception{
		int check = 0;
		boolean isPresent=false;
		try {
			for (int i = 0; i < messages.size(); i++) {
				if(driver.getPageSource().contains(messages.get(i))){
					check++;
				}
			}
			if(check==messages.size()){
				isPresent=true;
				log.logInfo("verified mandatory messages");
			}
		} catch (Exception e) {
			log.logError(e.getMessage());
		}
		return isPresent;	
	}
	
	/**
	 * 
	 *This method is used to check whether warning message is getting displayed or not if user provide wrong username and passeord.
	 *
	 * @param warningMessage the warning message which we are expecting to check.
	 * @return true, if warning message present in the screen
	 * @throws Exception the exception
	 * 
	 * @author Saritha Modiam
	 */
	public boolean waitAndCheckForWrongUsernameAndPasswordMessage(String warningMessage) throws Exception{
	   wait.waitTillElementVisible(driver, 20, wrongUsernamePassword);	
	   boolean isPresent=false;
		String message=wrongUsernamePassword.getText();
		if(message.equals(warningMessage)){
			isPresent=true;
			log.logInfo("verified invalid login attempt message");
		}
		return isPresent;
	}
	
}
