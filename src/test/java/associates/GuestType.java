package associates;

public enum GuestType {
	ADULTS("adults"),CHILDREN("children");
	
	private final String type;

	private GuestType(String value) {
		type = value;
	}

	public String toString() {
		return type.toLowerCase();
	}
}
