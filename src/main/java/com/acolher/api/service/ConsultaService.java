package com.acolher.api.service;

import java.util.List;
import java.util.Optional;

import com.acolher.api.domain.Consulta;

public interface ConsultaService {

	public Optional<Consulta> getById(Integer codigo);
	public List<Consulta> list();
	public Consulta save(Consulta consulta);
	public void cancelarConsulta(Consulta consulta);
	public void confirmarConsulta(Consulta consulta);
	public List<Consulta> findConsultasDisponiveis();
}
