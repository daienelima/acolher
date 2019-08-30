package com.acolher.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acolher.api.domain.Instituicao;
import com.acolher.api.repository.InstituicaoRepository;
import com.acolher.api.service.InstituicaoService;

@Service
@Transactional
public class InstituicaoServiceImpl implements InstituicaoService {

	private final Logger log = LoggerFactory.getLogger(InstituicaoServiceImpl.class);
	private final InstituicaoRepository instituicaoRepository;
		
	public InstituicaoServiceImpl(InstituicaoRepository instituicaoRepository) {
		this.instituicaoRepository = instituicaoRepository;
	}

	@Override
	public Optional<Instituicao> getById(Integer codigo) {
		log.debug("Instituicao getById: {} " , codigo);
		
		return this.instituicaoRepository.findById(codigo);
	}

	@Override
	public List<Instituicao> list() {
		log.debug("List Instituicao ");

		return this.instituicaoRepository.findAll();
	}

	@Override
	public Instituicao save(Instituicao instituicao) {
		log.debug("Save Instituicao: {} ", instituicao);
		
		return this.instituicaoRepository.save(instituicao);
	}

	@Override
	public void delete(Integer codigo) {
		log.debug("delete Instituicao: {} " , codigo);
		
		this.instituicaoRepository.deleteById(codigo);
	}

	public Instituicao getByCnpj(String cnpj){
		log.debug("Instituicao getByCnpj: {} " , cnpj);
		
		return this.instituicaoRepository.findByCnpj(cnpj);
		
	}
}
