package com.acolher.api.service;

import java.util.List;
import java.util.Optional;

import com.acolher.api.domain.Instituicao;

public interface InstituicaoService {
	
	public Optional<Instituicao> getById(Integer codigo);
	public List<Instituicao> list();
	public Instituicao save(Instituicao instituicao);
	public void delete (Integer codigo);
	public Instituicao getByCnpj(String cnpj);
	
}
