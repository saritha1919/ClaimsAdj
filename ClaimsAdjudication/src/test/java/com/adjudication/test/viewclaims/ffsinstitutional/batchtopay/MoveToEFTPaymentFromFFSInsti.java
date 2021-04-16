package com.adjudication.test.viewclaims.ffsinstitutional.batchtopay;

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
public class MoveToEFTPaymentFromFFSInsti extends TestBase {

	MyLogger log = new MyLogger(MoveToEFTPaymentFromFFSInsti.class.getName());

	String batchId;
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
		ffsInsti.clickOnBatchToPay();
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		viewClaimsPage=new ViewClaimsPage(DriverManager.getWebDriver());
		batchId=idProp.getProperty("MoveToEFTPaymentFromFFSInsti");
	}

	@Test(priority = 1,groups = { "Regression" })
	public void moveToEFTPaymentFromBatchToPay() throws Exception {
		try {
			log.logInfo("Verify Batch is moving to EFT payment or not after clicking on EFT Payment");
			commonSteps();
			QMPage.selectClaimFromBatchToPayWithOutFilter(elementsProp.getProperty("TO_BE_SINGEDOFF_TBODY_ID"));
			String batchIdFromApp = QMPage.getBatchIdFromBatchToPayWithOUtFilter(elementsProp.getProperty("TO_BE_SINGEDOFF_TBODY_ID"));
			QMPage.clickOnEFTPaymentButton();
			popUpMsg = messageProp.getProperty("SUCCESS_MSG_EFTPAYMENT");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(popUpMsg));
			QMPage.clickEFTPaymentCheckbox();
			wait.waitForSeconds(1);
			commonActions.filterData(elementsProp.getProperty("FFSINSTI_EFTPAYMENT_TABLE_ID"),2,batchIdFromApp);
			softAssert.assertEquals(batchIdFromApp, QMPage.getBatchIdFromBatchToPayWithFilter(elementsProp.getProperty("EFT_PAYMENT_TBODY_ID")));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}

	}

	@Test(priority = 2,groups = { "Regression" })
	public void moveToPaidFromEFTPaymentFFSInsti() throws Exception {
		try {
			log.logInfo("Verify Batch is moving to paid or not from EFT Payment after clicking on Move To Paid.");
			commonSteps();
			QMPage=new QueueManagementPage(DriverManager.getWebDriver());
			QMPage.clickEFTPaymentCheckbox();
			wait.waitForSeconds(2);
			commonActions.filterData(elementsProp.getProperty("FFSINSTI_EFTPAYMENT_TABLE_ID"),2,batchId);
			QMPage.selectClaimFromBatchToPayWithFilter(elementsProp.getProperty("EFT_PAYMENT_TBODY_ID"));
			//batchId = QMPage.getBatchIdFromBatchToPayWithOUtFilter(elementsProp.getProperty("EFT_PAYMENT_TBODY_ID"));
			System.out.println("batchid"+batchId);
			QMPage.clickOnMoveToPaidButton();
			popUpMsg = messageProp.getProperty("SUCCESS_MSG_EFT_TO_PAID");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(popUpMsg));
			ffsInsti.clickOnPaidTab();
			wait.waitForSeconds(2);
			commonActions.filterData(elementsProp.getProperty("FFSINSTI_PAID_TABLE_ID"),1,batchId);
			softAssert.assertEquals(batchId, QMPage.getBatchIdFromPaidWithFilter(elementsProp.getProperty("FFSINSTI_PAID_TABLE_ID")));
			softAssert.assertTrue(QMPage.isEOPLetterPresent());
			softAssert.assertTrue(QMPage.is835FilePresent());
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

}
