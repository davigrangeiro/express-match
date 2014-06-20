package br.usp.ime.escience.expressmatch.utils;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FacesUtils {

	public static void addMessage(String header, String desc, Severity severity){
		FacesContext context = FacesContext.getCurrentInstance();
        
		if(null == severity){
			severity = FacesMessage.SEVERITY_INFO;
		}
		
		context.addMessage(null, new FacesMessage(severity, header, desc));
	}
	
	public static void addMessage(Exception e){
		FacesContext context = FacesContext.getCurrentInstance();
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
	}
	
	
	public static void addEmptyFieldMessage(String field){
		addMessage("Empty " + field + " Field", MessageFormat.format("The field {0} should not be empty", field), null);
	}
	
}
