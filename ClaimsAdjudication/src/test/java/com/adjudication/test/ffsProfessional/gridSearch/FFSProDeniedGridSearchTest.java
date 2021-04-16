package com.adjudication.test.ffsProfessional.gridSearch;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
import com.adjudication.utils.Excel;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class FFSProDeniedGridSearchTest extends TestBase {

	MyLogger log = new MyLogger(FFSProDeniedGridSearchTest.class.getName());

	LinkedHashMap<String, String> filterData;
	String sortingXapth;
	String sheetName="FFSPRO_DENIED_GRIDSEARCH_ELE";
	String fieldName;
	String getValueToFilter;
	String getValueToFilterWithOutCOB;
	String searchTextBoxXapth;
	String resultInTabelXpath;
	String resultInTabelXpthWithOutCOB;
	String getTDCount;
	int rowCount;
	String testData;
	ArrayList<String> filteredData;
	String tr = "//table[@id='FFSDeniedQueTable']/tbody/tr";
	String td = "FFSDeniedQueTable";

	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	PropertyFile elementsProp;
	PropertyFile elementsPropForSidebar;
	CommonActionsAcrossPages commonActions;
	FFSProfessionalPage ffsProPage;
	QueueManagementPage QMPage;
	Excel excel;
	WaitUtility wait;
	SoftAssert softAssert;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		try {
			
			elementsPropForSidebar = new PropertyFile(environment.ElementsInAdjSideMenuPath());
			elementsProp = new PropertyFile(environment.CommonElementsPath());
			excel = new Excel(environment.ExcelPath());
			wait = new WaitUtility();
			commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}

	}

	@Test
	public void ffsProDeniedGridSearchTest() {
		try {
			softAssert = new SoftAssert();
			rowCount = excel.getRowCount(sheetName);
			loginpage = new LoginPage(DriverManager.getWebDriver());
			homePage = loginpage.loginByPassingCred(environment.username(), environment.password());
			sideMenu = new SideMenuPage(DriverManager.getWebDriver());
			// click on FFS peofessional from side menu.
			sideMenu.clickOnFFSPro();
			ffsProPage = new FFSProfessionalPage(DriverManager.getWebDriver());
			// click on management review tab
			ffsProPage.clickOnDenied();
			if (!commonActions.checkNoDataAvail()) {
				for (int i = 1; i < rowCount; i++) {
					loadExcelData(i);
					QMPage=new QueueManagementPage(DriverManager.getWebDriver());
					filterData = new LinkedHashMap<String, String>();
					if (QMPage.getSize(getTDCount) == 15) {
						sortDataBasedonCOB(getValueToFilterWithOutCOB, testData);
					} else {
						sortDataBasedonCOB(getValueToFilter, testData);
					}

					commonActions.clearText(searchTextBoxXapth);
					// in case if we get no data avialble after filter, it will take time load after
					// removing the data.
					if (filteredData.size() == 0) {
						wait.waitForSeconds(2);
					}
					wait.waitForSeconds(1);
				}
			} else {
				softAssert.assertFalse(true);
				log.logError("There is no data present in table to search. Please make sure data is present.");
			}
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}

	}

	public void commonSteps(String data) throws Exception {
		filterData.put(searchTextBoxXapth, data);
		// clicking on filter and providing the data.
		commonActions.filterData(filterData);
		// get the result after filtering from table.
		filteredData = commonActions.getFilteredData(tr, td, resultInTabelXpath, resultInTabelXpthWithOutCOB);
		if (commonActions.checkFilteredData(filteredData, data)) {
			softAssert.assertTrue(true);
			log.logInfo("Verified " + fieldName + " Filtered Data");
		} else {
			// in case if filter data validation is wrong we have fail the test case. that's
			// y we have given true value for assert false.
			softAssert.assertFalse(true);
			log.logError("Data(" + data + ")is not filtered based on provided value for " + fieldName);
		}
	}

	public void loadExcelData(int i) {
		fieldName = excel.readData(sheetName, "FieldName", i);
		getTDCount = excel.readData(sheetName, "GetTDCount", i);
		getValueToFilter = excel.readData(sheetName, "GetValueToFilter", i);
		getValueToFilterWithOutCOB = excel.readData(sheetName,"GetValueToFilterWithoutCOB", i);
		searchTextBoxXapth = excel.readData(sheetName, "SearchTextBox", i);
		resultInTabelXpath = excel.readData(sheetName, "ResultInTabel", i);
		resultInTabelXpthWithOutCOB = excel.readData(sheetName,
				"ResultInTabelWithOutCOB", i);
		sortingXapth = excel.readData(sheetName, "SortingIcon", i);
	}

	public void sortDataBasedonCOB(String getValueToFilter, String data) throws Exception {
		// get the test data to filter
		data = commonActions.getDataFromElement(getValueToFilter);
		// check data variable is having the data or not.
		if (data.length() == 0) {
			// in case of we don't have data we are sorting in desc.
			commonActions.sortData(sortingXapth);
			commonActions.sortData(sortingXapth);
			// After soritng we are checking td count to get data.
			if (QMPage.getSize(getTDCount) == 15) {
				data = commonActions.getDataFromElement(getValueToFilterWithOutCOB);
				commonSteps(data);
			} else {
				data = commonActions.getDataFromElement(getValueToFilter);
				commonSteps(data);
			}
		} else {
			commonSteps(data);
		}
	}
}
