package associates;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import loggers.Log;
import testData.CheckInAndOutData;
import utils.DriverUtils;

public class Hotel extends DriverUtils {

	@FindBy(id = "city")
	public WebElement staycity;

	@FindBy(xpath = "//*[contains(@placeholder,'Enter city/ Hotel')]")
	public WebElement autocompletePopUp;

	@FindBy(id = "react-autowhatever-1-section-0-item-0")
	public WebElement firstSuggestion;

	@FindBy(xpath = "//*[@class='DayPicker-Day' and @aria-disabled='false']")
	public List<WebElement> calenderDates;

	@FindBy(id = "checkin")
	public WebElement checkinDate;

	@FindBy(id = "guest")
	public WebElement guestSelection;
	
	@FindBy(className  = "ageSelectBox")
	public List<WebElement> ageSelector;

	@FindBy(css = ".primaryBtn.btnApply")
	public WebElement applyButton;

	@FindBy(xpath = "//*[@data-cy='travelForText']")
	public WebElement travelFor;
	
	@FindBy(id = "hsw_search_button")
	public WebElement searchButton;
	
	@FindBy(xpath = "//*[@data-cy='checkInDate']/span")
	public List<WebElement> selectCheckInDate;
	
	@FindBy(xpath = "//*[@data-cy='checkOutDate']/span")
	public List<WebElement> selectCheckOutDate;
	
	int endIndex;

	public Hotel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public Hotel enterSearchInput() {
		selectCity("Delhi");
		selectCheckinAndCheckOut();
		selectRoomAndTravellers(4, 3);
		travelReason(TravelReason.Work);
		return this;
		
	}
	
	
	public SearchResults performSearch() {
		CheckInAndOutData.checkinDay=selectCheckInDate.get(0).getText();
		Log.INFO("CheckInDay"+CheckInAndOutData.checkinDay);
		CheckInAndOutData.checkinMonth=selectCheckInDate.get(1).getText();
		Log.INFO("checkinMonth"+CheckInAndOutData.checkinMonth);
		CheckInAndOutData.checkinYear=selectCheckInDate.get(2).getText();
		Log.INFO("checkinYear"+CheckInAndOutData.checkinYear);
		
		CheckInAndOutData.checkOutDay=selectCheckOutDate.get(0).getText();
		Log.INFO("checkOutDay"+CheckInAndOutData.checkOutDay);
		CheckInAndOutData.checkOutMonth=selectCheckOutDate.get(1).getText();
		Log.INFO("checkOutMonth"+CheckInAndOutData.checkOutMonth);
		CheckInAndOutData.checkOutYear=selectCheckOutDate.get(2).getText();
		Log.INFO("checkOutYear"+CheckInAndOutData.checkOutYear);
		
		Assert.assertTrue(iselementVisible(searchButton));
		searchButton.click();
		
		return new SearchResults(getDriver());
	}

	public void selectCity(String city) {
		Assert.assertTrue(iselementVisible(staycity,10));
		staycity.click();
		Assert.assertTrue(iselementVisible(autocompletePopUp));
		autocompletePopUp.sendKeys(city);
		Log.INFO(firstSuggestion.getText());
		firstSuggestion.click();
	}

	public void selectCheckinAndCheckOut() {
		checkinDate.click();
		Assert.assertTrue(calenderDates.size() > 1, "Dates available in calender is more than one");
		chooseDates(calenderDates);
	}

	public void chooseDates(List<WebElement> dates) {
		endIndex = calenderDates.size() > 5 ? 3 : calenderDates.size() - 1;
		Log.INFO("checkout index"+endIndex);
		calenderDates.get(0).click();
		calenderDates.get(endIndex).click();
	}

	public void selectRoomAndTravellers(int adultCount, int childrenCount) {
		guestSelection.click();
		chooseGuestTypes(GuestType.ADULTS, adultCount);
		chooseGuestTypes(GuestType.CHILDREN, childrenCount);
		selectKidsAge();
	//	highlightAndClick(applyButton);
		clickByJSE(applyButton);
	}

	public void chooseGuestTypes(GuestType option, int count) {
		String guestselection = "//*[@data-cy='" + option.toString() + "-" + count + "']";
		clickelementByXpath(guestselection);
	}
	
	public void selectKidsAge() {
			for(WebElement agedropdown :ageSelector) {
				Select selectAge =new Select(agedropdown);
				selectAge.selectByIndex(3);
			}
	}

	public void travelReason(TravelReason option) {
		Assert.assertTrue(iselementVisible(travelFor));
		travelFor.click();
		String travelSelection = "//*[@data-cy='" + "travelFor" + "-" + option.toString() + "']";
		clickelementByXpath(travelSelection);
	}
	
	
}
