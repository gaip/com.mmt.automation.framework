package associates;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import loggers.Log;
import testData.CheckInAndOutData;
import utils.DriverUtils;

public class SearchResults extends DriverUtils {
	
	@FindBy(id = "hlistpg_fr_user_rating")
	public WebElement userRating;
	
	@FindBy(id = "hlistpg_hotel_name")
	public WebElement firstHotelName;
	
	
	@FindBy(xpath = "//*[text()='4 & above (Very Good)']")
	public WebElement ratingFour;

	public By userRatingList=By.tagName("li");
	
	@FindBy(className  = "input-range__slider")
	public WebElement priceSlider;

	@FindBy(className  = "minValue")
	public WebElement minAmount;
	
	
	
	String requiredPrice;
	
	public SearchResults(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public SearchResults setMinPrice(String requiredPrice) {
		this.requiredPrice=requiredPrice;
		waitForLoad(getDriver(), 60);
		Assert.assertTrue(iselementVisible(priceSlider, 60));
		scrollToElementByJSe(priceSlider);
		return this;
	}
	
	public SearchResults applyPriceFilter(int maxRetry) {
		
		if (isAmountNotMatched() && maxRetry > 0) {
		priceSlider.sendKeys(Keys.ARROW_RIGHT);
			applyPriceFilter(--maxRetry);
		}
		return this;
	}
	
	public SearchResults applyRatingFilter(String requiredUserRating) {
	
		scrollToElementByJSe(ratingFour);
		clickByJSE(ratingFour);
		return this;
	}
	
	public boolean isAmountNotMatched() {
		Log.INFO("Amount is "+minAmount.getText());
		Log.INFO("Amount Rrquired"+requiredPrice);
		Log.INFO("is not Contains Amount"+!minAmount.getText().toString().contains(requiredPrice));
		return !minAmount.getText().toString().contains(requiredPrice);
	}

	public BookingPage selectHotel(int selectHotelIndex) {
		selectHotelIndex=selectHotelIndex-1;
		Assert.assertTrue(iselementVisible(firstHotelName, 20), "Waiting for 20 second for search result to appear");
		String xpathRule="//*[@id='Listing_hotel_"+selectHotelIndex+"'] /descendant::*[@id='hlistpg_hotel_name']/span";
		CheckInAndOutData.hotelName=getTextelementByXpath(xpathRule);
		Log.INFO(CheckInAndOutData.hotelName);
		clickelementByXpath(xpathRule);
		switchToChildWindow();
		return new BookingPage(getDriver());
	}
	
}
