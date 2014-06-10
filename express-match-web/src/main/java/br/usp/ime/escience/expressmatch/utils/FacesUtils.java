package br.usp.ime.escience.expressmatch.utils;

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
	
}
