package br.usp.ime.escience.expressmatch.model.user.roles;

public enum UserRolesEnum {

	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	private final String value;

	
	private UserRolesEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
