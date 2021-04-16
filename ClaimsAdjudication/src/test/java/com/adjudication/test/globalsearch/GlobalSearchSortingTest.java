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
import com.adjudication.utils.Excel;
import com.adjudication.utils.MyLogger;

public class GlobalSearchSortingTest extends TestBase{

	MyLogger log = new MyLogger(GlobalSearchSortingTest.class.getName());
	LinkedHashMap<String, String> sortData;
    String sheetName="GlobalSearchElements";
    String fieldName;
    String getValueToFilter;
    String sortingXapth;
    String resultInTabelXpath;
    int rowCount;
	
	LoginPage loginpage;
	AdjHomePage homePage;
	SideMenuPage sideMenu;
	PropertyFile elementsPropForSidebar;
	PropertyFile elementsProp;
	CommonActionsAcrossPages commonActions;
	Excel excel;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		try {
			
			elementsPropForSidebar = new PropertyFile(environment.ElementsInAdjSideMenuPath());
			elementsProp = new PropertyFile(environment.CommonElementsPath());
			excel= new Excel(environment.ExcelPath());
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
	public void gridAscSortInGlobalSearch() throws Exception {
		try {
			SoftAssert softAssert= new SoftAssert();
			rowCount=excel.getRowCount(sheetName);
			loginpage = new LoginPage(DriverManager.getWebDriver());
			homePage = loginpage.loginByPassingCred(environment.username(), environment.password());
			sideMenu=new SideMenuPage(DriverManager.getWebDriver());
			sideMenu.clickOnGlobalSearch();
			for (int i = 1; i <rowCount ; i++) {
				    fieldName=excel.readData(sheetName, "FieldName", i);
				    sortingXapth=excel.readData(sheetName,"SortingIcon", i);
				    resultInTabelXpath=excel.readData(sheetName,"ResultInTabel",i);
					// getting xapth, values from properties and excel to filter the data.
				    sortData = new LinkedHashMap<String, String>();
					sortData.put(sortingXapth,resultInTabelXpath);
					ArrayList<String> listBeforeSort=commonActions.getDataFromListOfWebEle(commonActions.getListOfWebEle(resultInTabelXpath));
					//getting list of values by clicking on name 
					//ArrayList<String> obtainerList = commonActions.getDataFromListOfWebEle(commonActions.sortData(sortData,"//table[@id='GlobalSearchQueueTable']/tbody/tr"));
					ArrayList<String> obtainerList = commonActions.getDataFromListOfWebEle(commonActions.sortData(sortData));
					//arranging in ascending order with  list before sort
					ArrayList<String> sortedList = commonActions.sortingDataInAsc(listBeforeSort);
					if(commonActions.compareSortedData(obtainerList, sortedList)) {
						softAssert.assertTrue(true);
						log.logInfo("Verified sorting functionality of "+fieldName);
						
					}
					else {
						//in case of verification incorrect assert should fail that's why giving true for assertFalse.
						softAssert.assertFalse(true);
						log.logError(fieldName+"Data is not sorting properly.");
					}
			}	
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			log.logError(e.getMessage());
		}
	}


}
