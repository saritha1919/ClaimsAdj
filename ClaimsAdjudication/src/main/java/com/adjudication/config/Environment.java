package com.adjudication.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@org.aeonbits.owner.Config.Sources({
	"file:src/main/java/com/adjudication/config/${env}.properties",
	"classpath:src/main/java/com/adjudication/config/${env}.properties"
})

public interface Environment extends Config {
	
	String browser();
	String username();
	String password();
	String url();
	String screenshotsFolderPath();
	String ExcelPath();
	String MsgPropertyPath();
	String ElementsInSideMenuPath();	
	String ElementsInAdjSideMenuPath();
	String CommonElementsPath();
	String TestValorDBConnectionString();
	String QueryFilePath();
	String ClaimIdsFilePath();
}
