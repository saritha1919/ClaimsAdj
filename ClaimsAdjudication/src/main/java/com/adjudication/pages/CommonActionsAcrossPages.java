package com.adjudication.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.adjudication.seleniumactions.implementation.JavaScriptExecutor;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class CommonActionsAcrossPages extends BasePage{

	MyLogger log = new MyLogger(CommonActionsAcrossPages.class.getName());
	
	public CommonActionsAcrossPages(WebDriver driver) {
		super(driver);
	}
	
	String tableLoadingSymbol="//tfoot[@id='tableFooter']";
	String tableLoadingClassValue="tablefooterloading hidden";
	WaitUtility wait = new WaitUtility();
	JavaScriptExecutor js= new JavaScriptExecutor(driver);
	
	/**
	 * This method is used to Filter data in admission list page.
	 *
	 * @param input is LinkedHashMap<String, String>. Here key is xpath of
	 *              webelement and value is data.
	 * 
	 * @author Saritha
	 * @throws Exception
	 */
	public void filterData(LinkedHashMap<String, String> input) throws Exception {
		wait.waitForSeconds(3);
		Iterator<Map.Entry<String, String>> it = ((Map) input).entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			WebElement textbox=driver.findElement(By.xpath(key));
			wait.waitTillElementVisible(driver, 60, textbox);
			textbox.click();
			driver.findElement(By.xpath(key)).sendKeys(entry.getValue());
			log.logInfo("Provided search data as " + value);
			driver.findElement(By.xpath(key)).sendKeys(Keys.ENTER);
			wait.waitForSeconds(1);
			wait.waitTillEleClassChanges(driver,60,tableLoadingSymbol,tableLoadingClassValue);
		}
	}
	
	public void filterData(String tableId,int columnNUmber,String inputData) throws Exception {
		WebElement textBox;
		try {
		textBox=driver.findElement(By.xpath("//table[@id='"+tableId+"']/thead/tr[2]/th["+columnNUmber+"]/input"));
		wait.waitTillElementVisible(driver, 60, textBox);
		textBox.click();
		}catch (StaleElementReferenceException e) {
			textBox=driver.findElement(By.xpath("//table[@id='"+tableId+"']/thead/tr[2]/th["+columnNUmber+"]/input"));
			wait.waitTillElementVisible(driver, 60, textBox);
			textBox.click();
		}
		textBox.sendKeys(inputData);
		log.logInfo("Provided search data as " + inputData);
		textBox.sendKeys(Keys.ENTER);
		wait.waitTillEleClassChanges(driver,60,tableLoadingSymbol,tableLoadingClassValue);
	}
	
	/**
	 * This method is used to Scroll to table end.
	 *
	 * @param webElement the web element
	 * @throws InterruptedException the interrupted exception
	 * 
	 * @author Saritha
	 */
	public void scrollToEnd(String webElement) throws InterruptedException {
		boolean reachedEnd = false;
		int newCount;
		int oldCount = driver.findElements(By.xpath(webElement)).size();
        Actions actions = new Actions(driver);
        while(!reachedEnd) {
        	actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
        	Thread.sleep(4000);
        	newCount=driver.findElements(By.xpath(webElement)).size();
        	System.out.println("old count:"+oldCount);
        	System.out.println("New count:"+newCount);
        	if(oldCount==newCount) {
        		 System.out.println("success");
        		reachedEnd=true;
        	}
        	else {
        		oldCount= newCount;		
        	}
        }
	}

	/**
	 * This method is used to sort the data and get the result.
	 *
	 * @param input the LinkedHashMap<String, String>. Here key is xpath of element
	 *              which is going to click and value is xpath of table result of
	 *              selected column.
	 * @return the list of webelements.
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public List<WebElement> sortData(LinkedHashMap<String, String> input) throws Exception {
		List<WebElement> resultSet = null;
		Iterator<Map.Entry<String, String>> it = ((Map) input).entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			WebElement sortingIcon = driver.findElement(By.xpath(key));
			wait.waitForSeconds(4);
			sortingIcon.click();
			//scrollToEnd(tbelRowElement);
			resultSet = getListOfWebEle(value);
		}
		return resultSet;
	}

	
	/**
	 * This method is used to sort the data.
	 *
	 * @param input the LinkedHashMap<String, String>. Here key is xpath of element
	 *              which is going to click and value is xpath of table result of
	 *              selected column.
	 * @return the list of webelements.
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public void sortData(String input) throws Exception {
			WebElement sortingIcon = driver.findElement(By.xpath(input));
			wait.waitForSeconds(4);
			sortingIcon.click();
			wait.waitTillEleClassChanges(driver,60,tableLoadingSymbol,tableLoadingClassValue);
			//scrollToEnd(tbelRowElement);
	}
	
	/**
	 * This method is used to Get the list of web elements from tabel.
	 *
	 * @param element the element
	 * @return the list of web elements
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public List<WebElement> getListOfWebEle(String element) throws Exception {
		wait.waitForSeconds(5);
		return driver.findElements(By.xpath(element));
	}

	/**
	 * This method is used to get list of values from list of web elements.
	 *
	 * @param elementList the webelement list
	 * @return the list of values.
	 * 
	 * @author Saritha
	 */
	public ArrayList<String> getDataFromListOfWebEle(List<WebElement> elementList) throws Exception {
		ArrayList<String> obtainedList = new ArrayList<>();
		for (WebElement we : elementList) {
			obtainedList.add(we.getText());
		}
		log.logInfo("Got the list of result values.");
		return obtainedList;
	}

	/**
	 * This method is used to Sort the list of data in ascending order.
	 *
	 * @param list the list<string>
	 * @return the array list
	 * 
	 * @author Saritha
	 */
	public ArrayList<String> sortingDataInAsc(ArrayList<String> list) throws Exception {
		ArrayList<String> sortedList = new ArrayList<>();
		for (String s : list) {
			sortedList.add(s);
		}
		Collections.sort(sortedList);
		return sortedList;
	}

	/**
	 * This method is used to Sort the list of data in desending order.
	 *
	 * @param list the list<string>
	 * @return the array list
	 * 
	 * @author Saritha
	 */
	public ArrayList<String> sortingDataInDesc(ArrayList<String> list) throws Exception {
		ArrayList<String> sortedList = new ArrayList<>();
		for (String s : list) {
			sortedList.add(s);
		}
		Collections.reverse(sortedList);
		return sortedList;
	}

	/**
	 * This method is used to Compare the two lists
	 *
	 * @param expectedResult the ArrayList<String>
	 * @param actualResult   the ArrayList<string>
	 * @return true, if successful
	 * 
	 * @author Saritha
	 */

	public boolean compareSortedData(ArrayList<String> expectedResult, ArrayList<String> actualResult)
			throws Exception {
		return expectedResult.containsAll(actualResult) && expectedResult.size() == actualResult.size() ? true : false;
		// return expectedResult.equals(actualResult);
	}

	
	/**
	 * This method is used to Check filtered data with given value.
	 *
	 * @param list the list
	 * @param value the value
	 * @return true, if successful
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public boolean checkFilteredData(ArrayList<String> list, String value) throws Exception {
		int check = 0;
		boolean isPresent = false;
		value=value.toLowerCase();
		try {
			for (String data : list) {
				System.out.println("data: "+data);
				if (data.toLowerCase().contains(value)) {
					check++;
				}
				if (check == list.size()) {
					isPresent = true;
				}
			}
		} catch (Exception e) {
			log.logError(e.getMessage());
		}
		return isPresent;
	}


	/**
	 * Check no data found.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public boolean checkNoDataAvail() throws Exception {
		log.logInfo("No data avilable with provided search value in system");
		return driver.getPageSource().contains("No Data Available") ? true : false;
	}
	
	/**
	 * This method is used to Clear the text.
	 *
	 * @param webElement the web element
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public void clearText(String webElement) throws Exception {
		WebElement element = driver.findElement(By.xpath(webElement));
		element.sendKeys(Keys.CONTROL,"a");
		element.sendKeys(Keys.BACK_SPACE);	
		element.sendKeys(Keys.ENTER);
		wait.waitForSeconds(1);
	}
	
    /**
     * This method is used to gets the data from element.
     *
     * @param webElement the web element
     * @return the data from element
     * @throws Exception the exception
     * 
     * @author Saritha
     */
    public String getDataFromElement(String webElement) throws Exception{
    	WebElement element = driver.findElement(By.xpath(webElement));
    	wait.waitTillElementVisible(driver, 40, element);
		return element.getText();
	}

    public ArrayList<String> getFilteredData(String tr,String td,String eleWithCOB,String eleWithOutCOB) {
    	ArrayList<String> obtainedList = new ArrayList<>();
    	List<WebElement> row=driver.findElements(By.xpath(tr));
    	int rowCount=row.size();
    	int tdCount;
    	for (int i = 0; i < rowCount; i++) {
    		try {
    		int rowNumber=i+1; 
    		List<WebElement> tabelData=driver.findElements(By.xpath("//table[@id='"+td+"']/tbody/tr["+rowNumber+"]/td"));
    		tdCount=tabelData.size();
			if(tdCount==15) {
				obtainedList.add(driver.findElement(By.xpath("//table[@id='"+td+"']/tbody/tr["+rowNumber+"]/td["+eleWithOutCOB+"]")).getText());
			}
			else {
				obtainedList.add(driver.findElement(By.xpath("//table[@id='"+td+"']/tbody/tr["+rowNumber+"]/td["+eleWithCOB+"]")).getText());
			}
		}
    		catch (org.openqa.selenium.NoSuchElementException e) {
    			//incase if we get no data avilable after filter we are returning null list
    			obtainedList.add("");
       }
	}
		return obtainedList;
    }

    /**
	 * This method is used to Wait for element invisible.
	 *
	 * @param webElement the String type
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public void waitForElementInvisible(String webElement) throws Exception {
		WebElement loadingElement = driver.findElement(By.xpath(webElement));
		wait.waitTillElementInvisible(driver, 60, loadingElement);
	}
}
