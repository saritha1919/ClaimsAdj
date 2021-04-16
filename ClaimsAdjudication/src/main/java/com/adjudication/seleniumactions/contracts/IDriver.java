package com.adjudication.seleniumactions.contracts;

public interface IDriver {

	public void openBrowserAndNavigateToUrl(String url) throws Exception;
	
	public String getTitle() throws Exception;
	
	public String getCurrentUrl() throws Exception;
	
	public String getPageResource() throws Exception;
	
	public void closeBrowser() throws Exception;
	
	public void closeAllBrowsers() throws Exception;
	
}
