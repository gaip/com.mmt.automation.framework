package utils;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import loggers.Log;

public class DriverUtils {
	public static final int shortTimeOut = 5;
	public static final int medium = 10;
	public static final int Second30TimeOut = 10;
	public static String parentWindow,childWindow;

	private WebDriver driver;

	public DriverUtils(WebDriver driver) {
		this.driver = driver;
	}

	public boolean iselementVisible(WebElement element, int timeout) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(timeout))
					.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
			return false;
		}

	}

	public void highlightAndClick(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].setAttribute('style','background: yellow; border: 2px solid red;')", element);
			new Actions(driver).clickAndHold(element).release().perform();
		} catch (Exception ex) {
			throw new RuntimeException("Unable to click" + ex.getMessage());
		}
	}

	public void clickByJSE(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void clickByAction(WebElement element) {

		new Actions(driver).moveToElement(element.findElement(By.xpath(".."))).click().build().perform();
	}

	public boolean iselementVisible(WebElement element) {
		return iselementVisible(element, shortTimeOut);
	}

	public void clickelementByXpath(String xpathRule) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(DriverUtils.shortTimeOut))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathRule))).click();
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
			throw new RuntimeException("Unable to click Element" + ex.getMessage());
		}
	}
	
	public String  getTextelementByXpath(String xpathRule) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(DriverUtils.shortTimeOut))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathRule))).getText();
		} catch (Exception ex) {
			Log.INFO(ex.getMessage());
			throw new RuntimeException("Unable to click Element" + ex.getMessage());
		}
	}

	public void switchToChildWindow() {
		parentWindow = driver.getWindowHandle();
		Set<String> s = driver.getWindowHandles();
		Iterator<String> I1 = s.iterator();
		while (I1.hasNext()) {
			String child_window = I1.next();
			if (!parentWindow.equals(child_window)) {
				driver.switchTo().window(child_window);
			childWindow=driver.switchTo().window(child_window).getTitle();
			}
		}
	
	}

	public void switchToParent() {
		driver.switchTo().window(parentWindow);

	}

	public WebDriver getDriver() {
		return driver;
	}

	public void scrollToElement(WebElement element) {

		new Actions(driver).moveToElement(element).perform();
	}

	public void sleep(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
public	void waitForLoad(WebDriver driver, int timeOut) {
	    new WebDriverWait(driver,  Duration.ofSeconds(timeOut)).until((ExpectedCondition<Boolean>)wd->((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
public void scrollToElementByJSe(WebElement element) {
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView();", element);
}
}
