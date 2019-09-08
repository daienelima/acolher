package com.acolher.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.acolher.api.domain.Usuario;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByCpf(String cpf);
	Usuario getByEmail(String email);
	Optional<Usuario> findByCodigoAndPassword(Integer codigo, String senha);

}

