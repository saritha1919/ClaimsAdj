package com.adjudication.test.viewclaims.ffsinstitutional.pend;

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
public class ViewClaimsFromFFSInstiPend extends TestBase {

	MyLogger log = new MyLogger(ViewClaimsFromFFSInstiPend.class.getName());

	String id ;
	int totalButtonsInServiceLinePage;
	int deductibleAmt = 10;
	int coinsuranceAmt = 10;
	int copayAmt = 10;
	int totalPaymentAmount;
	int claimLineAmount;
	int totalAdjustment;
	List<String> warningMsgs;
	String popUpMsg;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	PropertyFile messageProp;
	PropertyFile idProp;
	Excel excel;
	SoftAssert softAssert;

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
			id=idProp.getProperty("ViewClaimsFromFFSInstiPend");
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
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("FFSINSTI_PEND_TABLE_ID"),1,id);
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		// System.out.println("claim number:"+ffsSearchPage.clickOnViewIcon());
		QMPage.clickOnViewClaims(elementsProp.getProperty("FFSINSTI_PEND_TABLE_ID"));
		wait.waitForSeconds(3);
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());

	}

	@Test(priority = 1, groups = { "Regression" })
	public void moveToApproveFromFFSInstiPendNegTC() {
		try {
			log.logInfo(
					"Verify warning messages if claim is moving from pend bucket to Approve bucket with pend as Deny from FFS Insti pend");
			commonSteps();
			viewClaimsPage.clickOnApporveButton();
			popUpMsg = messageProp.getProperty("APPROVE_WARNING_MSG_STATUS_PEND");
			assertTrue(viewClaimsPage.checkDenyWarningMsg(popUpMsg));

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

	@Test(priority = 2, groups = { "Regression" })
	public void moveToDenyFromFFSInstiPendNegTC() {
		try {
			log.logInfo(
					"Verify claim is moving from pend to Deny bucket with status as pend from FFS institutional Pend.");
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
	public void checkPRInServiceDetailFromFFSInsti() {
		try {
			log.logInfo(
					"Verify Total Dedutible,coinsurance,copay values are reflecting or not by adding PR-1,PR-2,PR-3 from cap pro");
			commonSteps();
			viewClaimsPage.clickOnServiceDetails();
			serviceDetailsPage = new ServiceDetailsPage(DriverManager.getWebDriver());
			serviceDetailsPage.clickOnExpandBtnInInsti();
			serviceDetailExpPage = new ServiceDetailsExpansionPage(DriverManager.getWebDriver());
			totalButtonsInServiceLinePage = serviceDetailExpPage.getNoOfServiceLinesCountInInsti();
			for (int i = 1; i <= totalButtonsInServiceLinePage; i++) {
				int NumOfRowsInAdjustmentTable = serviceDetailExpPage.getNumOfRowsInAdjustmentTable(i);
				serviceDetailExpPage.clickOnAddButton(i, NumOfRowsInAdjustmentTable);
				wait.waitForSeconds(2);
				serviceDetailExpPage.setAdjustmentInfoInsti("PR - Patient Responsibility", "1 - Deductible Amount",
						deductibleAmt, i, NumOfRowsInAdjustmentTable);
				serviceDetailExpPage.clickOnAddButton(i, NumOfRowsInAdjustmentTable + 1);
				wait.waitForSeconds(2);
				serviceDetailExpPage.setAdjustmentInfoInsti("PR - Patient Responsibility", "2 - Coinsurance Amount",
						coinsuranceAmt, i, NumOfRowsInAdjustmentTable + 1);
				serviceDetailExpPage.clickOnAddButton(i, NumOfRowsInAdjustmentTable + 2);
				wait.waitForSeconds(2);
				serviceDetailExpPage.setAdjustmentInfoInsti("PR - Patient Responsibility", "3 - Co-payment Amount", copayAmt,
						i, NumOfRowsInAdjustmentTable + 2);
				wait.waitForSeconds(1);
				softAssert.assertEquals(deductibleAmt, serviceDetailExpPage.getDeductibleAmountInsti(i));
				softAssert.assertEquals(coinsuranceAmt, serviceDetailExpPage.getCoinsuranceAmountInsti(i));
				softAssert.assertEquals(copayAmt, serviceDetailExpPage.getCopayAmountInsti(i));
				claimLineAmount=serviceDetailExpPage.getClaimLineAmountInsti(i);
				totalAdjustment=serviceDetailExpPage.getTotalAdjustAmountInsti(i);
				totalPaymentAmount=claimLineAmount-totalAdjustment-deductibleAmt-coinsuranceAmt-copayAmt;
				softAssert.assertEquals(totalPaymentAmount, serviceDetailExpPage.getTotalPaymentAmountInsti(i));
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
