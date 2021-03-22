package associates;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import loggers.Log;
import utils.DriverUtils;

public class MenuOptions {
	
	
	
	
	public void navigateToMenu(WebDriver driver, Menus option) {
		try {
			String selector="//*[@data-cy='"+selectMenu(option)+"']/a";
			Log.INFO("Clicking on "+selector);
			new WebDriverWait(driver, Duration.ofSeconds(DriverUtils.medium))
			.until(ExpectedConditions.elementToBeClickable(By.xpath(selector))).click();
		}catch(Exception ex) {
			Log.INFO(ex.getMessage());
		   throw new RuntimeException("Unable to click Element"+ex.getMessage());
		}
		
	}
	
	
	

	public String selectMenu(Menus option) {

		switch (option) {
		case BUSES:
			return "menu_Buses";
		case CAB:
			return "menu_Cabs";
		case CHARTED_FLIGHTS:
			return "menu_Charters";
		case FLIGHTS:
			return "menu_Flights";
		case HOLIDAYS:
			return "menu_Holidays";
		case HOTELS:
			return "menu_Hotels";
		case MORE_MENU:
			return "menu_More";
		case TRAINS:
			return "menu_Trains";
		case VILLA_OR_APARTMENT:
			return "menu_Villasmore";
		case VISA:
			return "menu_Visa";
		default:
			return "menu_Hotels";

		}

	}

}
