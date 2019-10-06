package com.acolher.api.service;

import java.util.List;
import java.util.Optional;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Usuario;

public interface ConsultaService {

	public Optional<Consulta> getById(Integer codigo);
	public List<Consulta> list();
	public Consulta save(Consulta consulta);
	public void cancelarConsulta(Consulta consulta);
	public void cancelarConsultaPaciente(Consulta consulta);
	public void confirmarConsulta(Consulta consulta);
	public List<Consulta> findConsultasDisponiveis();
	public Consulta findConsultasByPaciente(Optional<Usuario> usuario);
	public List<Consulta> findConsultasPorPaciente(Usuario u);
	public List<Consulta> findConsultasPorVoluntario(Usuario u);
}
