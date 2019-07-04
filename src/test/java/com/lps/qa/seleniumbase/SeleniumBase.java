package com.lps.qa.seleniumbase;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.lps.qa.util.PropertiesUtil;

public class SeleniumBase {

	private static ThreadLocal<WebDriver> WEB_DRIVERS_PROVIDER = new ThreadLocal<WebDriver>();

	protected static WebDriver getWebDriver() {
		return WEB_DRIVERS_PROVIDER.get();
	}

	private static void setWebDriver(WebDriver driver) {
		WEB_DRIVERS_PROVIDER.set(driver);
	}


	protected SeleniumBase(){
	}

	protected SeleniumBase(String browser, String os) throws IOException{

		if(getWebDriver() == null)
			setWebDriver(invokeBrowser(browser, os));
	}


	public static synchronized void initializeDriver(String browser, String os) throws IOException{
		if(getWebDriver() == null)
			setWebDriver(invokeBrowser(browser, os));
	}

	
	protected static void closeDriver(){
		try{
			getWebDriver().close();
		}catch(Exception ex){
			System.err.println(ex);
		}
	}

	public static void quitDriver(){
		try{
			closeDriver();
			getWebDriver().quit();
			WEB_DRIVERS_PROVIDER.remove();
		}catch(Exception ex){
			System.err.println(ex);
		}
	}
	
	

	private static WebDriver invokeBrowser (String browser, String os) throws IOException {

		WebDriver driver = null ;

		System.out.println("browser = "+ browser);
		if(browser.equalsIgnoreCase("firefox"))
		{
			DesiredCapabilities dc=new DesiredCapabilities();
			dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);

			driver =new FirefoxDriver(dc);

		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			if(os.equalsIgnoreCase("win"))
			{
				System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("WindowsChromeDriver"));
			}
			else if(os.equalsIgnoreCase("mac")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("MacChromeDriver"));

			}

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			driver = new ChromeDriver(options);
		}

		driver.manage().window().maximize();

		return driver;

	}

	
	protected void openURL(String url){
		System.out.println(getWebDriver());
		getWebDriver().get(url);
	}


	protected void switchToNewWindow(String title){
		Set<String> windows = getWebDriver().getWindowHandles();
		for(String window : windows){

			getWebDriver().switchTo().window(window);
			if(getWebDriver().getTitle().contains(title))
				break;
		}
	}


	protected void sleepThread(long milliseconds)
	{
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	protected Boolean fluentWait(final WebElement locator,
			final int timeoutSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getWebDriver())
				.withTimeout(timeoutSeconds, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);

		return wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver webDriver) {
				System.out.println("Value in Fluent for displayed ::" +locator.isDisplayed());
				return locator.isDisplayed();
			}
		});
	}

}


