package com.adjudication.test.viewclaims.dental.deny;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.adjudication.base.PropertyFile;
import com.adjudication.base.TestBase;
import com.adjudication.drivermanager.DriverManager;
import com.adjudication.pages.CommonActionsAcrossPages;
import com.adjudication.pages.QueueManagementPage;
import com.adjudication.pages.dental.DentalPage;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.home.SideMenuPage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.pages.viewclaims.ViewClaimsPage;
import com.adjudication.reports.TestListener;
import com.adjudication.utils.Excel;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

@Listeners(TestListener.class)
public class DenyToPrebatchFromDentalTest extends TestBase {

	MyLogger log = new MyLogger(DenyToPrebatchFromDentalTest.class.getName());

	String id ;
	String popUpMsg;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	PropertyFile messageProp;
	PropertyFile idProp;
	CommonActionsAcrossPages commonActions;
	Excel excel;
	SoftAssert softAssert;

	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	DentalPage dental;
	ViewClaimsPage viewClaimsPage;
	QueueManagementPage QMPage;
	GenericMethods genericMethods = new GenericMethods(DriverManager.getWebDriver());
	WaitUtility wait = new WaitUtility();

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		try {
			
			elementsPropForSidebar = new PropertyFile(environment.ElementsInAdjSideMenuPath());
			elementsProp = new PropertyFile(environment.CommonElementsPath());
			excel = new Excel(environment.ExcelPath());
			idProp=new PropertyFile(environment.ClaimIdsFilePath());
			messageProp = new PropertyFile(environment.MsgPropertyPath());
			commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
			id=idProp.getProperty("DenyToPrebatchFromDentalTest");
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}

	}

	public void commonSteps() throws Exception {
		softAssert = new SoftAssert();
		loginpage = new LoginPage(DriverManager.getWebDriver());
		homePage = loginpage.loginByPassingCred(environment.username(), environment.password());
		sideMenu = new SideMenuPage(DriverManager.getWebDriver());
		// Click on FFS Professional from side menu
		sideMenu.clickOnDental();
		dental=new DentalPage(DriverManager.getWebDriver());
		dental.clickOnDenied();
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("DENTAL_DENIED_TABLE_ID"),1,id);
		// System.out.println("claim number:"+ffsSearchPage.clickOnViewIcon());
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		QMPage.clickOnViewClaims(elementsProp.getProperty("DENTAL_DENIED_TABLE_ID"));
		wait.waitForSeconds(2);
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
	}

	@Test(priority = 1, groups = { "Regression" })
	public void tryToMoveDenyFromApprovedFromCAPInsti() throws Exception {
		try {
			commonSteps();
			viewClaimsPage.clickOnPrebatchButton();
			viewClaimsPage.clickOnPrebatchPayButton();
			popUpMsg = messageProp.getProperty("PREBATCH_PAY_WARNING_MESSAGE");
			softAssert.assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "Regression" })
	public void tryToMovePrebatchFromApprovedFromCAPInsti() throws Exception {
		try {
			commonSteps();
			viewClaimsPage.clickOnPrebatchButton();
			viewClaimsPage.clickOnPrebatchDenyButton();
			popUpMsg = messageProp.getProperty("PREBATCH_APPROVE_SUCCESS_MSG1");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popUpMsg, id, 70)));
			dental = new DentalPage(DriverManager.getWebDriver());
			dental.clickOnPreBatch();
			// clicking on filter and providing the data.
			commonActions.filterData(elementsProp.getProperty("DENTAL_PREBATCH_TABLE_ID"),2,id);
			wait.waitForSeconds(3);
			QMPage = new QueueManagementPage(DriverManager.getWebDriver());
			softAssert.assertEquals(id,
					QMPage.getClaimNumberFromPrebatch(elementsProp.getProperty("DENTAL_PREBATCH_TABLE_ID")));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}
}
