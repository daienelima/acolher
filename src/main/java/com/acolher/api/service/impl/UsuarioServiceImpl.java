package com.acolher.api.service.impl;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Usuario;
import com.acolher.api.repository.ConsultaRepository;
import com.acolher.api.repository.UsuarioRepository;
import com.acolher.api.service.UsuarioService;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	private final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	private final UsuarioRepository usuarioRepository;
	private final ConsultaRepository consultaRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ConsultaRepository consultaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.consultaRepository = consultaRepository;
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

	@Override
	public void delete(Integer codigo) {
		log.debug("Usuario Delete byId");

		List<Consulta> consultasProfissional = this.consultaRepository.findAllConsultaByCodigoProficional(codigo);
		List<Consulta> consultasPaciente = this.consultaRepository.findAllConsultaByCodigoPaciente(codigo);
		Optional<Usuario> usuario = this.usuarioRepository.findById(codigo);

		if(usuario.isPresent()) {
			if(consultasProfissional.size() > 0) {
				throw new InvalidParameterException("Profissional não pode ser deletado pois existe agendamento de consultas ativo");
			}

			if(consultasPaciente.size() > 0) {
				throw new InvalidParameterException("Paciente não pode ser deletado pois existe agendamento de consulta ativo");
			}
			
		}
		
		this.usuarioRepository.deleteById(codigo);
	}

}

