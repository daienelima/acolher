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
	public void desativarConta(Instituicao instituicao) {
		log.debug("desativar Instituicao: {} " , instituicao);
		
		this.instituicaoRepository.save(instituicao);
	}

	public Instituicao getByCnpj(String cnpj){
		log.debug("Instituicao getByCnpj: {} " , cnpj);
		
		return this.instituicaoRepository.findByCnpj(cnpj);
		
	}
	
	public Instituicao getByEmail(String email){
		log.debug("Instituicao getByEmail: {} " , email);
		
		return this.instituicaoRepository.findByEmail(email);
		
	}

	@Override
	public Optional<Instituicao> findByCodigoAndSenha(Integer codigo, String senha) {
		log.debug("String findByCodigoAndSenha: {} " , codigo + "; " + senha);
		
		return this.instituicaoRepository.findByCodigoAndSenha(codigo,senha);
	}
	
	@Override
	public Optional<Instituicao> findByEmailAndSenha(String email, String senha) {
		log.debug("String findByEmailAndSenha: {} " , email + "; " + senha);
		
		return this.instituicaoRepository.findByEmailAndSenha(email, senha);
	}
}
