package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import associates.Hotel;
import associates.MenuOptions;
import associates.Menus;
import utils.DriverUtils;

public class LandingPage extends DriverUtils{

	public LandingPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public Hotel navigateToHotel() {
		new MenuOptions().navigateToMenu(getDriver(), Menus.HOTELS);;
		
		return new Hotel(getDriver());
	}
	
}
