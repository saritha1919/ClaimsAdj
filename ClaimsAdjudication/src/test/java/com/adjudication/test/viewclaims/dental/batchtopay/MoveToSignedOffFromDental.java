package com.adjudication.test.viewclaims.dental.batchtopay;

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
public class MoveToSignedOffFromDental extends TestBase {

	MyLogger log = new MyLogger(MoveToSignedOffFromDental.class.getName());

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
	DentalPage dental;
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
		sideMenu.clickOnDental();
		dental = new DentalPage(DriverManager.getWebDriver());
		dental.clickOnBatchToPay();
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		viewClaimsPage=new ViewClaimsPage(DriverManager.getWebDriver());
		batchId=idProp.getProperty("MoveToSignedOffFromDental");
	}

	@Test(priority = 1,groups = { "Regression" })
	public void moveToSignedOffFromBatchToPay() throws Exception {
		try {
			log.logInfo("Verify Batch is moving to Signed off or not after clicking on Sign off");
			commonSteps();
			QMPage.selectClaimFromBatchToPayWithOutFilter(elementsProp.getProperty("TO_BE_SINGEDOFF_TBODY_ID"));
			String batchIdFromApp = QMPage.getBatchIdFromBatchToPayWithOUtFilter(elementsProp.getProperty("TO_BE_SINGEDOFF_TBODY_ID"));
			QMPage.clickOnSignOffButton();
			popUpMsg = messageProp.getProperty("SUCCESS_MSG_SIGNOFF");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(popUpMsg));
			QMPage.clickOnSignedOffCheckbox();
			commonActions.filterData(elementsProp.getProperty("DENTAL_SIGNEDOFF_TABLE_ID"),2, batchIdFromApp);
			softAssert.assertEquals(batchIdFromApp, QMPage.getBatchIdFromBatchToPayWithFilter(elementsProp.getProperty("SINGED_OFF_TBODY_ID")));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}

	}

	@Test(priority = 2,groups = { "Regression" })
	public void moveToSentForPaymentFromSignedOffDental() throws Exception {
		try {
			log.logInfo("Verify Batch is moving to Sent for payment  or not from Signed Off after clicking on Send for Payment");
			commonSteps();
			QMPage=new QueueManagementPage(DriverManager.getWebDriver());
			QMPage.clickOnSignedOffCheckbox();
			wait.waitForSeconds(2);
			commonActions.filterData(elementsProp.getProperty("DENTAL_SIGNEDOFF_TABLE_ID"),2, batchId);
			QMPage.selectClaimFromBatchToPayWithFilter(elementsProp.getProperty("SINGED_OFF_TBODY_ID"));
			//batchId = QMPage.getBatchIdFromBatchToPayWithOUtFilter(elementsProp.getProperty("SINGED_OFF_TBODY_ID"));
			QMPage.clickOnSendForPaymentButton();
			QMPage.clickOnDownloadInDoownloadGPFiles();
			wait.waitForSeconds(1);
			popUpMsg = messageProp.getProperty("SUCCESS_MSG_SENT_FOR_PAYMENT");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(popUpMsg));
			if(QMPage.isDownloadGPFileModelPresent()) {
				QMPage.closeDownloadGPFileModel();
			}
			wait.waitForSeconds(2);
			QMPage.clickOnSentForPaymentCheckbox();
			commonActions.filterData(elementsProp.getProperty("DENTAL_SENT_FOR_PAYMENT_TABLE_ID"),2,batchId);
			softAssert.assertEquals(QMPage.getBatchIdFromBatchToPayWithFilter(elementsProp.getProperty("SENT_FOR_PAYMENT_TBODY_ID")),batchId);
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

}
