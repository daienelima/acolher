package com.acolher.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acolher.api.domain.Usuario;
import com.acolher.api.repository.UsuarioRepository;
import com.acolher.api.service.UsuarioService;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public Optional<Usuario> getById(Integer codigo) {
		log.debug("Usuario getById {} " , codigo);

		return this.usuarioRepository.findById(codigo);
	}

	@Override
	public List<Usuario> list() {
		log.debug("List Usuario");
		
		return this.usuarioRepository.findAll();
	}

	@Override
	public Usuario save(Usuario usuario) {
		log.debug("Save Usuario : {} " , usuario);
		
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public void desativarConta(Usuario usuario) {
		log.debug("desativar Conta {} " , usuario);
  
		this.usuarioRepository.save(usuario);
	}
  
	@Override
	public Usuario getByCpf(String cpf){
		log.debug("Usuario getByCPF: {} " , cpf);

		return this.usuarioRepository.findByCpf(cpf);
		
	}
	@Override
	public Usuario getByEmail(String email){
		log.debug("Usuario findByEmail: {} " , email);

		return this.usuarioRepository.getByEmail(email);
		
	}
	
	@Override
	public Optional<Usuario> findByCodigoAndPassword(Integer codigo, String password) {
		log.debug("String getBysenha: {} " , codigo + "; " + password);

		return this.usuarioRepository.findByCodigoAndPassword(codigo,password);
	}
	
	@Override
	public Optional<Usuario> findByEmailAndPassword(String email, String password) {
		log.debug("String findByEmailAndPassword: {} " , email + "; " + password);
		
		return this.usuarioRepository.findByEmailAndPassword(email,password);
	}

}

