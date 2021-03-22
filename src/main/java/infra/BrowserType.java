package infra;

public enum BrowserType {

	Chrome("chrome"), FireFox("firefox");

	private final String browser;

	private BrowserType(String value) {
		browser = value;
	}

	public String getBrowserName() {
		return browser.toLowerCase();
	}

}
