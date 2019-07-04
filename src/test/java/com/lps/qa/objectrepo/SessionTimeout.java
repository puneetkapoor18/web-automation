package com.lps.qa.objectrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.lps.qa.seleniumbase.SeleniumBase;

public class SessionTimeout extends SeleniumBase{
	
	public SessionTimeout(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "/html[1]/body[1]/div[10]/div[2]/div[1]/div[1]/h2[1]")
	public WebElement sessionTimeoutMsg;
	
	
	public String getSessionTimeoutMsg() {
		
		String message = sessionTimeoutMsg.getText();
		return message;
	}

}
