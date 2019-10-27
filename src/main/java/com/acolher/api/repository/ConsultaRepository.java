package com.acolher.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Usuario;
import com.acolher.api.domain.Instituicao;


@Repository 
public interface ConsultaRepository extends JpaRepository<Consulta, Integer>{
	
	@Query("SELECT c FROM consulta c WHERE c.statusConsulta = 'DISPONIVEL'")
	List<Consulta> findAllConcultasDisponiveis();
	
	@Query("SELECT c FROM consulta c WHERE c.paciente = ?1 and c.statusConsulta = 'CONFIRMADA'")
	Consulta findConsultaDisponivelByPaciente(Optional<Usuario> paciente);
	
	@Query("SELECT c FROM consulta c WHERE c.paciente = ?1")
	List<Consulta> findAllConcultasPorPaciente(Usuario u);
	
	@Query("SELECT c FROM consulta c WHERE c.profissional = ?1")
	List<Consulta> findAllConcultasPorVoluntario(Usuario u);
	
	@Query("SELECT c FROM consulta c WHERE c.instituicao = ?1")
	List<Consulta> findAllConsultasPorInstituicao(Instituicao i);
	
	@Query(value = "select * from consulta c where (status = 'DISPONIVEL' or status = 'CONFIRMADA') and c.codigo_paciente = ?1", nativeQuery = true)
	List<Consulta> findAllConsultaByCodigoPaciente(Integer codigo_paciente);
	
	@Query(value = "select * from consulta c where (status = 'DISPONIVEL' or status = 'CONFIRMADA') and c.codigo_profissional = ?1", nativeQuery = true)
	List<Consulta> findAllConsultaByCodigoProficional(Integer codigo_profissional);
	
	@Query(value = "select * from consulta c where (status = 'DISPONIVEL' or status = 'CONFIRMADA') and c.codigo_instituicao = ?1", nativeQuery = true)
	List<Consulta> findAllConsultaByCodigoInstituicao(Integer codigo_instituicao);
	
	@Query(value = "SELECT * FROM consulta WHERE codigo_profissional = ?1 AND status IN ('DISPONIVEL', 'CONFIRMADA') " + 
			"AND STR_TO_DATE(data,'%d/%m/%Y') >= STR_TO_DATE(?2,'%d/%m/%Y')" +
			"AND STR_TO_DATE((SELECT replace(hora,':','')), '%H %i') " +
			"BETWEEN SUBTIME(STR_TO_DATE((SELECT replace(?3,':','')), '%H %i'), '01:00:00') AND " + 
			"ADDTIME(STR_TO_DATE((SELECT replace(?3,':','')), '%H %i'), '01:00:00')", nativeQuery = true)
	List<Consulta> findConsultasFuturasByCodigoProfissional(Integer codigo_profissional, String data, String hora);
	
	@Query(value = "SELECT * FROM consulta WHERE codigo_instituicao = ?1 AND status IN ('DISPONIVEL', 'CONFIRMADA') " + 
			"AND STR_TO_DATE(data,'%d/%m/%Y') >= STR_TO_DATE(?2,'%d/%m/%Y') " + 
			"AND STR_TO_DATE((SELECT replace(hora,':','')), '%H %i') " + 
			"BETWEEN SUBTIME(STR_TO_DATE((SELECT replace(?3,':','')), '%H %i'), '01:00:00') AND " + 
			"ADDTIME(STR_TO_DATE((SELECT replace(?3,':','')), '%H %i'), '01:00:00')", nativeQuery = true)
	List<Consulta> findConsultasFuturasByCodigoInstituicao(Integer codigo_instituicao, String data, String hora);
}
