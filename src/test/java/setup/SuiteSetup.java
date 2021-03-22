package setup;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import infra.BrowserType;
import infra.DriverFactory;
import loggers.Log;

public class SuiteSetup {
	DriverFactory factory;

	@Parameters({ "browser" })
	@BeforeTest
	public void createInstance(String browser) {

		if (!Optional.ofNullable(factory).isPresent()) {
			factory = new DriverFactory();
		}
		factory.createBrowserInstance(getBrowser(browser)).getDriver().navigate().to(EnvironmentConstants.getURL());
		Log.INFO("Navigating to Url" + EnvironmentConstants.getURL());
	}

	@AfterTest
	public void tearDown() {
		factory.tearDown();
	}

	public WebDriver getDriver() {
		return factory.getDriver();
	}

	public BrowserType getBrowser(String browser) {
		for (BrowserType type : BrowserType.values()) {
			if (type.getBrowserName().equals(browser.toLowerCase())) {
				return type;
			}
		}
		return BrowserType.Chrome;

	}
}
