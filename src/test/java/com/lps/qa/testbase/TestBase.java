package com.lps.qa.testbase;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.cucumber.listener.Reporter;
import com.lps.qa.seleniumbase.SeleniumBase;
import com.lps.qa.util.EmailUtil;
import com.lps.qa.util.PropertiesUtil;

import cucumber.api.testng.TestNGCucumberRunner;

public class TestBase {

	protected String environment = null;
	protected String operatingSystem = null;
	protected String browser = null;
	protected TestNGCucumberRunner testRunner;

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("Preparing to Load Constant file..");
		PropertiesUtil.loadConstantFile("Constant.cfg");

	}

	@BeforeTest(alwaysRun = true)
	@Parameters({"environment"})
	public void setUpEnvironment(@Optional("Staging")String environment) throws Exception {
		String fileName = environment+".cfg";
		System.out.println("Preparing to Load " + fileName + " file..");

		PropertiesUtil.loadEnvConfigFile(fileName);
	}

	@BeforeClass
//	@Parameters({"os","browser"})
	@Parameters({"browser"})
	public void beforeEveryClass(@Optional("chrome")String br) throws Exception{
		operatingSystem = System.getProperty("os.name");
		browser = br;

		testRunner = new TestNGCucumberRunner(this.getClass());
		
		SeleniumBase.initializeDriver(br,operatingSystem);
	}


	@AfterClass
	public void tearDown()
	{
		Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("reportConfigPath")));
		testRunner.finish();
	}	

	@AfterTest
	public void afterEachTestCycle() {
		// terminate web driver if not terminated
		SeleniumBase.quitDriver();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {

		File f = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("reportPath"));

		if(f.exists()){

			EmailUtil.sendEmail(System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("reportPath"),
					PropertiesUtil.getConstantProperty("EmailReciever"),
					PropertiesUtil.getConstantProperty("EmailSender"),
					PropertiesUtil.getConstantProperty("EmailPassword"),
					PropertiesUtil.getConstantProperty("EmailReportSubject"),
					PropertiesUtil.getConstantProperty("EmailReportBody"));
		}else{
			System.out.println("REPORT FILE NOT CREATED");
		}

	}


}
