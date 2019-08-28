package com.acolher.api.service;

import java.util.List;
import java.util.Optional;

import com.acolher.api.domain.Endereco;

public interface EnderecoService {
	
	public Optional<Endereco> getById(Integer codigo);
	public List<Endereco> list();
	public Endereco save(Endereco endereco);
	public void delete(Integer codigo);
	
}
