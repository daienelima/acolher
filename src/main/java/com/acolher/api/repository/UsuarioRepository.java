package com.acolher.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acolher.api.domain.Usuario;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByCpf(String cpf);
	Usuario getByEmail(String email);
	Optional<Usuario> findByCodigoAndPassword(Integer codigo, String senha);
	Optional<Usuario> findByEmailAndPassword(String email, String senha);
	@Query(value = "select * from usuario u where u.codigo_endereco = ?1" , nativeQuery = true)
	List<Usuario> findAllEnderecoByCodigo(Integer codigo_endereco);
	
}

