package tests;

import org.testng.annotations.Test;

import pageObjects.LandingPage;
import pageObjects.LoginPage;
import setup.SuiteSetup;

public class MakeMyTripBaseTests  extends SuiteSetup{

	
@Test(priority = 1)
public void userlogin() {	
LoginPage page =new LoginPage(getDriver());
page.performLogin();

}

@Test(priority = 2)
public void reservation() throws InterruptedException {	
LandingPage page =new LandingPage(getDriver());
page.navigateToHotel()
	.enterSearchInput()
	.performSearch()
	.setMinPrice("2000")
	.applyPriceFilter(5)
	.applyRatingFilter("4").selectHotel(5).performBooking().reviewAndPay();

Thread.sleep(14000);

}

}
