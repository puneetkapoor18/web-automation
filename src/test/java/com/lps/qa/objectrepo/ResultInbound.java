package com.lps.qa.objectrepo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.lps.qa.seleniumbase.SeleniumBase;

public class ResultInbound extends SeleniumBase{

	public ResultInbound(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
//	@FindBy(xpath = "//span[@class='jss540 test-priceWholeValue']")
//	public List<WebElement> wholePrice;
//
//	@FindBy(xpath = "//span[@class='jss542 test-priceCentsValue']")
//	public List<WebElement> centPrice;
//	
//	@FindBy(xpath = "//div[@class='buttonWrapper-0-114 test-toggler']")
//	public List<WebElement> toggleBtn;
//	
//	@FindBy(xpath = "//div[@class='selectButtonArea-0-215']")
//	public List<WebElement> selectBtn;
	
	
	@FindBy(xpath = "//span[contains(@class,'test-priceWholeValue')]")
	public List<WebElement> wholePrice;

	@FindBy(xpath = "//span[contains(@class,'test-priceCentsValue')]")
	public List<WebElement> centPrice;
	
	@FindBy(xpath = "//div[contains(@class,'test-toggler')]")
	public List<WebElement> toggleBtn;
	
	@FindBy(xpath = "//div[contains(@class,'selectButtonArea')]")
	public List<WebElement> selectBtn;

	
	
	public boolean verifySearchResultForArrival() {

		sleepThread(5000);
		List<Float> completePrice = new ArrayList<Float>();

		boolean isSorted = true;

		for (int i = 0; i < wholePrice.size(); i++) {

			String price = wholePrice.get(i).getText().replace(",", "");

			completePrice.add(Float.parseFloat(price.concat(".").concat(centPrice.get(i).getText())));

		}

		for (int j = 1; j < completePrice.size(); j++) {
			if (completePrice.get(j-1).compareTo(completePrice.get(j)) > 0 ) {
				isSorted = false;
				break;

			}
		}
		return isSorted;
	}
	
	
	public void showPriceToSelectForArrival(String index) {
		
		fluentWait(toggleBtn.get(Integer.parseInt(index)), 5);
		toggleBtn.get(Integer.parseInt(index)).click();
		
	}
	
	public void clickSelectBtnForArrival(String index) {
	
//		fluentWait(selectBtn.get(Integer.parseInt(index)), 10);
		selectBtn.get(Integer.parseInt(index)).click();
	}
	
	public Double getArrivalPrice(String index) {
		
		fluentWait(wholePrice.get(Integer.parseInt(index)), 5);
		String price = wholePrice.get(Integer.parseInt(index)).getText();
		Double finalPrice = Double.parseDouble(price.concat(".").concat(centPrice.get(Integer.parseInt(index)).getText()));
		return finalPrice;
	}
}
