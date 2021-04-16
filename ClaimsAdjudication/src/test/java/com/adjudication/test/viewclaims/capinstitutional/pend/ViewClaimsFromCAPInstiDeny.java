package com.adjudication.test.viewclaims.capinstitutional.pend;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.adjudication.base.PropertyFile;
import com.adjudication.base.TestBase;
import com.adjudication.drivermanager.DriverManager;
import com.adjudication.pages.CommonActionsAcrossPages;
import com.adjudication.pages.QueueManagementPage;
import com.adjudication.pages.capinstitutional.CAPInstitutionalPage;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.home.SideMenuPage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.pages.viewclaims.ViewClaimsPage;
import com.adjudication.pages.viewclaims.serviceDetails.ServiceDetailsExpansionPage;
import com.adjudication.pages.viewclaims.serviceDetails.ServiceDetailsPage;
import com.adjudication.utils.Excel;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class ViewClaimsFromCAPInstiDeny extends TestBase {

	MyLogger log = new MyLogger(ViewClaimsFromCAPInstiDeny.class.getName());

	String id;
	int totalButtonsInServiceLinePage;
	List<String> warningMsgs;
	String popUpMsg;
	Excel excel;
	SoftAssert softAssert;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	PropertyFile messageProp;
	PropertyFile idProp;
	
	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	CAPInstitutionalPage capInsti;
	CommonActionsAcrossPages commonActions;
	ViewClaimsPage viewClaimsPage;
	ServiceDetailsPage serviceDetailsPage;
	QueueManagementPage QMPage;
	ServiceDetailsExpansionPage serviceDetailExpPage;
	
	
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
			id=idProp.getProperty("ViewClaimsFromCAPInstiDeny");
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}

	}

	public void commonSteps() throws Exception {
		softAssert = new SoftAssert();
		loginpage = new LoginPage(DriverManager.getWebDriver());
		homePage = loginpage.loginByPassingCred(environment.username(), environment.password());
		sideMenu=new SideMenuPage(DriverManager.getWebDriver());
		// Click on FFS Professional from side menu
		sideMenu.clickOnCAPInsti();
		// clicking on filter and providing the data.
		commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
		commonActions.filterData(elementsProp.getProperty("CAPINSTI_PEND_TABLE_ID"),1,id);
		// System.out.println("claim number:"+ffsSearchPage.clickOnViewIcon());
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		QMPage.clickOnViewClaims(elementsProp.getProperty("CAPINSTI_PEND_TABLE_ID"));
		wait.waitForSeconds(2);
	}

	@Test(priority = 1, groups = { "Regression" })
	public void moveToApproveFromCAPInstiPendNegTC() {
		try {
			log.logInfo(
					"Verify warning messages if claim is moving from pend bucket to Approve bucket with status as Deny from CAP Insti pend");
			commonSteps();
			viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
			viewClaimsPage.clickOnApporveButton();
			popUpMsg = messageProp.getProperty("APPROVE_WARNING_MESSAGE");
			assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "Regression" })
	public void moveToDenyFromFFSInstiPendPositiveTC() {
		try {
			log.logInfo(
					"Verify claim is moving from pend to Deny bucket with status as deny from CAP institutional Pend.");
			commonSteps();
			viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
			viewClaimsPage.clickOnDenyButton();
			popUpMsg = messageProp.getProperty("DENY_SUCCESS_MESSAGE");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popUpMsg, id, 70)));
			wait.waitForSeconds(2);
			capInsti=new CAPInstitutionalPage(DriverManager.getWebDriver());
			capInsti.clickOnDenied();
			// clicking on filter and providing the data.
			commonActions.filterData(elementsProp.getProperty("CAPINSTI_DENIED_TABLE_ID"),1,id);
			softAssert.assertEquals(id, QMPage.getClaimNumber(elementsProp.getProperty("CAPINSTI_DENIED_TABLE_ID")));
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

}
