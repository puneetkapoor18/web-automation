package com.lps.qa.objectrepo;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.lps.qa.seleniumbase.SeleniumBase;

public class HomePage extends SeleniumBase{

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(name="destination")
	public WebElement destination;

	@FindBy(name="departDate")
	public WebElement departDate;

	@FindBy(name="arriveDate")
	public WebElement arriveDate;

	@FindBy(xpath="//span[contains(text(),'Search Flights')]")
	public WebElement searchButton;
	
	@FindBy(xpath = "//span[@class='destination-autocomplete__item']")
	public List<WebElement> flyingToCity;
	
	@FindBy(xpath = "//div[@class='trip-dates__depart-date-dialog']//span[contains(text(),'15')]")
	public WebElement departDateWindow;
	
	@FindBy(xpath = "//div[@class='trip-dates__return-date-dialog']//span[contains(text(),'20')]")
	public WebElement arriveDateWindow;

	
	
	public void enterFlyingToCity(String cityCode) {
		
		fluentWait(destination, 5);
		destination.sendKeys(cityCode);
	}
	
	public void selectFlyingToCityFromList(String cityCode) {
		
		for (int i = 0; i < flyingToCity.size(); i++) {
			
			if (flyingToCity.get(i).getText().toLowerCase().startsWith(cityCode)) {
				flyingToCity.get(i).click();
			}
			
		}
	}
	
	public void clickDepartDate() {
		
		fluentWait(departDate, 6);
		departDate.click();
		
	}
	
	public void selectDepartDate() {
		
		fluentWait(departDateWindow, 5);
		departDateWindow.click();
	}
	
	public void selectArrivalDate() {
		
		fluentWait(arriveDateWindow, 5);
		arriveDateWindow.click();
		sleepThread(2000);
	}
	
	
	public void clickSearch() throws InterruptedException {
		
		searchButton.click();
		switchToNewWindow("Flight Centre New Zealand | Availability");
		
	}

}
