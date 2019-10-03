package com.acolher.api.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Status;
import com.acolher.api.domain.Usuario;
import com.acolher.api.service.ConsultaService;
import com.acolher.api.service.UsuarioService;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaResource {

	private final Logger log = LoggerFactory.getLogger(ConsultaResource.class);
	private final ConsultaService consultaService;
	private final UsuarioService usuarioService;

	public ConsultaResource(ConsultaService consultaService, UsuarioService usuarioService) {
		this.consultaService = consultaService;
		this.usuarioService = usuarioService;
	}

	@GetMapping()
	public ResponseEntity<?> get() {
		log.debug("Request List Consulta");
		List<Consulta> consultas = this.consultaService.list();
		return ResponseEntity.ok().body(consultas);
	}
	

	@GetMapping("/{codigo}")
	public ResponseEntity<?> getById(@PathVariable(name = "codigo") Integer codigo) {
		log.debug("Requst Consulta by id: {}", codigo);

		Optional<Consulta> consulta = this.consultaService.getById(codigo);

		return consulta != null ? ResponseEntity.ok().body(consulta) : ResponseEntity.notFound().build();
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody Consulta consulta) throws URISyntaxException {
		log.debug("Request to save Consulta : {}", consulta);

		consulta.setStatusConsulta(Status.DISPONIVEL);
		Consulta consultaSalva = this.consultaService.save(consulta);
		
		return ResponseEntity.created(new URI("/consulta/" + consulta.getCodigo())).body(consultaSalva);
	}

	@PutMapping()
	public ResponseEntity<?> update(@RequestBody Consulta consulta) {
		log.debug("Request to update Consulta: {}", consulta);

		if (this.consultaService.getById(consulta.getCodigo()) == null) {
			return ResponseEntity.notFound().build();
		}
		this.consultaService.save(consulta);
		return ResponseEntity.ok().build();
	}

	@PutMapping(path = "/cancelar")
	public ResponseEntity<?> delete(@RequestBody Consulta consulta) {
		log.debug("Request to cancelar Consulta by id : {}", consulta);
		
		if (this.consultaService.getById(consulta.getCodigo()) == null) {
			return ResponseEntity.notFound().build();
		}
		consulta.setStatusConsulta(Status.CANCELADA);
		this.consultaService.cancelarConsulta(consulta);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "/disponiveis")
	public ResponseEntity<?> getAllDisponivel() {
		log.debug("Request List Consulta Disponiveis");
		List<Consulta> consultas = this.consultaService.findConsultasDisponiveis();
		
		return ResponseEntity.ok().body(consultas);
	}

	@PutMapping(path = "/confirmar")
	public ResponseEntity<?> confirmarConsulta(@RequestBody Consulta consulta) {
		log.debug("Confirmar Consulta: {}", consulta);

		if (this.consultaService.getById(consulta.getCodigo()) == null
				|| this.usuarioService.getById(consulta.getPaciente().getCodigo()) == null) {
			return ResponseEntity.notFound().build();
		}
		consulta.setStatusConsulta(Status.CONFIRMADA);
		this.consultaService.confirmarConsulta(consulta);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "/disponiveis/{codigo}")
	public ResponseEntity<?> getAllDisponivelByPaciente(@PathVariable(name = "codigo") Integer id){
		log.debug("Consultas por ID - ", id);
		Optional<Usuario> usuario = usuarioService.getById(id);
		Consulta consulta = this.consultaService.findConsultasByPaciente(usuario);
		return consulta != null ? ResponseEntity.ok().body(consulta) : ResponseEntity.notFound().build();
	}
}
