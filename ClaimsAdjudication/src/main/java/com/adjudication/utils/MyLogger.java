package com.adjudication.utils;

import java.lang.invoke.MethodHandles;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.adjudication.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;



public class MyLogger {
	
	private static Logger LOG ; 

	public MyLogger(String name)
	{
		LOG=Logger.getLogger(name);
		
	}
	public  void logInfo(String message) {
	    LOG.log(MyLogger.class.getCanonicalName(), Level.INFO, message, null);
	    ExtentTestManager.getTest().log(Status.INFO, message);
	}
	
	public  void logError(String message) {
	    LOG.log(MyLogger.class.getCanonicalName(), Level.ERROR, message, null);
	    ExtentTestManager.getTest().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
		//LOG.error(message);
	}

	public static void logNotWorking(String message) {
	    LOG.info(message);
	} 
	

}
