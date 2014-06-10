package br.usp.ime.escience.expressmatch.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.usp.ime.escience.expressmatch.model.Symbol;


public interface SymbolRepository extends JpaRepository<Symbol, Integer> {

	List<Symbol> findByLabel(String label);

	@Query("select distinct(s.label) from Symbol s")
	List<String> getDistinctSymbolClasses();
	
}
