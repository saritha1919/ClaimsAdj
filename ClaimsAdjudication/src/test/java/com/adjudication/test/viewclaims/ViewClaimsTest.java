package com.adjudication.test.viewclaims;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.adjudication.base.PropertyFile;
import com.adjudication.base.TestBase;
import com.adjudication.pages.CommonActionsAcrossPages;
import com.adjudication.pages.QueueManagementPage;
import com.adjudication.pages.globalsearch.GlobalSearchPage;
import com.adjudication.pages.home.AdjHomePage;
import com.adjudication.pages.login.LoginPage;
import com.adjudication.pages.viewclaims.ViewClaimsPage;
import com.adjudication.pages.viewclaims.serviceDetails.ServiceDetailsExpansionPage;
import com.adjudication.pages.viewclaims.serviceDetails.ServiceDetailsPage;
import com.adjudication.test.login.LoginTest;
import com.adjudication.utils.Excel;
import com.adjudication.utils.GenericMethods;
import com.adjudication.utils.MyLogger;
import com.adjudication.utils.WaitUtility;

public class ViewClaimsTest extends TestBase{
	
	MyLogger log = new MyLogger(ViewClaimsTest.class.getName());
	
	
}
