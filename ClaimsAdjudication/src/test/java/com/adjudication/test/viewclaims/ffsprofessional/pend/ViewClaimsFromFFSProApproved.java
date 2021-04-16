package com.adjudication.test.viewclaims.ffsprofessional.pend;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import com.adjudication.pages.ffsprofessional.FFSProfessionalPage;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.home.SideMenuPage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.pages.viewclaims.ViewClaimsPage;
import com.adjudication.pages.viewclaims.serviceDetails.ServiceDetailsExpansionPage;
import com.adjudication.pages.viewclaims.serviceDetails.ServiceDetailsPage;
import com.adjudication.reports.TestListener;
import com.adjudication.utils.Excel;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

@Listeners(TestListener.class)
public class ViewClaimsFromFFSProApproved extends TestBase {

	MyLogger log = new MyLogger(ViewClaimsFromFFSProApproved.class.getName());

	String id;
	int totalButtonsInServiceLinePage;
	List<String> warningMsgs;
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
	FFSProfessionalPage ffsPro;
	ViewClaimsPage viewClaimsPage;
	ServiceDetailsPage serviceDetailsPage;
	ServiceDetailsExpansionPage serviceDetailExpPage;
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
			id=idProp.getProperty("ViewClaimsFromFFSProApproved");
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
		sideMenu.clickOnFFSPro();
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("FFSPRO_PEND_TABLE_ID"),1,id);
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		// System.out.println("claim number:"+ffsSearchPage.clickOnViewIcon());
		QMPage.clickOnViewClaims(elementsProp.getProperty("FFSPRO_PEND_TABLE_ID"));
		wait.waitForSeconds(2);
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
		viewClaimsPage.clickOnServiceDetails();
		serviceDetailsPage = new ServiceDetailsPage(DriverManager.getWebDriver());
		serviceDetailsPage.clickOnExpandBtn();
		serviceDetailExpPage = new ServiceDetailsExpansionPage(DriverManager.getWebDriver());
		totalButtonsInServiceLinePage = serviceDetailExpPage.getNoOfServiceLinesCountPro();
	}

	@Test(priority = 1, groups = { "Regression" })
	public void SaveValiForApporvedStatusFromFFSProPendNegTC() throws Exception {
		try {
			log.logInfo(
					"Verify warning message if user tries to save and validate with out co-45 information and status as Approve from FFS Professional pend");
			commonSteps();
			serviceDetailExpPage.isNextButtonPresent();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.checkAndRemoveCO45(i);
				serviceDetailExpPage.selectStatus(i, "Apporved");
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			serviceDetailExpPage.clickOnSaveValidatePro();
			warningMsgs = new ArrayList<String>();
			warningMsgs.add(messageProp.getProperty("SERVICE_WARNING_MSG_APPROVESTATUS"));
			warningMsgs.add(messageProp.getProperty("SERVICE_WARNING_MSG_APPROVESTATUS2"));
			assertTrue(serviceDetailExpPage.checkWarningMessages(warningMsgs));

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "Regression" })
	public void SaveValiForDenyStatusFromFFSProPendNegTC() throws Exception {
		log.logInfo(
				"Verify warning message if user tries to save and validate with co-45 info and sates as Deny from FFS Professional pend");
		commonSteps();
		for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
			serviceDetailExpPage.selectStatus(i, "Deny");
			wait.waitForSeconds(1);
			serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
		}
		serviceDetailExpPage.clickOnSaveValidatePro();
		warningMsgs = new ArrayList<String>();
		warningMsgs.add(messageProp.getProperty("SERVICE_WARNING_MSG_DENYSTATUS"));
		warningMsgs.add(messageProp.getProperty("SERVICE_WARNING_MSG_DENYSTATUS3"));
		assertTrue(serviceDetailExpPage.checkWarningMessages(warningMsgs));
	}

	@Test(priority = 3, groups = { "Regression" })
	public void moveToDenyFromFFSProPendNegTC() throws Exception {
		try {
			log.logInfo(
					"verify warning messages if claim is moving from pend bucket to deny bucket with status as approved from FFS Professional pend");
			commonSteps();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.selectStatus(i, "Approved");
				wait.waitForSeconds(1);
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			wait.waitForSeconds(2);
			serviceDetailExpPage.clickOnSaveValidatePro();
			// softAssert.assertTrue(serviceDetailExpPage.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			softAssert.assertTrue(serviceDetailExpPage
					.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			viewClaimsPage.clickOnDenyButton();
			popUpMsg = messageProp.getProperty("DENY_WARNING_MESSAGE");
			softAssert.assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 4, groups = { "Regression" })
	public void moveToApporveFromFFSProPendPositiveTC() throws Exception {
		try {
			log.logInfo(
					"Verify claim is moving from pend to approve bucket with status as approved from FFS Professional pend");
			commonSteps();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.selectStatus(i, "Approved");
				wait.waitForSeconds(1);
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			wait.waitForSeconds(2);
			serviceDetailExpPage.clickOnSaveValidatePro();
			// softAssert.assertTrue(serviceDetailExpPage.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			softAssert.assertTrue(serviceDetailExpPage
					.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			viewClaimsPage.clickOnApporveButton();
			popUpMsg = messageProp.getProperty("APPORVE_SUCCESS_MESSAGE_PRO");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popUpMsg, id, 70)));
			wait.waitForSeconds(2);
			ffsPro=new FFSProfessionalPage(DriverManager.getWebDriver());
			ffsPro.clickOnApproved();
			// clicking on filter and providing the data.
			commonActions.filterData(elementsProp.getProperty("FFSPRO_APPROVED_TABLE_ID"),1,id);
			softAssert.assertEquals(id, QMPage.getClaimNumber(elementsProp.getProperty("FFSPRO_APPROVED_TABLE_ID")));
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}

	}

}
