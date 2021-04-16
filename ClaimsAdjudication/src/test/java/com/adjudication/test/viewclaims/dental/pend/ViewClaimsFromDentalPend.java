package com.adjudication.test.viewclaims.dental.pend;

import static org.testng.Assert.assertTrue;

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
public class ViewClaimsFromDentalPend extends TestBase {

	MyLogger log = new MyLogger(ViewClaimsFromDentalPend.class.getName());

	String id ;
	int deductibleAmt = 10;
	int coinsuranceAmt = 10;
	int copayAmt = 10;
	int totalPaymentAmount;
	int claimLineAmount;
	int totalAdjustment;
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
			commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
			id=idProp.getProperty("ViewClaimsFromDentalPend");
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
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("DENTAL_PEND_TABLE_ID"),1,id);
		//wait.waitTillEleClassChanges(DriverManager.getWebDriver(), 60, elementsProp.getProperty("LOADING_SYMBOL_TBLRESULT"));
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		// System.out.println("claim number:"+ffsSearchPage.clickOnViewIcon());
		QMPage.clickOnViewClaims(elementsProp.getProperty("DENTAL_PEND_TABLE_ID"));
		wait.waitForSeconds(2);
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
	}

	@Test(priority = 1, groups = { "Regression" })
	public void moveToApproveFromDentalPendNegTC() {
		try {
			log.logInfo(
					"Verify warning messages if claim is moving from pend bucket to Approve bucket with status as Deny from Dental pend");
			commonSteps();
			viewClaimsPage.clickOnApproveButtonForDental();
			popUpMsg = messageProp.getProperty("APPROVE_WARNING_MSG_STATUS_PEND");
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
			viewClaimsPage.clickOnDenyButton();
			popUpMsg = messageProp.getProperty("DENY_WARNING_MSG_STATUS_PEND");
			assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 3, groups = { "Regression" })
	public void checkPRInServiceDetailFromDental() {
		try {
			log.logInfo(
					"Verify Total Dedutible,coinsurance,copay values are reflecting or not by adding PR-1,PR-2,PR-3 from cap pro");
			commonSteps();
			viewClaimsPage.clickOnServiceDetails();
			serviceDetailsPage = new ServiceDetailsPage(DriverManager.getWebDriver());
			serviceDetailsPage.clickOnExpandBtnInDental();
			serviceDetailExpPage = new ServiceDetailsExpansionPage(DriverManager.getWebDriver());
			totalButtonsInServiceLinePage = serviceDetailExpPage.getNoOfServiceLinesCountInDental();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				int NumOfRowsInAdjustmentTable = serviceDetailExpPage.getNumOfRowsInAdjustmentTable(i);
				serviceDetailExpPage.clickOnAddButton(i, NumOfRowsInAdjustmentTable);
				wait.waitForSeconds(2);
				serviceDetailExpPage.setAdjustmentInfoDental("PR - Patient Responsibility", "1 - Deductible Amount",
						deductibleAmt, i, NumOfRowsInAdjustmentTable);
				serviceDetailExpPage.clickOnAddButton(i, NumOfRowsInAdjustmentTable + 1);
				wait.waitForSeconds(2);
				serviceDetailExpPage.setAdjustmentInfoDental("PR - Patient Responsibility", "2 - Coinsurance Amount",
						coinsuranceAmt, i, NumOfRowsInAdjustmentTable + 1);
				serviceDetailExpPage.clickOnAddButton(i, NumOfRowsInAdjustmentTable + 2);
				wait.waitForSeconds(2);
				serviceDetailExpPage.setAdjustmentInfoDental("PR - Patient Responsibility", "3 - Co-payment Amount",
						copayAmt, i, NumOfRowsInAdjustmentTable + 2);
				wait.waitForSeconds(1);
				softAssert.assertEquals(deductibleAmt, serviceDetailExpPage.getDeductibleAmountDental(i));
				softAssert.assertEquals(coinsuranceAmt, serviceDetailExpPage.getCoinsuranceAmountDental(i));
				softAssert.assertEquals(copayAmt, serviceDetailExpPage.getCopayAmountDental(i));
				claimLineAmount=serviceDetailExpPage.getClaimLineAmountDental(i);
				totalAdjustment=serviceDetailExpPage.getTotalAdjustAmountDental(i);
				totalPaymentAmount=claimLineAmount-totalAdjustment-deductibleAmt-coinsuranceAmt-copayAmt;
				softAssert.assertEquals(totalPaymentAmount, serviceDetailExpPage.getTotalPaymentAmountDental(i));
				serviceDetailExpPage.navToNextpage(totalButtonsInServiceLinePage, i);
			}
			softAssert.assertAll();
			log.logInfo("All amounts are verified");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}
}
