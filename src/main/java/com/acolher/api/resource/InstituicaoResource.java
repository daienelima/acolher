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
import com.acolher.api.service.InstituicaoService;

@RestController
@RequestMapping("/api/instituicao")
public class InstituicaoResource {

	private final Logger log = LoggerFactory.getLogger(InstituicaoResource.class);
	private final InstituicaoService instituicaoService;
	
	public InstituicaoResource(InstituicaoService instituicaoService) {
		this.instituicaoService = instituicaoService;
	}
	
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
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?>delete(@PathVariable(name="codigo") Integer codigo){
		log.debug("Request to delete by id : {}", codigo);
		
		if (this.instituicaoService.getById(codigo) == null) {
			return ResponseEntity.notFound().build();
		}
		this.instituicaoService.delete(codigo);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(path = "/")
	public ResponseEntity<?>alterarSenha(@RequestBody String senhaAntiga, String novaSenha){
		log.debug("Request to update by senha"); 
		
		Instituicao instituicao = this.instituicaoService.getBysenha(senhaAntiga);
		
		if(senhaAntiga != instituicao.getSenha()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha atual está incorreta!");
		}
		if(novaSenha.length()<4 || novaSenha.isEmpty()){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A senha não pode ser menor que 4 caracteres!");
			
		}
		
		instituicao.setSenha(novaSenha);
		
		this.instituicaoService.save(instituicao);
		return ResponseEntity.ok().build();
	
	}
	
	
}
