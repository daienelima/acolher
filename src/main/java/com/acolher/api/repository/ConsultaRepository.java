package com.acolher.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Usuario;

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
	
	@Query(value = "select * from consulta c where c.codigo_paciente = ?1", nativeQuery = true)
	List<Consulta> findAllConsultaByCodigoPaciente(Integer codigo_paciente);
	
	@Query(value = "select * from consulta c where c.codigo_profissional = ?1", nativeQuery = true)
	List<Consulta> findAllConsultaByCodigoProficional(Integer codigo_profissional);
	
	@Query(value = "select * from consulta c where c.codigo_instituicao = ?1", nativeQuery = true)
	List<Consulta> findAllConsultaByCodigoInstituicao(Integer codigo_instituicao);
}
