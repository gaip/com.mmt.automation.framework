package setup;

public enum LanguageCode {
English("en"), Deutsch("de");
	
private final String languageCode;

private LanguageCode(String value) {
	languageCode = value;
}

public String getLanguageCode() {
	return languageCode.toLowerCase();
}
}
