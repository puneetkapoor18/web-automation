package com.lps.qa.objectrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.lps.qa.seleniumbase.SeleniumBase;

public class ResultBaggage extends SeleniumBase {
	
	public ResultBaggage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
//	@FindBy(xpath = "//span[@class='jss284']//*[@class='jss253 test-priceSummaryReviewHeaderExpand']")
//	public WebElement expandBtn;
//
//	@FindBy(xpath = "//div[@data-price-item-label='Servicing Fee']//span[@class='test-priceWholeValue']")
//	public WebElement serviceFeeWholePrice;
//	
//	@FindBy(xpath = "//div[@data-price-item-label='Servicing Fee']//span[@class='test-priceCentsValue']")
//	public WebElement serviceFeeCentPrice;
//	
//	@FindBy(xpath = "//div[@data-price-item-label='Merchant Fee']//span[@class='test-priceWholeValue']")
//	public WebElement merchantFeeWholePrice;
//	
//	@FindBy(xpath = "//div[@data-price-item-label='Merchant Fee']//span[@class='test-priceCentsValue']")
//	public WebElement merchantFeeCentPrice;
//	
//	@FindBy(xpath = "//span[@class='jss552 test-priceWholeValue']")
//	public WebElement totalWholePrice;
//	
//	@FindBy(xpath = "//span[@class='jss554 test-priceCentsValue']")
//	public WebElement totalCentPrice;
	
	
	@FindBy(xpath = "//*[contains(@class,'test-priceSummaryReviewHeaderExpand')]")
	public WebElement expandBtn;

	@FindBy(xpath = "//div[@data-price-item-label='Servicing Fee']//span[@class='test-priceWholeValue']")
	public WebElement serviceFeeWholePrice;
	
	@FindBy(xpath = "//div[@data-price-item-label='Servicing Fee']//span[@class='test-priceCentsValue']")
	public WebElement serviceFeeCentPrice;
	
	@FindBy(xpath = "//div[@data-price-item-label='Merchant Fee']//span[@class='test-priceWholeValue']")
	public WebElement merchantFeeWholePrice;
	
	@FindBy(xpath = "//div[@data-price-item-label='Merchant Fee']//span[@class='test-priceCentsValue']")
	public WebElement merchantFeeCentPrice;
	
	@FindBy(xpath = "//div[contains(@class,'test-totalPrice')]//span[contains(@class,'test-priceWholeValue')]")
	public WebElement totalWholePrice;
	
	@FindBy(xpath = "//div[contains(@class,'test-totalPrice')]//span[contains(@class,'test-priceCentsValue')]")
	public WebElement totalCentPrice;
	
	
	public void expandFareSummary() {
		
		fluentWait(expandBtn, 8);
		expandBtn.click();
	}
	
	public Double getServiceFees() {
		
		Double serviceFees = null ;
		fluentWait(serviceFeeWholePrice, 5);
		serviceFees = Double.parseDouble(serviceFeeWholePrice.getText().concat(".").concat(serviceFeeCentPrice.getText()));
		System.out.println("Total Price for Service Fee :: " +serviceFees);
		return serviceFees;
	}
	
	public Double getMerchantFees() {
		
		Double merchantFees = null ;
		fluentWait(merchantFeeWholePrice, 5);
		merchantFees = Double.parseDouble(merchantFeeWholePrice.getText().concat(".").concat(merchantFeeCentPrice.getText()));
		System.out.println("Total Price for Merchant Fee :: " +merchantFees);
		
		return merchantFees;
	}
	
	public Double getTotalPrice() {
		
		Double totalPrice = null;
		totalPrice = Double.parseDouble(totalWholePrice.getText().concat(".").concat(totalCentPrice.getText()));
		System.out.println("Total price :: " +totalPrice);
		
		return totalPrice;
	}
	
}
