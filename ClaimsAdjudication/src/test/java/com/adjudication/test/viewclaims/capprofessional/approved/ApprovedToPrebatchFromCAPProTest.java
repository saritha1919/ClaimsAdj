package com.adjudication.test.viewclaims.capprofessional.approved;

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
import com.adjudication.pages.capprofessional.CAPProfessionalPage;
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
public class ApprovedToPrebatchFromCAPProTest extends TestBase {

	MyLogger log = new MyLogger(ApprovedToPrebatchFromCAPProTest.class.getName());

	String id;
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
	CAPProfessionalPage capPro;
	ViewClaimsPage viewClaimsPage;
	QueueManagementPage QMPage;
	GenericMethods genericMethods = new GenericMethods(DriverManager.getWebDriver());
	WaitUtility wait = new WaitUtility();

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		try {
			
			elementsPropForSidebar = new PropertyFile(environment.ElementsInAdjSideMenuPath());
			elementsProp = new PropertyFile(environment.CommonElementsPath());
			idProp=new PropertyFile(environment.ClaimIdsFilePath());
			excel = new Excel(environment.ExcelPath());
			messageProp = new PropertyFile(environment.MsgPropertyPath());
			commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
			id=idProp.getProperty("ApprovedToPrebatchFromCAPProTest");
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}

	}

	public void commonSteps() throws Exception {
		softAssert = new SoftAssert();
		loginpage = new LoginPage(DriverManager.getWebDriver());
		homePage = loginpage.loginByPassingCred(environment.username(), environment.password());
		// Click on FFS Professional from side menu
		sideMenu = new SideMenuPage(DriverManager.getWebDriver());
		sideMenu.clickOnCAPPro();
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		capPro=new CAPProfessionalPage(DriverManager.getWebDriver());
		capPro.clickOnApproved();
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("CAPPRO_APPROVED_TABLE_ID"),1,id);
		// System.out.println("claim number:"+ffsSearchPage.clickOnViewIcon());
		QMPage.clickOnViewClaims(elementsProp.getProperty("CAPPRO_APPROVED_TABLE_ID"));
		wait.waitForSeconds(2);
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
	}

	@Test(priority = 1, groups = { "Regression" })
	public void tryToMoveDenyFromApprovedFromCAPPro() throws Exception {
		try {
			commonSteps();
			viewClaimsPage.clickOnPrebatchButton();
			viewClaimsPage.clickOnPrebatchDenyButton();
			popUpMsg = messageProp.getProperty("PREBATCH_DENY_WARNING_MESSAGE");
			softAssert.assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "Regression" })
	public void tryToMovePrebatchFromApprovedFromCAPPro() throws Exception {
		try {
			commonSteps();
			viewClaimsPage.clickOnPrebatchButton();
			viewClaimsPage.clickOnPrebatchPayButton();
			popUpMsg = messageProp.getProperty("PREBATCH_APPROVE_SUCCESS_MSG");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popUpMsg, id, 70)));
			capPro=new CAPProfessionalPage(DriverManager.getWebDriver());
			capPro.clickOnPreBatch();
			commonActions.filterData(elementsProp.getProperty("CAPPRO_PREBATCH_TABLE_ID"),2,id);
			wait.waitForSeconds(3);
			softAssert.assertEquals(id,
					QMPage.getClaimNumberFromPrebatch(elementsProp.getProperty("CAPPRO_PREBATCH_TABLE_ID")));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}
}
