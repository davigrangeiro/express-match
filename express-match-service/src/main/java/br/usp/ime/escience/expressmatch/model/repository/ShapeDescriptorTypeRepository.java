package br.usp.ime.escience.expressmatch.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.usp.ime.escience.expressmatch.model.ShapeDescriptorType;

@Transactional
public interface ShapeDescriptorTypeRepository extends JpaRepository<ShapeDescriptorType, Integer> {

}
