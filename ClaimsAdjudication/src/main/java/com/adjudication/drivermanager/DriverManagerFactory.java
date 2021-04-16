package com.adjudication.drivermanager;

public class DriverManagerFactory {
	
	public static DriverManager getDriverManager(String browserType) throws Exception{
		DriverManager driverManger;
		switch (browserType.toLowerCase()) {
		case "chrome":
			driverManger=new ChromeDriverManager();
			break;
		case "firefox":
			driverManger=new FireFoxDriverManager();
			break;
		default:
			throw new Exception("Invalid browser type:"+browserType);
		}
		return driverManger;
	}
}
