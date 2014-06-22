package br.usp.ime.escience.expressmatch.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.SymbolClass;

@Transactional
public interface SymbolClassRepository extends JpaRepository<SymbolClass, Long> {

	SymbolClass findByLabel(String label);
	
}
