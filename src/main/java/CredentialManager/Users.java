package CredentialManager;

public enum Users {
	Default("mmttest1701@gmail.com"), Invalid("aaa@gmail.com");
	private final String usermail;

	private Users(String value) {
		usermail = value;
	}

	public String getUser() {
		return usermail;
	}
}
