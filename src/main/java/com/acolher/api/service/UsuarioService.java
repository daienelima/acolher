package com.acolher.api.service;

import java.util.List;
import java.util.Optional;
import com.acolher.api.domain.Usuario;

public interface UsuarioService {
	
	public Optional<Usuario> getById(Integer codigo);
	public List<Usuario> list();
	public Usuario save(Usuario usuario);
	public void delete(Integer codigo);
	public Usuario getByCpf(String cpf);
	public Usuario getByEmail(String email);
	public Optional<Usuario> findByCodigoAndSenha(Integer codigo, String senha);
	
}
