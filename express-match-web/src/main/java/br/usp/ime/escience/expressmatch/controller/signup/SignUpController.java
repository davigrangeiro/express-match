package br.usp.ime.escience.expressmatch.controller.signup;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Institution;
import br.usp.ime.escience.expressmatch.service.InstitutionServiceProvider;
import br.usp.ime.escience.expressmatch.service.signup.SignUpServiceProvider;
import br.usp.ime.escience.expressmatch.service.signup.SignupVo;
import br.usp.ime.escience.expressmatch.utils.FacesUtils;

@Component
@ManagedBean
@SessionScoped
public class SignUpController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private InstitutionServiceProvider institutionServiceProvider;
	
	@Autowired
	private SignUpServiceProvider signUpServiceProvider;
	
	private List<Institution> institutions;
	
	private Institution newInstitution;
	
	private SignupVo signupData;
	
	@PostConstruct
	public void init() {
		if(null == institutions){
			this.institutions = institutionServiceProvider.getAllInstitutions();
			this.newInstitution = new Institution();
			this.signupData = new SignupVo();
		}
	}

	public String onRegister() {
		if(validateRegister()){
			
			try {
				this.signUpServiceProvider.register(signupData, newInstitution);
				FacesUtils.addMessage("Success", "You've been successfully registered, please sign in.", null);
			} catch (ExpressMatchException e) {
				FacesUtils.addMessage(e);
			}
			
		}
		return "";
	}
	
	private boolean validateRegister(){
		boolean res = true;
		
		if(signupData.getNick() == null || signupData.getNick().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Username");
		} else if (!this.signUpServiceProvider.isValidNickName(signupData.getNick())) {
			res = false;
			FacesUtils.addMessage("Username already registred", "The entered username has already been registered, please choose another one.", FacesMessage.SEVERITY_WARN);
		}
		
		if(signupData.getName() == null || signupData.getName().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Name");
		}
		if(signupData.getEmail() == null || signupData.getEmail().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Email");
		}
		if(signupData.getEmail() == null || signupData.getEmail().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Email");
		}
		if(signupData.getPass() == null || signupData.getPass().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Password");
		} else if (signupData.getPass().length() < 8) {
			res = false;
			FacesUtils.addMessage("Password Error", "The entered password shold be longer than 8 characters", FacesMessage.SEVERITY_WARN);
		}
		if(signupData.getPassRepeat() == null || signupData.getPassRepeat().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Password retype");
		}
		if(signupData.getPassRepeat() != null && signupData.getPass() != null && !signupData.getPassRepeat().isEmpty() && !signupData.getPass().isEmpty() && !signupData.getPass().equals(signupData.getPassRepeat())) {
			FacesUtils.addMessage("Password Error", "The entered passwords does not match", FacesMessage.SEVERITY_WARN);
			res = false;
		}
		if(signupData.getNationality() == null || signupData.getNationality().isEmpty()){
			res = false;
			FacesUtils.addEmptyFieldMessage("Nationality");
		}
		if(newInstitution.getId() == -1) {
			res = false;
			FacesUtils.addEmptyFieldMessage("Institution");
		} else if(newInstitution.getId() == -2) {

			if(newInstitution.getAcronym() == null || newInstitution.getAcronym().isEmpty()){
				res = false;
				FacesUtils.addEmptyFieldMessage("Institution Acronym");
			}
			
			if(newInstitution.getName() == null || newInstitution.getName().isEmpty()){
				res = false;
				FacesUtils.addEmptyFieldMessage("Institution Name");
			}
			if(newInstitution.getNationality() == null || newInstitution.getNationality().isEmpty()){
				res = false;
				FacesUtils.addEmptyFieldMessage("Institution Nationality");
			}
		}
		
		return res;
	}
	
	public SignupVo getSignupData() {
		return signupData;
	}

	public void setSignupData(SignupVo signupData) {
		this.signupData = signupData;
	}

	public InstitutionServiceProvider getInstitutionServiceProvider() {
		return institutionServiceProvider;
	}

	public void setInstitutionServiceProvider(
			InstitutionServiceProvider institutionServiceProvider) {
		this.institutionServiceProvider = institutionServiceProvider;
	}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}

	public Institution getNewInstitution() {
		return newInstitution;
	}

	public void setNewInstitution(Institution newInstitution) {
		this.newInstitution = newInstitution;
	}

}
