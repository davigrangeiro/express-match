package br.usp.ime.escience.expressmatch.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.UserInfo;

@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	@Query("select i from UserInfo i, User u where u.nick = ?1 and i.user = u")
	public UserInfo getUserInfoByUserNick(String nick);
	
	@Query("select count(u) from UserInfo i, User u where u.nick = ?1 and i.user = u")
	public Integer isThereAnyUserWithNick(String nick);
	
}
