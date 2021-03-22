package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import CredentialManager.Credentials;
import loggers.Log;
import setup.EnvironmentConstants;
import setup.LanguageCode;
import utils.DriverUtils;

public class LoginPage extends DriverUtils {

	@FindBy(xpath = "//*[@type='email']")
	public WebElement userEmailorPhone;

	@FindBy(xpath = "//*[text()='Next']/..")
	public WebElement nextButton;

	@FindBy(xpath = "//*[@data-cy='account']")
	public WebElement accountsOption;

	@FindBy(xpath = "//*[@data-cy='googleLogin']")
	public WebElement loginWithGooglePopup;

	@FindBy(css = ".popupSprite.popupGoogleIcon")
	public WebElement loginWithGoogleAccountOption;

	@FindBy(xpath = "//*[@type='password']")
	public WebElement password;

	@FindBy(xpath = "//*[@data-cy='loggedInUser']")
	public WebElement loggedIn;
	
	@FindBy(xpath = "//*[text()='Verify Mobile Number']")
	public WebElement mobileNumberForm;
	

	@FindBy(id = "lang-chooser")
	public WebElement languageChooser;
	
	@FindBy(xpath = "//*[@data-cy='modalClose']")
	public WebElement closeWindow;
	

	
	Credentials credential;

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void performLogin() {
		credential = new Credentials();

		if (isLoginByGoogleDisplayed()) {
			Assert.assertTrue(iselementVisible(loginWithGooglePopup));
			highlightAndClick(loginWithGooglePopup);
			performLoginByGoogleOption();
		} else {
			Log.INFO("Choosing full Path by account option");
			performLoginByAccountOption();
		}

		isLoginSuccessful();
	}

	public boolean isLoginByGoogleDisplayed() {
		return iselementVisible(loginWithGooglePopup);

	}

	public void performLoginByAccountOption() {
		Assert.assertTrue(iselementVisible(accountsOption));
		highlightAndClick(accountsOption);
		for(int i=0;i<5;i++) {
			Log.INFO("Trying element click"+i);
		clickByJSE(loginWithGooglePopup);
		clickByAction(loginWithGooglePopup);
		new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(loginWithGooglePopup));
		}
		performLoginByGoogleOption();
		Assert.assertTrue(iselementVisible(mobileNumberForm,30));
		closeWindow.click();
		
	}

	public void performLoginByGoogleOption() {

		switchToChildWindow();
		iselementVisible(languageChooser,DriverUtils.Second30TimeOut);
		languageChooser.click();
		selectLanguage(LanguageCode.English);
		Log.INFO("Entering username " + credential.getUserName());
		iselementVisible(userEmailorPhone);
		userEmailorPhone.sendKeys(credential.getUserName());
		nextButton.click();
		iselementVisible(password);
		Log.INFO("Entering password " + credential.getPassword());
		password.sendKeys(credential.getPassword());
		nextButton.click();
		switchToParent();
	}

	public void isLoginSuccessful() {
		Assert.assertTrue(iselementVisible(loggedIn,20));
		getDriver().navigate().to(EnvironmentConstants.getURL());
		Log.INFO("logged In username"+loggedIn.getText());
		Assert.assertTrue(loggedIn.getText().toLowerCase().contains("gap"));
	}

	public void selectLanguage(LanguageCode language) {
		
		String selector="//*[@data-value='"+language.getLanguageCode()+"' and @role='option']";
		clickelementByXpath(selector);
		
	}
	
}
