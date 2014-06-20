package br.usp.ime.escience.expressmatch.controller.signup;

import br.usp.ime.escience.expressmatch.model.Institution;

public class SignupVo {

	private String nick;
	private String pass;
	private String passRepeat;
	private Institution institution;
	private String name;
	private String nationaity;
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationaity() {
		return nationaity;
	}
	public void setNationaity(String nationaity) {
		this.nationaity = nationaity;
	}
	public String getPassRepeat() {
		return passRepeat;
	}
	public void setPassRepeat(String passRepeat) {
		this.passRepeat = passRepeat;
	}
	
}
