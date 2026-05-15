package com.acolher.api.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

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
import org.springframework.web.bind.annotation.RestController;

import com.acolher.api.domain.Consulta;
import com.acolher.api.domain.Status;
import com.acolher.api.domain.Usuario;
import com.acolher.api.domain.Instituicao;
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

	@CrossOrigin
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
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        gc.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");

        if(consulta.getData().equals(sdf.format(gc.getTime()))){
            SimpleDateFormat sdfH = new SimpleDateFormat("HH:mm");
            sdfH.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
            int horaAtual = Integer.valueOf(sdfH.format(gc.getTime()).replace(":", ""));
            int horaConsulta = Integer.valueOf(consulta.getHora().replace(":", ""));
            if (horaConsulta <= horaAtual){
            	return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível agendar consultas para a data e hora atual ou retroativa");
            }
        }
        
		List<Consulta> consultas = new ArrayList<Consulta>();
		
		if(consulta.getInstituicao() == null) {
			consultas = this.consultaService.findConsultasFuturasByCodigoProfissional(consulta.getProfissional().getCodigo(), consulta.getData(), consulta.getHora());
		}else {
			consultas = this.consultaService.findConsultasFuturasByCodigoInstituicao(consulta.getInstituicao().getCodigo(), consulta.getData(), consulta.getHora());			
		}
		
		if (consultas.size() > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível agendar consultas em intervalo inferior a 1h de consultas já agendadas");
		}
		
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
	
	@PutMapping(path = "/confirmarRealizacao")
	public ResponseEntity<?> confirmarRealizacaoConsulta(@RequestBody Consulta consulta) {
		log.debug("Confirmar Realização da Consulta: {}", consulta);
		
		if (this.consultaService.getById(consulta.getCodigo()) == null
				|| this.usuarioService.getById(consulta.getPaciente().getCodigo()) == null) {
			return ResponseEntity.notFound().build();
		}
		consulta.setStatusConsulta(Status.REALIZADA);
		this.consultaService.confirmarRealizacaoConsulta(consulta);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(path = "/disponiveis/{codigo}")
	public ResponseEntity<?> getAllDisponivelByPaciente(@PathVariable(name = "codigo") Integer id){
		log.debug("Consultas por ID - ", id);
		
		Optional<Usuario> usuario = usuarioService.getById(id);
		Consulta consulta = this.consultaService.findConsultasByPaciente(usuario);
		
		return consulta != null ? ResponseEntity.ok().body(consulta) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/paciente/{codigo}")
	public ResponseEntity<?> getByPaciente(@PathVariable(name = "codigo") Integer codigo) {
		log.debug("Request List Consulta por Paciente");
		
		Usuario u = new Usuario();
		u.setCodigo(codigo);
		List<Consulta> consultas = this.consultaService.findConsultasPorPaciente(u);
		
		return ResponseEntity.ok().body(consultas);
	}
	
	@GetMapping("/voluntario/{codigo}")
	public ResponseEntity<?> getByVoluntario(@PathVariable(name = "codigo") Integer codigo) {
		log.debug("Request List Consulta por Voluntario");
		
		Usuario u = new Usuario();
		u.setCodigo(codigo);
		List<Consulta> consultas = this.consultaService.findConsultasPorVoluntario(u);
		
		return ResponseEntity.ok().body(consultas);
	}
	
	@GetMapping("/instituicao/{codigo}")
	public ResponseEntity<?> getByInstituicao(@PathVariable(name = "codigo") Integer codigo) {
		log.debug("Request List Consulta por Instituicao");
		
		Instituicao i = new Instituicao();
		i.setCodigo(codigo);
		List<Consulta> consultas = this.consultaService.findConsultasPorInstituicao(i);
		
		return ResponseEntity.ok().body(consultas);
	}
	
	@PutMapping(path = "/cancelarpaciente")
	public ResponseEntity<?> cancelarConsultaPaciente(@RequestBody Consulta consulta) {
		log.debug("Request to cancelar Consulta by id : {}", consulta);
		
		if (this.consultaService.getById(consulta.getCodigo()) == null) {
			return ResponseEntity.notFound().build();
		}
		consulta.setStatusConsulta(Status.DISPONIVEL);
		consulta.setPaciente(null);
		this.consultaService.cancelarConsultaPaciente(consulta);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?>delete(@PathVariable(name="codigo") Integer codigo){
		log.debug("Request to delete by id : {}", codigo);
		
		if (this.consultaService.getById(codigo) == null) {
			return ResponseEntity.notFound().build();
		}
		this.consultaService.delete(codigo);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/regiao/{regiao}")
	public ResponseEntity<?> getByRegiao(@PathVariable(name = "regiao") String regiao){
		log.debug("Get By regiao");
		
		List<Consulta> consultas = this.consultaService.findConsultasPorRegiao(regiao);
		return ResponseEntity.ok().body(consultas);
	}
	
}

