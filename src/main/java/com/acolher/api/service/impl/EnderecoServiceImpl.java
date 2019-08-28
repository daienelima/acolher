package com.acolher.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acolher.api.domain.Endereco;
import com.acolher.api.repository.EnderecoRepository;
import com.acolher.api.service.EnderecoService;

@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService{

	private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);
	private final EnderecoRepository enderecoRepository;
	
	public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}
	
	@Override
	public Optional<Endereco> getById(Integer codigo) {
		log.debug("Endereco getById {} " , codigo);

		return this.enderecoRepository.findById(codigo);
	}

	@Override
	public List<Endereco> list() {
		log.debug("List Endereco");
		
		return this.enderecoRepository.findAll();
	}

	@Override
	public Endereco save(Endereco endereco) {
		log.debug("Save Endereco : {} " , endereco);
		
		return this.enderecoRepository.save(endereco);
	}

	@Override
	public void delete(Integer codigo) {
		log.debug("delete endereco {} " , codigo);
		
		this.enderecoRepository.deleteById(codigo);
	}

}
