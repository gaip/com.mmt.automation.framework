package infra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import loggers.Log;

public class DriverFactory {
	private static ThreadLocal<WebDriver> localizedDriver = new ThreadLocal<WebDriver>();

	public DriverFactory createBrowserInstance(BrowserType type) {
		WebDriver driver = null;
		switch (type) {
		case Chrome:
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-web-security");
			/*options.addArguments("--user-data-dir");*/
			options.addArguments("--allow-running-insecure-content"); 
			driver = new ChromeDriver(options);
			Log.INFO("Launching browser with Chrome");
			break;

		case FireFox:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			Log.INFO("Launching browser with Firefox");
			break;

		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().getImplicitWaitTimeout();
		localizedDriver.set(driver);
		return this;
	}

	public WebDriver getDriver() {
		return localizedDriver.get();
	}

	public void tearDown() {
		localizedDriver.get().quit();
		localizedDriver.remove();
	}

}