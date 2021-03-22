package associates;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import loggers.Log;
import testData.CheckInAndOutData;
import utils.DriverUtils;

public class BookingPage extends DriverUtils {

	@FindBy(id = "detpg_hotel_rooms")
	public WebElement roomType;

	@FindBy(id = "detpg_multi_2_add_room")
	public WebElement addRoom;

	@FindBy(id = "checkin")
	public WebElement checkIndate;
	
	@FindBy(id = "checkout")
	public WebElement checkOutDate;
	
	@FindBy(id = "guest")
	public WebElement guests;
	
	@FindBy(id = "detpg_hotel_name")
	public WebElement hotelName;
	
	@FindBy(id = "detpg_bread_crumbs")
	public WebElement bread_crumbs;
	
	
	
	
	@FindBy(id = "detpg_confirm_booking_btn")
	public WebElement reviewBooking;

	@FindBy(xpath = "//*[contains(text(),'Recommended for')]")
	public WebElement recommendation;

	@FindBy(xpath = "//*[contains(text(),'Or make your own choice')]")
	public WebElement chooseRooms;

	@FindBy(xpath = "//*[@class='primaryBtn btnMakePayment']")
	public WebElement continueButton;

	@FindBy(xpath = "//*[@class='selectedRoomRow']")
	public List<WebElement> roomList;

	@FindBy(id = "detpg_cart_total_price_per_night")
	public WebElement chargePerNight;

	@FindBy(xpath = "//*[@id='detpg_cart_total_price_per_night']/following-sibling::*")
	public List<WebElement> bookingdetails;

	int roomSelectionCounter = 0;

	public BookingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public ReviewAndPayment performBooking() {
		validateHotelTab();
		selectRoomType(4);
		return new ReviewAndPayment(getDriver());
	}

	public void validateHotelTab() {
		Log.INFO(getDriver().getCurrentUrl());
		Log.INFO("isBreadcrumbs visible "+iselementVisible(bread_crumbs,DriverUtils.Second30TimeOut));
		Assert.assertTrue(iselementVisible(checkIndate, DriverUtils.medium));
		Log.INFO(checkIndate.getText());
		Assert.assertTrue(iselementVisible(checkOutDate, DriverUtils.medium));
		Log.INFO(checkOutDate.getText());
		Log.INFO("Wait for hotelName to appear"+iselementVisible(hotelName,DriverUtils.medium));
		Log.INFO(DriverUtils.childWindow);
		Log.INFO(hotelName.getText());
		Assert.assertTrue(DriverUtils.childWindow.contains(CheckInAndOutData.hotelName)|| hotelName.getText().toLowerCase().contains(CheckInAndOutData.hotelName.toLowerCase()));
	}

	public void selectRoomType(int numberOfroom) {
		scrollToElementByJSe(roomType);
		roomType.click();
		Assert.assertTrue(iselementVisible(recommendation, DriverUtils.medium));
		scrollToElementByJSe(chooseRooms);

		for (int i = 0; i < numberOfroom; i++) {
			clickRoomifAvailable();
		}
		storePriceTaxesAndBookingDetails();
		waitforReviewBookingToLoad(10);
		Assert.assertTrue(iselementVisible(reviewBooking, DriverUtils.medium));
		reviewBooking.click();
		if (iselementVisible(continueButton)) {
			continueButton.click();
		}

	}


	
	public void clickRoomifAvailable() {
		if (addRoom.isEnabled()) {
			addRoom.click();
			roomSelectionCounter = roomSelectionCounter + 1;
			Assert.assertEquals(roomList.size(), roomSelectionCounter);
			Log.INFO("Size of room Now" + roomList.size());
		}
	}

	public void storePriceTaxesAndBookingDetails() {
		Assert.assertTrue(iselementVisible(chargePerNight));
		CheckInAndOutData.totalBookingCharges = chargePerNight.getText();
		for (WebElement element : bookingdetails) {
			if (element.getText().contains("tax")) {
				Assert.assertTrue(iselementVisible(element));
				CheckInAndOutData.taxes = element.getText();
			} else {
				CheckInAndOutData.totalGuestInfo = element.getText();
			}
		}
		Log.INFO("Total Booking chareges" + CheckInAndOutData.totalBookingCharges);
		Log.INFO("Total tax chareges" + CheckInAndOutData.taxes);
		Log.INFO("Total Guest information" + CheckInAndOutData.totalGuestInfo);
	}

	public void waitforReviewBookingToLoad(int tries) {
		
		for(int i=0;i<tries;i++) {
			Log.INFO(reviewBooking.getText());
			if(reviewBooking.getText().toLowerCase().contains("review")) {
				break;
			}
			sleep(1);
		}
		
	}
	
}
