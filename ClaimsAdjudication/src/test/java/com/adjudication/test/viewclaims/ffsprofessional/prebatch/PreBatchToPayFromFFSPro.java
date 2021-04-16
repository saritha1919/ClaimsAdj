package com.adjudication.test.viewclaims.ffsprofessional.prebatch;

import java.sql.ResultSet;

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
import com.adjudication.reports.TestListener;
import com.adjudication.utils.DBConnection;
import com.adjudication.utils.Excel;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

@Listeners(TestListener.class)
public class PreBatchToPayFromFFSPro extends TestBase {

	MyLogger log = new MyLogger(PreBatchToPayFromFFSPro.class.getName());

	String id ;
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
	FFSProfessionalPage ffsPro;
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
			id=idProp.getProperty("PreBatchToPayFromFFSPro");
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
		ffsPro = new FFSProfessionalPage(DriverManager.getWebDriver());
		ffsPro.clickOnPreBatch();
		// clicking on filter and providing the data.
		commonActions.filterData(elementsProp.getProperty("FFSPRO_PREBATCH_TABLE_ID"),2,id);
		QMPage = new QueueManagementPage(DriverManager.getWebDriver());
		QMPage.selectClaimFromPrebatch(elementsProp.getProperty("FFSPRO_PREBATCH_TABLE_ID"));
		ffsPro.clickOnBtachClaimToPayButton();
		viewClaimsPage = new ViewClaimsPage(DriverManager.getWebDriver());
	}

	@Test
	public void movePrebatchToPay() throws Exception {
		try {
			commonSteps();
			popUpMsg = messageProp.getProperty("SUCCESS_MSG_PREBATCH_BACTHTOPAY");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(popUpMsg));
			DBConnection db = new DBConnection();
			ResultSet rs = db.getDeatailsFromDB(queryProp.getProperty("GetBatchIdFromPro") + "('" + id + "')",
					environment.TestValorDBConnectionString());
			while (rs.next()) {
				System.out.println("batch code:" + rs.getString("BatchCode"));
				batchId = rs.getString("BatchCode");
			}
			ffsPro.clickOnBatchToPay();
			commonActions.filterData(elementsProp.getProperty("FFSPRO_BATCHTOPAY_TABLE_ID"),2,batchId);
			softAssert.assertEquals(batchId, QMPage.getBatchIdFromBatchToPayWithFilter(elementsProp.getProperty("TO_BE_SINGEDOFF_TBODY_ID")));
			QMPage.clickOnViewBatch();
			softAssert.assertEquals(id, QMPage.getClaimNumber(elementsProp.getProperty("FFSPRO_BATCHTOPAY_VIEWCLAIM_TABLE_ID")));
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

}
