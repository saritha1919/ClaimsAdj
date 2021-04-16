package com.adjudication.test.globalsearch;

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
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.home.SideMenuPage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.test.login.LoginTest;
import com.adjudication.utils.Excel;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class GlobalGridSearchTest extends TestBase {

	MyLogger log = new MyLogger(LoginTest.class.getName());
	LinkedHashMap<String, String> filterData;
	String sheetName="GlobalSearchElements";
	String fieldName;
	String getValueToFilter;
	String searchTextBoxXapth;
	String resultInTabelXpath;
	int rowCount;

	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	CommonActionsAcrossPages commonActions;
	Excel excel;
	WaitUtility wait = new WaitUtility();

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		try {
			
			elementsPropForSidebar = new PropertyFile(environment.ElementsInAdjSideMenuPath());
			elementsProp = new PropertyFile(environment.CommonElementsPath());
			excel = new Excel(environment.ExcelPath());
			commonActions = new CommonActionsAcrossPages(DriverManager.getWebDriver());
		} catch (Exception e) {
			e.printStackTrace();
			log.logError(e.getMessage());
		}

	}

	/**
	 * This method is used to Search for a data in global search with 14 fields.
	 *
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	@Test
	public void gridSearchInGlobalSearch() throws Exception {
		try {
			SoftAssert softAssert = new SoftAssert();
			rowCount = excel.getRowCount(sheetName);
			loginpage = new LoginPage(DriverManager.getWebDriver());
			homePage = loginpage.loginByPassingCred(environment.username(), environment.password());
			sideMenu = new SideMenuPage(DriverManager.getWebDriver());
			// Click on Global Search from side menu
			sideMenu.clickOnGlobalSearch();
			if (!commonActions.checkNoDataAvail()) {
				for (int i = 1; i < rowCount; i++) {
					fieldName = excel.readData(sheetName, "FieldName", i);
					getValueToFilter = excel.readData(sheetName,
							"GetValueToFilter", i);
					searchTextBoxXapth = excel.readData(sheetName, "SearchTextBox",
							i);
					resultInTabelXpath = excel.readData(sheetName,"ResultInTabel", i);
					// getting xapth, values from properties and excel to filter the data.
					filterData = new LinkedHashMap<String, String>();
					// get data from table to filter
					String testData = commonActions.getDataFromElement(getValueToFilter);
					filterData.put(searchTextBoxXapth, testData);
					// clicking on search element and providing the data
					commonActions.filterData(filterData);
					// Storing arraylist after filtering the data.
					ArrayList<String> filteredData = commonActions
							.getDataFromListOfWebEle(commonActions.getListOfWebEle(resultInTabelXpath));
					// checking filtered result is having the search value or not.
					if (commonActions.checkFilteredData(filteredData, testData)) {
						softAssert.assertTrue(true);
						log.logInfo("Verified " + fieldName + " Filtered Data");

					} else {
						// in case of verification incorrect assert should fail that's why giving true
						// for assertFalse.
						softAssert.assertFalse(true);
						log.logError("Data(" + testData + ")is not filtered based on provided value for " + fieldName);
					}
					commonActions.clearText(searchTextBoxXapth);
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

}
