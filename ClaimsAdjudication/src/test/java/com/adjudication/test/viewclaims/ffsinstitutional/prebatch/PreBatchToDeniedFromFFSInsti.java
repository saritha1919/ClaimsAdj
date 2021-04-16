package com.adjudication.test.viewclaims.ffsinstitutional.prebatch;

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
import com.adjudication.pages.ffsinstitutional.FFSInstitutionalPage;
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
public class PreBatchToDeniedFromFFSInsti extends TestBase{

	MyLogger log = new MyLogger(PreBatchToDeniedFromFFSInsti.class.getName());

	String id ;
	String popUpMsg;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	PropertyFile queryProp;
	PropertyFile messageProp;
	PropertyFile idProp;
	CommonActionsAcrossPages commonActions;
	Excel excel;
	SoftAssert softAssert;

	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	FFSInstitutionalPage ffsInsti;
	ViewClaimsPage viewClaimsPage;
	QueueManagementPage QMPage;
	GenericMethods genericMethods = new GenericMethods(DriverManager.getWebDriver());
	WaitUtility wait = new WaitUtility();

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		try {
			
			elementsPropForSidebar = new PropertyFile(environment.ElementsInAdjSideMenuPath());
			queryProp = new PropertyFile(environment.QueryFilePath());
			elementsProp = new PropertyFile(environment.CommonElementsPath());
			idProp=new PropertyFile(environment.ClaimIdsFilePath());
			excel = new Excel(environment.ExcelPath());
			messageProp = new PropertyFile(environment.MsgPropertyPath());
			commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
			id=idProp.getProperty("PreBatchToDeniedFromFFSInsti");
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
		sideMenu.clickOnFFSInsti();
		ffsInsti = new FFSInstitutionalPage(DriverManager.getWebDriver());
		ffsInsti.clickOnPreBatch();
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("FFSINSTI_PREBATCH_TABLE_ID"),2,id);
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
        QMPage.clickOnViewClaims(elementsProp.getProperty("FFSINSTI_PREBATCH_TABLE_ID"));
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
	}

	@Test(priority = 1,groups = { "Regression" })
	public void moveToDenyFromPrebatchNegTC() throws Exception {
		try {
			commonSteps();
			wait.waitForSeconds(2);
			viewClaimsPage.clickOnApporveButton();
			popUpMsg = messageProp.getProperty("APPROVE_WARNING_MSG_STATUS_PEND");
			softAssert.assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}
	
	@Test(priority = 2,groups = { "Regression" })
	public void moveToDenyFromPrebatchPositiveTC() throws Exception {
		try {
			commonSteps();
			wait.waitForSeconds(3);
			viewClaimsPage.clickOnDenyButton();
			popUpMsg = messageProp.getProperty("DENY_SUCCESS_MESSAGE");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popUpMsg, id, 70)));
			ffsInsti.clickOnDenied();
			// clicking on filter and providing the data.
			commonActions.filterData(elementsProp.getProperty("FFSINSTI_DENIED_TABLE_ID"),1,id);
			softAssert.assertEquals(id, QMPage.getClaimNumber(elementsProp.getProperty("FFSINSTI_DENIED_TABLE_ID")));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}
}
