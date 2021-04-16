package com.adjudication.utils;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class UploadFile {
	
	public void uploadFile(String fileLocation) {
        try {
        	//Setting clipboard with file location
        	StringSelection stringSelection = new StringSelection(fileLocation);
  		   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
  		   Thread.sleep(3000);
            //native key strokes for CTRL, V and ENTER keys
            Robot robot = new Robot();
            // Press Enter

            robot.keyPress(KeyEvent.VK_ENTER);
            
           // Release Enter
            robot.keyRelease(KeyEvent.VK_ENTER);
            
             // Press CTRL+V
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            
           // Release CTRL+V
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            Thread.sleep(1000);
                   
            //Press Enter 
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception exp) {
        	exp.printStackTrace();
        }
    }

}
