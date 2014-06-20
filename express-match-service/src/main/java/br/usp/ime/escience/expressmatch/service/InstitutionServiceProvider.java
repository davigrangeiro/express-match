package br.usp.ime.escience.expressmatch.service;

import java.text.MessageFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.model.Institution;
import br.usp.ime.escience.expressmatch.model.repository.InstitutionRepository;

@Service
@Transactional
public class InstitutionServiceProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InstitutionServiceProvider.class);

	@Autowired
	private InstitutionRepository institutionRepository;
	
	public List<Institution> getAllInstitutions(){
		List<Institution> res = institutionRepository.findAll();
		
		if (null != res) {
			LOGGER.info(MessageFormat.format("Returned {0} isntitutions", res.size()));
		}
		return res;
	}
}
