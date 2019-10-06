package com.acolher.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Instituicao;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

	Instituicao findByCnpj(String cnpj); 
	Instituicao findByEmail(String email); 
	Optional<Instituicao> findByCodigoAndSenha(Integer codigo, String senha);
	Optional<Instituicao> findByEmailAndSenha(String email, String senha);
}
