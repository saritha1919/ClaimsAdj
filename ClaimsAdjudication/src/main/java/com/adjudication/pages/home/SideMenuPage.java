package com.adjudication.pages.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.adjudication.pages.BasePage;
import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;


public class SideMenuPage extends BasePage {

	
	MyLogger log = new MyLogger(SideMenuPage.class.getName());

	public SideMenuPage(WebDriver driver) {
		super(driver);
	}
	
	WaitUtility wait = new WaitUtility();
	JavaScriptExecutor js = new JavaScriptExecutor(driver);

	String txtboxCliamNumber = "filter_textfield_0";
	
	@FindBy(xpath = "//a[@data-tab-action=\"FFS Professional\"]")
	@CacheLookup
	WebElement lnkFFsPro;

	@FindBy(xpath = "//a[@data-tab-action=\"CAP Professional\"]")
	@CacheLookup
	WebElement lnkCAPPro;

	@FindBy(xpath = "//a[@data-tab-action=\"FFS Institutional\"]")
	@CacheLookup
	WebElement lnkFFSInsti;

	@FindBy(xpath = "//a[@data-tab-action=\"CAP Institutional\"]")
	@CacheLookup
	WebElement lnkCAPInsti;

	@FindBy(xpath = "//a[@data-tab-action=\"Dental\"]")
	@CacheLookup
	WebElement lnkDental;
	
	@FindBy(id = "menu_toggle")
	@CacheLookup
	WebElement menuToggle;

	@FindBy(xpath = "(//li[@id=\"adjudicationsideMenu\"]/ul/li/a)[2]")
	@CacheLookup
	WebElement lnkGlobalSearch;

	
	/**
	 * Click on global search.
	 *
	 * @throws Exception the exception
	 * @author Saritha
	 */
	public void clickOnGlobalSearch()throws Exception {
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(lnkGlobalSearch));
		js.clickOnElement(lnkGlobalSearch);
		log.logInfo("Clicked on GlobalSearch in side menu");
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='GlobalSearchQueueTable']/tbody")));
	}
	
	/**
	 * Click on FFS pro.
	 *
	 * @throws Exception the exception
	 *  @author Saritha
	 */
	public void clickOnFFSPro() throws Exception {
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(lnkFFsPro));
		js.clickOnElement(lnkFFsPro);
		log.logInfo("Clicked on FFS Profressional in side menu");
		wait.getWaitDriver(driver, 120)
				.until(ExpectedConditions.presenceOfElementLocated(By.id(txtboxCliamNumber)));
		wait.waitForSeconds(1);
	}

	/**
	 * Click on CAP pro.
	 *
	 * @throws Exception the exception
	 *  @author Saritha
	 */
	public void clickOnCAPPro() throws Exception {
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(lnkCAPPro));
		js.clickOnElement(lnkCAPPro);
		log.logInfo("Clicked on CAP Profressional in side menu");
		wait.getWaitDriver(driver, 120)
				.until(ExpectedConditions.presenceOfElementLocated(By.id(txtboxCliamNumber)));
		wait.waitForSeconds(1);
	}

	/**
	 * Click on FFS insti.
	 *
	 * @throws Exception the exception
	 *  @author Saritha
	 */
	public void clickOnFFSInsti() throws Exception {
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(lnkFFSInsti));
		js.clickOnElement(lnkFFSInsti);
		log.logInfo("Clicked on FFS Insti in side menu");
		wait.getWaitDriver(driver, 120)
				.until(ExpectedConditions.presenceOfElementLocated(By.id(txtboxCliamNumber)));
		wait.waitForSeconds(1);
	}

	/**
	 * Click on CAP insti.
	 *
	 * @throws Exception the exception
	 *  @author Saritha
	 */
	public void clickOnCAPInsti() throws Exception {
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(lnkCAPInsti));
		js.clickOnElement(lnkCAPInsti);
		log.logInfo("Clicked on CAP Insti in side menu");
		wait.getWaitDriver(driver, 120)
				.until(ExpectedConditions.presenceOfElementLocated(By.id(txtboxCliamNumber)));
		wait.waitForSeconds(1);
	}

	/**
	 * Click on dental.
	 *
	 * @throws Exception the exception
	 *  @author Saritha
	 */
	public void clickOnDental() throws Exception {
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(lnkDental));
		js.clickOnElement(lnkDental);
		log.logInfo("Clicked on Dental in side menu");
		wait.getWaitDriver(driver, 120)
				.until(ExpectedConditions.presenceOfElementLocated(By.id(txtboxCliamNumber)));
		wait.waitForSeconds(1);
	}

	/**
	 * Click on menu toggle.
	 *
	 * @throws Exception the exception
	 *  @author Saritha
	 */
	public void clickOnMenuToggle() throws Exception{
		wait.getWaitDriver(driver, 120).until(ExpectedConditions.visibilityOf(menuToggle));
		js.clickOnElement(menuToggle);
	}
}
