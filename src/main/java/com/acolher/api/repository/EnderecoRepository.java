package com.acolher.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Endereco;

@Repository 
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
	
	@Query(value = "SELECT * FROM endereco e WHERE e.cep = ?1 "
			+ "AND e.uf = ?2 "
			+ "AND e.bairro = ?3 "
			+ "AND e.cidade = ?4 "
			+ "AND e.logradouro = ?5 "
			+ "AND e.numero = ?6 ", nativeQuery = true)
	Endereco findAddressByParameters(String cep, String uf, String bairro, String cidade, String logradouro, String numero);

}
