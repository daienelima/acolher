package com.acolher.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Endereco;

@Repository 
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

}
