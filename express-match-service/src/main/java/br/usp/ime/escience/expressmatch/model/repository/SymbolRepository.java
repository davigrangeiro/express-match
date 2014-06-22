package br.usp.ime.escience.expressmatch.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.Symbol;

@Transactional
public interface SymbolRepository extends JpaRepository<Symbol, Integer> {

	List<Symbol> findByLabelAndSymbolStatus(String label, Integer symbolStatus);

	@Query("select distinct(s.label) from Symbol s")
	List<String> getDistinctSymbolClasses();
	
}
