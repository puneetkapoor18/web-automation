package com.lps.qa.runner;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lps.qa.testbase.TestBase;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;



@CucumberOptions(
		features="src/test/resource/features",
		glue={"com.lps.qa.stepdefinition"},
		plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
		monochrome = true
		)

public class TestRunner extends TestBase{

	@Test(dataProvider="features")
	public void login(CucumberFeatureWrapper cFeature) throws Throwable
	{
		testRunner.runCucumber(cFeature.getCucumberFeature());
	}
	
	
	@DataProvider(name="features")
	public Object[][] getFeatures()
	{
		return testRunner.provideFeatures();
	}
}
