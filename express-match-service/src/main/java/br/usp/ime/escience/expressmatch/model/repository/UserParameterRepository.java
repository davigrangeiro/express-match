package br.usp.ime.escience.expressmatch.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.UserParameter;

@Transactional
public interface UserParameterRepository extends JpaRepository<UserParameter, Integer>{

	public UserParameter getUserParameterByRoot(boolean root);
	
}
