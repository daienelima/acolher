package com.acolher.api.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acolher.api.domain.Instituicao;
import com.acolher.api.dto.AlterarSenha;
import com.acolher.api.dto.Login;
import com.acolher.api.service.InstituicaoService;

@RestController
@RequestMapping("/api/instituicao")
public class InstituicaoResource {

	private final Logger log = LoggerFactory.getLogger(InstituicaoResource.class);
	private final InstituicaoService instituicaoService;
	
	public InstituicaoResource(InstituicaoService instituicaoService) {
		this.instituicaoService = instituicaoService;
	}
	
	@CrossOrigin
	@GetMapping()
	public ResponseEntity<?> get(){
		log.debug("Request List Instituicoes");
		
		List<Instituicao> instituicoes = this.instituicaoService.list();
		
		return ResponseEntity.ok().body(instituicoes);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?>getById(@PathVariable(name="codigo") Integer codigo){
		log.debug("Requst Instituicao by id: {}", codigo);
	
		Optional<Instituicao> instituicao = this.instituicaoService.getById(codigo);
		
		return  instituicao!= null ?  ResponseEntity.ok().body(instituicao) : ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody Instituicao instituicao) throws URISyntaxException{
		log.debug("Request to save Instituicao : {}", instituicao);
		
		Instituicao instituicaoExistente = this.instituicaoService.getByCnpj(instituicao.getCnpj());
		
		if(instituicaoExistente != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("CNPJ já cadastrado");
		}

		instituicaoExistente = this.instituicaoService.getByEmail(instituicao.getEmail());
		
		if(instituicaoExistente != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("E-mail já cadastrado");
		}
		
		Instituicao instituicaoSalva = this.instituicaoService.save(instituicao);
		
		return ResponseEntity.created(new URI("/instituicao/" + instituicao.getCodigo())).body(instituicaoSalva);
	}
	
	@PutMapping()
	public ResponseEntity<?>update(@RequestBody Instituicao instituicao){
		log.debug("Request to update Instituicao: {}", instituicao);
		
		if(this.instituicaoService.getById(instituicao.getCodigo()) == null){
			return ResponseEntity.notFound().build();
		} 
		this.instituicaoService.save(instituicao);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/desativar/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?>desativar(@PathVariable(name="codigo") Integer codigo){
		log.debug("Request to desativar  : {}", codigo);
		
		Optional<Instituicao> instituicao = this.instituicaoService.getById(codigo);
		if (!instituicao.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		instituicao.get().setAtivo(false);
		this.instituicaoService.desativarConta(instituicao.get());
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(path = "/senha")
	public ResponseEntity<?>alterarSenha(@RequestBody AlterarSenha alterarSenha){
		log.debug("Request to update by senha"); 
		
		Optional<Instituicao> instituicao = this.instituicaoService.findByCodigoAndSenha(alterarSenha.getCodigo(), alterarSenha.getSenhaAntiga());
		
		if(instituicao.isPresent()) {
			
			if(alterarSenha.getNovaSenha().length()<4 || alterarSenha.getNovaSenha().isEmpty() || alterarSenha.getNovaSenha().contains(" ")){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A senha não pode ser menor que 4 caracteres ou conter espaços.");
			}
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha atual está incorreta!");
		}
		
		instituicao.get().setSenha(alterarSenha.getNovaSenha());
		
		this.instituicaoService.save(instituicao.get());
		return ResponseEntity.ok().build();
	
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?>login(@RequestBody Login login){
		log.debug("Request to login");
		
		Optional<Instituicao> instituicao = this.instituicaoService.findByEmailAndSenha(login.getEmail(), login.getSenha());
		
		return instituicao.isPresent() ? ResponseEntity.ok().body(instituicao) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login inválido");
		
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?>delete(@PathVariable(name="codigo") Integer codigo){
		log.debug("Request to delete by id : {}", codigo);
		
		try {
			if (this.instituicaoService.getById(codigo) == null) {
				return ResponseEntity.notFound().build();
			}
			this.instituicaoService.delete(codigo);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
