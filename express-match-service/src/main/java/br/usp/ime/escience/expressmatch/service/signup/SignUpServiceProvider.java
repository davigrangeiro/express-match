package br.usp.ime.escience.expressmatch.service.signup;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchExpression;
import br.usp.ime.escience.expressmatch.model.Institution;
import br.usp.ime.escience.expressmatch.model.repository.UserInfoRepository;

@Service
public class SignUpServiceProvider implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	public void register(SignupVo vo, Institution institution) throws ExpressMatchExpression{
		
	}
	
	
	private PasswordEncoder getPassowEncoder(){
		return new ShaPasswordEncoder();
	}
	
	public boolean isValidNickName(String nick){
		return this.userInfoRepository.isThereAnyUserWithNick(nick) == 0;
	}
}
