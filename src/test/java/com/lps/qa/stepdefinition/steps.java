package com.lps.qa.stepdefinition;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.Assert;

import com.lps.qa.objectrepo.HomePage;
import com.lps.qa.objectrepo.ResultBaggage;
import com.lps.qa.objectrepo.ResultInbound;
import com.lps.qa.objectrepo.ResultsOutbound;
import com.lps.qa.objectrepo.SessionTimeout;
import com.lps.qa.seleniumbase.SeleniumBase;
import com.lps.qa.util.PropertiesUtil;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class steps extends SeleniumBase {

	HomePage homePage = new HomePage(getWebDriver());
	ResultsOutbound resultPage = new ResultsOutbound(getWebDriver());
	ResultInbound resultInboundPage = new ResultInbound(getWebDriver());
	ResultBaggage resultBaggage = new ResultBaggage(getWebDriver());
	SessionTimeout sessionTimeout = new SessionTimeout(getWebDriver());
	Double departingPrice;
	Double inboundPrice;

	@Given("^a user is on home page$")
	public void homePage() {

		openURL(PropertiesUtil.getEnvConfigProperty("BaseUrl"));
		
	}
	
	@Given("^user enter the details$")
	public void enterUserDetails() {
		
		homePage.enterFlyingToCity("chc");
		homePage.selectFlyingToCityFromList("chc");
		homePage.clickDepartDate();
		homePage.selectDepartDate();
		homePage.selectArrivalDate();
		
	}
	
	@Given("^user click on the search$")
	public void clickSearch() throws InterruptedException {
		
		boolean flag = false;
		StopWatch pageLoad = new StopWatch();
		pageLoad.start();
		homePage.clickSearch();
		pageLoad.stop();
		long pageLoadTimeMilliSecond = pageLoad.getTime();
        long pageLoadTimeSeconds = pageLoadTimeMilliSecond / 1000;
		System.out.println("Total Time for page load :: "+pageLoadTimeSeconds); 
		if (pageLoadTimeSeconds <= Long.parseLong(PropertiesUtil.getEnvConfigProperty("Timeout")))
			flag = true;
		Assert.assertTrue(flag,"Page load time is more than 3.5 sec");
		
	}
	
	@Then("^search result should be in ascending order for Departure page$")
	public void verifySearchResultForDeparture() {
		
		boolean isSorted = resultPage.verifySearchResultForDeparture();
		System.out.println("Departure Sorting :: " +isSorted);
		Assert.assertTrue(isSorted,"Values on the search page are not sorted in Ascending order");
	}
	
	@Given("^user select the \"([^\"]*)\" price for Departing Flight$")
	public void selectPriceForDepartingFlight(String index) {
		
		boolean flag = false;
		departingPrice = resultPage.getDeparturePrice(index);
		System.out.println("departingPrice :: "+departingPrice);
		resultPage.showPriceToSelectForDeparture(index);
		
		StopWatch pageLoad = new StopWatch();
		pageLoad.start();
		resultPage.clickSelectBtnForDeparture(index);
		pageLoad.stop();
		long pageLoadTimeMilliSecond = pageLoad.getTime();
		System.out.println("Total Time for page load 2 in millis:: "+pageLoadTimeMilliSecond);
        long pageLoadTimeSeconds = pageLoadTimeMilliSecond / 1000;
		System.out.println("Total Time for page load 2 :: "+pageLoadTimeSeconds);
		if (pageLoadTimeSeconds <= Long.parseLong(PropertiesUtil.getEnvConfigProperty("Timeout")))
			flag = true;
		Assert.assertTrue(flag,"Page load time is more than 3.5 sec");
		
		
	}
	
	@Then("^search result should be in ascending order for Arrival page$")
	public void verifySearchResultForInbound() {
		
		boolean isSorted = resultInboundPage.verifySearchResultForArrival();
		System.out.println("Arrival Sorting :: " +isSorted);
		Assert.assertTrue(isSorted,"Values on the search page are not sorted in Ascending order");
	}
	
	@Given("^user select the \"([^\"]*)\" price for Inbound Flight$")
	public void selectPriceForInboundFlight(String index) {
		
		boolean flag = false;
		inboundPrice = resultInboundPage.getArrivalPrice(index);
		System.out.println("inboundPrice :: "+ inboundPrice);
		resultInboundPage.showPriceToSelectForArrival(index);
		
		StopWatch pageLoad = new StopWatch();
		pageLoad.start();
		resultInboundPage.clickSelectBtnForArrival(index);
		pageLoad.stop();
		long pageLoadTimeMilliSecond = pageLoad.getTime();
		System.out.println("Total Time for page load 3 in millis:: "+pageLoadTimeMilliSecond);
        long pageLoadTimeSeconds = pageLoadTimeMilliSecond / 1000;
		System.out.println("Total Time for page load 3 :: "+pageLoadTimeSeconds);
		
		if (pageLoadTimeSeconds <= Long.parseLong(PropertiesUtil.getEnvConfigProperty("Timeout")))
			flag = true;
		Assert.assertTrue(flag,"Page load time is more than 3.5 sec");

		
	}
	
	@Then("^user total price should be correct$")
	public void checkTotalPrice() {
		
		resultBaggage.expandFareSummary();
		Double serviceFees = resultBaggage.getServiceFees();
		Double merchantFees = resultBaggage.getMerchantFees();
		Double totalPrice = serviceFees + merchantFees + departingPrice + inboundPrice;
		System.out.println("Total Price on summary page :: " +totalPrice);
		
		Assert.assertEquals(totalPrice, resultBaggage.getTotalPrice(),"Total price doesn't match on the result baggage screen");
		
	}
	
	@Then("^user session time out with message \"([^\"]*)\"$")
	public void checkSessionTimeout(String msg) {
		
		sleepThread(1800000);
		String message = sessionTimeout.getSessionTimeoutMsg();
		System.out.println("Timeout message :: " +message);
		Assert.assertEquals(msg, message,"Time out message is not same");
		
	}

}
