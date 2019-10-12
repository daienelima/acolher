package com.acolher.api.service.impl;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acolher.api.domain.Endereco;
import com.acolher.api.domain.Instituicao;
import com.acolher.api.domain.Usuario;
import com.acolher.api.repository.EnderecoRepository;
import com.acolher.api.repository.InstituicaoRepository;
import com.acolher.api.repository.UsuarioRepository;
import com.acolher.api.service.EnderecoService;

@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService{

	private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);
	private final EnderecoRepository enderecoRepository;
	private final UsuarioRepository usuarioRepository;
	private final InstituicaoRepository instituicaoRepository;
	
	public EnderecoServiceImpl(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository,
			InstituicaoRepository instituicaoRepository) {
		this.enderecoRepository = enderecoRepository;
		this.usuarioRepository = usuarioRepository;
		this.instituicaoRepository = instituicaoRepository;
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
		
		List<Usuario> usuarios = this.usuarioRepository.findAllEnderecoByCodigo(codigo);
		List<Instituicao> instituicoes = this.instituicaoRepository.findAllEnderecoByCodigo(codigo);
		if(usuarios.size() > 0 || instituicoes.size() > 0) {
			throw new InvalidParameterException("Endereço não pode ser deletado pois está assossiado ao usuario");
		}
		
		this.enderecoRepository.deleteById(codigo);
	}

}
