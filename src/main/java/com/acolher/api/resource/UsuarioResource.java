package com.acolher.api.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import com.acolher.api.dto.AlterarSenha;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acolher.api.domain.Usuario;
import com.acolher.api.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);
	private final UsuarioService usuarioService;
	
	public UsuarioResource(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping()
	public ResponseEntity<?> get(){
		log.debug("Request List Usuarios");
		List<Usuario> usuarios = this.usuarioService.list();
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?>getById(@PathVariable(name="codigo") Integer codigo){
		log.debug("Requst Usuario by id: {}", codigo);
	
		Optional<Usuario> usuario = this.usuarioService.getById(codigo);
		
		return  usuario!= null ?  ResponseEntity.ok().body(usuario) : ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario) throws URISyntaxException{
		log.debug("Request to save Usuario : {}", usuario);
		
		Usuario usuarioExistente = this.usuarioService.getByCpf(usuario.getCpf());

		if(usuarioExistente != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("CPF já cadastrado");
		}

		Usuario usuarioSalvo = this.usuarioService.save(usuario);

		return ResponseEntity.created(new URI("/usuario/" + usuario.getCodigo())).body(usuarioSalvo);
	}

	
	@PutMapping()
	public ResponseEntity<?>update(@RequestBody Usuario usuario){
		log.debug("Request to update Usuario: {}", usuario);
		
		if(this.usuarioService.getById(usuario.getCodigo()) == null){
			return ResponseEntity.notFound().build();
		} 
		this.usuarioService.save(usuario);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?>delete(@PathVariable(name="codigo") Integer codigo){
		log.debug("Request to delete by id : {}", codigo);
		
		if (this.usuarioService.getById(codigo) == null) {
			return ResponseEntity.notFound().build();
		}
		this.usuarioService.delete(codigo);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(path = "/senha")
	public ResponseEntity<?>alterarSenha(@RequestBody AlterarSenha alterarSenha){
		log.debug("Request to update by senha"); 

		Optional<Usuario> usuario = this.usuarioService.findByCodigoAndSenha(alterarSenha.getCodigo(), alterarSenha.getSenhaAntiga());

		if(usuario.isPresent()) {
			if(alterarSenha.getNovaSenha().length()<4 || alterarSenha.getNovaSenha().isEmpty() || alterarSenha.getNovaSenha().contains(" ")){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A senha não pode ser menor que 4 caracteres ou conter espaço.");
			}
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha atual está incorreta!");
		}

		usuario.get().setPassword(alterarSenha.getNovaSenha());

		this.usuarioService.save(usuario.get());
		return ResponseEntity.ok().build();

	}
	
	
}

