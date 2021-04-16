package com.adjudication.utils;

import org.openqa.selenium.WebDriver;

public class GenericMethods {

	private WebDriver driver;
	MyLogger log = new MyLogger(GenericMethods.class.getName());

	public GenericMethods(WebDriver driver) {
		this.driver = driver;
	}


	/**
	 * This method is used to Gets the digit from text.
	 *
	 * @param text the text
	 * @return the digit from text
	 * @throws Exception the exception
	 * 
	 * @author Saritha
	 */
	public int getDigitFromText(String text) throws Exception {
		return Integer.parseInt(text.replaceAll("[^0-9]", ""));
	}

	public String removeDoubleQuotesFromText(String text) throws Exception{
		return text.replaceAll("\"", "");
	}
	public String insertString(String originalString,String stringToBeInserted,int index) 
	    { 
	  
	        // Create a new string 
	        String newString = new String(); 
	  
	        for (int i = 0; i < originalString.length(); i++) { 
	  
	            // Insert the original string character 
	            // into the new string 
	            newString += originalString.charAt(i); 
	  
	            if (i == index) { 
	  
	                // Insert the string to be inserted 
	                // into the new string 
	                newString += stringToBeInserted; 
	            } 
	        } 
	  
	        // return the modified String 
	        return newString; 
	    } 

}

