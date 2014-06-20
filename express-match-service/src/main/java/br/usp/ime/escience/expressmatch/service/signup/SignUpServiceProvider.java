package br.usp.ime.escience.expressmatch.service.signup;

import java.io.Serializable;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.exception.ExpressMatchException;
import br.usp.ime.escience.expressmatch.model.Authorities;
import br.usp.ime.escience.expressmatch.model.Institution;
import br.usp.ime.escience.expressmatch.model.User;
import br.usp.ime.escience.expressmatch.model.UserInfo;
import br.usp.ime.escience.expressmatch.model.repository.InstitutionRepository;
import br.usp.ime.escience.expressmatch.model.repository.UserInfoRepository;
import br.usp.ime.escience.expressmatch.model.repository.UserRepository;

@Service
public class SignUpServiceProvider implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	public void register(SignupVo vo, Institution institution) throws ExpressMatchException{
		User user = new User();
		UserInfo userInfo = new UserInfo();
		
		if(institution.getId() == -2) {
			institution.setId(null);
			institutionRepository.save(institution);
		} else {
			institution = institutionRepository.findOne(institution.getId());
		}
		
		userInfo.setInstitution(institution);
		userInfo.setName(vo.getName());
		userInfo.setNationaity(vo.getNationality());
		userInfo.setUser(user);

		Authorities authorities = new Authorities(user, "ROLE_USER");
		
		user.setNick(vo.getNick());
		user.setEnabled(true);
		user.setPass(getPassowEncoder().encodePassword("admin", null));
		user.setUserInfo(userInfo);
		
		user.setAuthoritieses(new HashSet<Authorities>());
		user.getAuthoritieses().add(authorities);
		
		try{
			userRepository.save(user);
		} catch (Exception e) {
			throw new ExpressMatchException("There was errors while attempting to save new user", e);
		}
	}
	
	
	private PasswordEncoder getPassowEncoder(){
		return new ShaPasswordEncoder();
	}
	
	public boolean isValidNickName(String nick){
		return this.userInfoRepository.isThereAnyUserWithNick(nick) == 0;
	}
}
