package com.adjudication.utils;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;


public class Screenshot {

	org.apache.log4j.Logger log; 
	private WebDriver driver;	
	private final String folderPath;
	
	private final String folderNotExistError = "screenshot folder does not exist";
	private final String cannotCleanFolderError = "cannot clean screenshot folder";
	private final String cannotCaptureScreenshotError = "cannot capture the screenshot";

	public Screenshot(WebDriver driver) throws Exception {		
		this.driver = driver;
		folderPath="./src/test/resources/screenshots/";
		validateFolderExists();	
	}

	private void validateFolderExists() {
		File screenShotFolder = new File(folderPath);
		if (!screenShotFolder.exists()) {
			screenShotFolder.mkdir();
		}
	}
	
	public void cleanFolder()
	{		
		try{					
			File screenShotFolder = new File(folderPath);
			for(File file: screenShotFolder.listFiles()) 
				file.delete();
		}
		catch(Exception ex) {
			log.info(cannotCleanFolderError);
			throw new RuntimeException(cannotCleanFolderError, ex);
		}
	}	
	
	public String capture(String methodName) 
	{		
		File scrFile;
		 
		 try {
			 scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			 String screenshotName=getScreenshotName(methodName);
			 FileUtils.copyFile(scrFile, new File(screenshotName));	
			 return screenshotName;
		 } 
		 catch (IOException e) {
			 e.printStackTrace();
		 }
		 
		 log.info(cannotCaptureScreenshotError);
		 throw new RuntimeException(cannotCaptureScreenshotError);
        
    }
	
	private String getScreenshotName(String methodName) {
		 //String localDateTime = LocalDateTime.now().toString().replaceAll("[^0-9a-zA-Z]", "");
		String screenshotDate=new SimpleDateFormat("yyyyMMddHHmm'.png'").format(new Date());
		 String screenshotName ="./src/test/resources/Screenshots/"+methodName+"_"+screenshotDate; 
		//StringBuilder name = new StringBuilder().append(folderPath).append(methodName).append("_").append(screenshotDate).append(".png");
		 return screenshotName.toString();
	 }


}
