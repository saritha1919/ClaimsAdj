package com.adjudication.reports;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.adjudication.utils.Screenshot;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


public class TestListener  implements ITestListener{


	/**
	 * On test failure taking screenshot
	 *
	 * @param result the result
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			Screenshot screenshot = new Screenshot(getDriverFromBaseTest(result));
			String screenshotName = screenshot.capture(result.getName());
			File file = new File(screenshotName);
			String absolutepath = file.getAbsolutePath();
			ExtentTestManager.getTest().fail("Screenshot",
					MediaEntityBuilder.createScreenCaptureFromPath(absolutepath).build());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());
	}

	/**
	 * On test success adding count to the list and logging result
	 *
	 * @param result the result
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	/**
	 * On test skipped adding count to the list and logging result.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		
		ExtentTestManager.getTest().log(Status.SKIP,result.getThrowable());
	}

	/**
	 * On finish adding the count to the list and logging result.
	 *
	 * @param context the context
	 */
	@Override
	public void onFinish(ITestContext context) {
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();

		/*try {
			SendMail.send(emailFromAddress, emailList, "Report", emailMsgTxt);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/**
	 * On test start logging method name in report.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestStart(ITestResult result) {
		ExtentTestManager.startTest(result.getMethod().getRealClass().getName() + ":" + result.getMethod().getMethodName());
	}

	/**
	 * Report log.
	 *
	 * @param message the message
	 */
	public static void reportLog(String message) {
		ExtentTestManager.getTest().log(Status.INFO, message);
	}

	/**
	 * Gets the driver from base test.
	 *
	 * @param result the result
	 * @return the driver from base test
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private WebDriver getDriverFromBaseTest(ITestResult result) throws Exception {
		WebDriver driver = null;

		try {
			Class<? extends ITestResult> testClass = (Class<? extends ITestResult>) result.getInstance().getClass();

			Class<? extends ITestResult> baseTestClass = (Class<? extends ITestResult>) testClass.getSuperclass();
					
			Field driverField = baseTestClass.getDeclaredField("driver");
			driverField.setAccessible(true);
			driver =  (WebDriver) driverField.get(result.getInstance());
			return driver;
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException ex) {
			throw new Exception("error getting the driver from base test");
		}

	}
	/**
	 * On test failed but within success percentage.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	/**
	 * On start.
	 *
	 * @param context the context
	 */
	@Override
	public void onStart(ITestContext context) {
		
	}
}
