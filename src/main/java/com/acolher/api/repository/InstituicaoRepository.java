package com.acolher.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Instituicao;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Integer> {

	Instituicao findByCnpj(String cnpj);
	Instituicao findBySenha(String senha);
}
