package com.acolher.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Usuario;
import com.acolher.api.repository.ConsultaRepository;
import com.acolher.api.service.ConsultaService;

@Service
@Transactional
public class ConsultaServiceImpl implements ConsultaService{

	private final Logger log = LoggerFactory.getLogger(ConsultaServiceImpl.class);
	private final ConsultaRepository consultaRepository;
	
	public ConsultaServiceImpl(ConsultaRepository consultaRepository) {
		this.consultaRepository = consultaRepository;
	}
	
	@Override
	public Optional<Consulta> getById(Integer codigo) {
		log.debug("Consulta getById {} " , codigo);
		
		return this.consultaRepository.findById(codigo);
	}

	@Override
	public List<Consulta> list() {
		log.debug("List Consultas");
		
		return this.consultaRepository.findAll();
	}

	@Override
	public Consulta save(Consulta consulta) {
		log.debug("Save Consulta : {} " , consulta);
	
		return this.consultaRepository.save(consulta);
	}

	@Override
	public void cancelarConsulta(Consulta consulta) {
		log.debug("cancelar Consulta {} " , consulta);
		
		this.consultaRepository.save(consulta);
	}
	
	@Override
	public void cancelarConsultaPaciente(Consulta consulta) {
		log.debug("cancelar Consulta {} " , consulta);
		
		this.consultaRepository.save(consulta);
	}

	@Override
	public void confirmarConsulta(Consulta consulta) {
		log.debug("confirmar Consulta {} " , consulta);
		
		this.consultaRepository.save(consulta);
	}

	@Override
	public List<Consulta> findConsultasDisponiveis() {
		log.debug("List Consultas Disponiveis");
		
		return this.consultaRepository.findAllConcultasDisponiveis();
	}

	@Override
	public Consulta findConsultasByPaciente(Optional<Usuario> paciente) {
		log.debug("List Consultas por Paciente");
		
		return this.consultaRepository.findConsultaDisponivelByPaciente(paciente);
	}
	
	@Override
	public List<Consulta> findConsultasPorPaciente(Usuario u) {
		log.debug("List Consultas Disponiveis");
		
		return this.consultaRepository.findAllConcultasPorPaciente(u);
	}
	@Override
	public List<Consulta> findConsultasPorVoluntario(Usuario u) {
		log.debug("List Consultas Disponiveis");
		
		return this.consultaRepository.findAllConcultasPorVoluntario(u);
	}

	@Override
	public void delete(Integer codigo) {
		log.debug("Deletar Consultas");
		
		this.consultaRepository.deleteById(codigo);
	}
}
