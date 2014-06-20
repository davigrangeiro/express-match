package br.usp.ime.escience.expressmatch.controller.signup;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.stereotype.Component;


@Component
@ManagedBean
@SessionScoped
public class SignUpController implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean signUp;
	
	private SignupVo signupData;
	
	public void init(){
		this.signUp = false;
		this.signupData = new SignupVo();
	}
	
	public String onSignUp(){
		signUp=true;
		return "";
	}
	

	public String onRegister(){
		signUp=true;
		return "";
	}
	
	public boolean isSignUp() { 
		return signUp;
	}

	public void setSignUp(boolean signUp) {
		this.signUp = signUp;
	}

	public SignupVo getSignupData() {
		return signupData;
	}

	public void setSignupData(SignupVo signupData) {
		this.signupData = signupData;
	}
	
	
	
}
