package CredentialManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class Credentials {
	String username, password;
	private static final Map<String, String> usermap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
		put(Users.Default.getUser(), "@Test1234");
		put(Users.Invalid.getUser(), "sdsdsdsdsdsss");
		}
		
	};
	
	public void setTestUserName(String username) {
		this.username = username;
	}

	public void setTestPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		if(Optional.ofNullable(username).isPresent()) {
			return username;
	
		}
		return  Users.Default.getUser().toString();
	}

	public String getPassword() {
		if(Optional.ofNullable(password).isPresent()) {
		return password;
	}
	return usermap.get(Users.Default.getUser());
	}
}
