package br.usp.ime.escience.expressmatch.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.usp.ime.escience.expressmatch.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
