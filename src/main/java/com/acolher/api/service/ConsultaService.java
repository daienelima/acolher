package com.acolher.api.service;

import java.util.List;
import java.util.Optional;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Usuario;
import com.acolher.api.domain.Instituicao;

public interface ConsultaService {

	public Optional<Consulta> getById(Integer codigo);
	public List<Consulta> list();
	public Consulta save(Consulta consulta);
	public void cancelarConsulta(Consulta consulta);
	public void cancelarConsultaPaciente(Consulta consulta);
	public void confirmarConsulta(Consulta consulta);
	public void confirmarRealizacaoConsulta(Consulta consulta);
	public List<Consulta> findConsultasDisponiveis();
	public Consulta findConsultasByPaciente(Optional<Usuario> usuario);
	public List<Consulta> findConsultasPorPaciente(Usuario u);
	public List<Consulta> findConsultasPorVoluntario(Usuario u);
	public List<Consulta> findConsultasPorInstituicao(Instituicao i);
	public void delete(Integer codigo);
	public List<Consulta> findConsultasFuturasByCodigoProfissional(Integer codigoProfissional, String data, String hora);
	public List<Consulta> findConsultasFuturasByCodigoInstituicao(Integer codigoInstituicao, String data, String hora);
	public List<Consulta> findConsultasPorRegiao(String regiao);
}
