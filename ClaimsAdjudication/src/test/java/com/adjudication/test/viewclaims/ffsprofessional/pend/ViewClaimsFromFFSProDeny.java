package com.adjudication.test.viewclaims.ffsprofessional.pend;

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
import com.adjudication.pages.ffsprofessional.FFSProfessionalPage;
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
public class ViewClaimsFromFFSProDeny extends TestBase {

	MyLogger log = new MyLogger(ViewClaimsFromFFSProApproved.class.getName());

	String id ;
	String[] idList;
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
			id=idProp.getProperty("ViewClaimsFromFFSProDeny");
			idList=id.split(",");
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
	}

	@Test(priority = 1, groups = { "Regression" })
	public void moveToApproveFromFFSProPendNegTC() {
		try {
			log.logInfo(
					"Verify warning messages if claim is moving from pend bucket to Approve bucket with status as Deny from FFS pro pend");
			commonSteps();
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
	public void moveToDenyFromFFSProPendPositiveTC() {
		try {
			log.logInfo("Verify claim is moving from pend to Deny bucket with status as deny from ffs pro pend");
			commonSteps();
			viewClaimsPage.clickOnDenyButton();
			popUpMsg = messageProp.getProperty("DENY_SUCCESS_MESSAGE_PRO");
			softAssert.assertTrue(viewClaimsPage.checkApproveSuccessMsg(genericMethods.insertString(popUpMsg, id, 70)));
			commonActions.waitForElementInvisible(elementsProp.getProperty("LOADING_SYMBOL"));
			wait.waitForSeconds(2);
			ffsPro=new FFSProfessionalPage(DriverManager.getWebDriver());
			ffsPro.clickOnDenied();
			commonActions.filterData(elementsProp.getProperty("FFSPRO_DENIED_TABLE_ID"),1,id);
			softAssert.assertEquals(id, QMPage.getClaimNumber(elementsProp.getProperty("FFSPRO_DENIED_TABLE_ID")));
			softAssert.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}

}
