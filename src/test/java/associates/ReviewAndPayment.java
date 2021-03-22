package associates;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utils.DriverUtils;

public class ReviewAndPayment extends DriverUtils {
	
	@FindBy(id = "myself")
	public WebElement bookingFormySelf;
	
	@FindBy(id = "mNo")
	public WebElement mobileNumber;
	
	@FindBy(xpath = "//*[text()='Pay Now']")
	public WebElement payNow;
	
	@FindBy(xpath = "//*[text()='Fare Summary']")
	public WebElement fareSummary;
	
	@FindBy(xpath = "//*[text()='Payment options']")
	public WebElement paymentOptions;
	
	@FindBy(xpath = "//*[text()='Review your Booking']")
	public WebElement reviewYourBooking;
	@FindBy(xpath = "//*[text()='Hotel Information']")
	public WebElement hotelInformation;
	
	public ReviewAndPayment(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void reviewAndPay() {
		Assert.assertTrue(iselementVisible(reviewYourBooking));
		Assert.assertTrue(iselementVisible(hotelInformation));
		scrollToElementByJSe(bookingFormySelf);
		clickByJSE(bookingFormySelf);
		mobileNumber.sendKeys("9999999999");
		iselementVisible(payNow,10);
		payNow.click();
		Assert.assertTrue(iselementVisible(paymentOptions,10));
		scrollToElementByJSe(fareSummary);
	}
	


}
