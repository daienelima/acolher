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
}
