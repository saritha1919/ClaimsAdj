package com.adjudication.test.viewclaims.capprofessional.pend;

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
import com.adjudication.pages.capprofessional.CAPProfessionalPage;
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
public class ViewClaimsFromCAPProApproved extends TestBase {

	MyLogger log = new MyLogger(ViewClaimsFromCAPProApproved.class.getName());

	String id;
	int totalButtonsInServiceLinePage;
	List<String> warningMsgs;
	String popupMsg;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	PropertyFile messageProp;
	PropertyFile idProp;
	Excel excel;
	SoftAssert softAssert;

	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	CAPProfessionalPage capPro;
	CommonActionsAcrossPages commonActions;
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
			id=idProp.getProperty("ViewClaimsFromCAPProApproved");
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
		sideMenu.clickOnCAPPro();
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("CAPPRO_PEND_TABLE_ID"),1,id);
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		QMPage.clickOnViewClaims(elementsProp.getProperty("CAPPRO_PEND_TABLE_ID"));
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
		viewClaimsPage.clickOnServiceDetails();
		serviceDetailsPage = new ServiceDetailsPage(DriverManager.getWebDriver());
		serviceDetailsPage.clickOnExpandBtn();
		serviceDetailExpPage = new ServiceDetailsExpansionPage(DriverManager.getWebDriver());
		totalButtonsInServiceLinePage = serviceDetailExpPage.getNoOfServiceLinesCountPro();

	}

	@Test(priority = 1, groups = { "Regression" })
	public void SaveValiForApporvedStatusFromCAPProPendNegTC() throws Exception {
		try {
			log.logInfo(
					"Verify warning message if user tries to save and validate with out co-45 information and status as Approve from CAP Professional pend.");
			commonSteps();
			serviceDetailExpPage.isNextButtonPresent();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.checkAndRemoveCO45(i);
				serviceDetailExpPage.selectStatus(i, "Approved");
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
	public void SaveValiForDenyStatusFromCAPProPendNegTC() throws Exception {
		try {
			log.logInfo(
					"Verify warning message if user tries to save and validate with co-45 info and sates as Deny from CAP Professional pend.");
			commonSteps();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.selectStatus(i, "Deny");
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			serviceDetailExpPage.clickOnSaveValidatePro();
			warningMsgs = new ArrayList<String>();
			warningMsgs.add(messageProp.getProperty("SERVICE_WARNING_MSG_DENYSTATUS"));
			warningMsgs.add(messageProp.getProperty("SERVICE_WARNING_MSG_DENYSTATUS3"));
			assertTrue(serviceDetailExpPage.checkWarningMessages(warningMsgs));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 3, groups = { "Regression" })
	public void moveToDenyFromCAPProPendNegTC() throws Exception {
		try {
			log.logInfo(
					"verify warning messages if claim is moving from pend bucket to deny bucket with status as approved from CAP Professional pend.");
			commonSteps();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.selectStatus(i, "Approved");
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			serviceDetailExpPage.clickOnSaveValidatePro();
			// softAssert.assertTrue(serviceDetailExpPage.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			softAssert.assertTrue(serviceDetailExpPage
					.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
			viewClaimsPage.clickOnDenyButton();
			popupMsg = messageProp.getProperty("DENY_WARNING_MESSAGE");
			softAssert.assertTrue(viewClaimsPage.checkDenyWarningMsg(popupMsg));
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}

	}

	@Test(priority = 4, groups = { "Regression" })
	public void moveToApproveFromCAPProPendNegTC() throws Exception {
		try {
			log.logInfo(
					"Verify claim is moving from pend to approve bucket with status as approved from CAP Professional pend.");
			commonSteps();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				serviceDetailExpPage.selectStatus(i, "Approved");
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			serviceDetailExpPage.clickOnSaveValidatePro();
			// softAssert.assertTrue(serviceDetailExpPage.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			softAssert.assertTrue(serviceDetailExpPage
					.waitAndCheckForSuccessPopUp(messageProp.getProperty("SERVICELINE_ADJ_MESSAGE")));
			viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
			viewClaimsPage.clickOnApporveButton();
			popupMsg = messageProp.getProperty("APPORVE_SUCCESS_MESSAGE_PRO");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popupMsg, id, 70)));
			wait.waitForSeconds(2);
			capPro=new CAPProfessionalPage(DriverManager.getWebDriver());
			capPro.clickOnApproved();
			// clicking on filter and providing the data.
			commonActions.filterData(elementsProp.getProperty("CAPPRO_APPROVED_TABLE_ID"),1,id);
			softAssert.assertEquals(id, QMPage.getClaimNumber(elementsProp.getProperty("CAPPRO_APPROVED_TABLE_ID")));
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}

	}

}
