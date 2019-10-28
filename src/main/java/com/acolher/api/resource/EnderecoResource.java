package com.acolher.api.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acolher.api.domain.Endereco;
import com.acolher.api.service.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoResource {
	
	private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);
	private final EnderecoService enderecoService;
	
	public EnderecoResource(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}
	
	@GetMapping()
	public ResponseEntity<?> get(){
		log.debug("Request List Enderecos");
		
		List<Endereco> enderecos = this.enderecoService.list();
		
		return ResponseEntity.ok().body(enderecos);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?>getById(@PathVariable(name="codigo") Integer codigo){
		log.debug("Requst Endereco by id: {}", codigo);
	
		Optional<Endereco> endereco = this.enderecoService.getById(codigo);
		
		return  endereco!= null ?  ResponseEntity.ok().body(endereco) : ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody Endereco endereco) throws URISyntaxException{
		log.debug("Request to save Endereco : {}", endereco);
		
		Endereco enderecoSalvo = this.enderecoService.save(endereco);
		
		return ResponseEntity.created(new URI("/endereco/" + endereco.getCodigo())).body(enderecoSalvo);
	}
	
	@PutMapping()
	public ResponseEntity<?>update(@RequestBody Endereco endereco){
		log.debug("Request to update Endereco: {}", endereco);
		
		if(this.enderecoService.getById(endereco.getCodigo()) == null){
			return ResponseEntity.notFound().build();
		} 
		this.enderecoService.save(endereco);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?>delete(@PathVariable(name="codigo") Integer codigo){
		log.debug("Request to delete by id : {}", codigo);
		
		try {
			if (this.enderecoService.getById(codigo) == null) {
				return ResponseEntity.notFound().build();
			}
			this.enderecoService.delete(codigo);
		} catch (InvalidParameterException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "/getByParameters")
	public ResponseEntity<?>getByParameter(@RequestBody Endereco endereco){
		log.debug("Request to get Endereco by parameters : {}", endereco);
		Endereco address = this.enderecoService.getAddressByParameters(endereco);
		return ResponseEntity.ok().body(address);
	}
	
}
