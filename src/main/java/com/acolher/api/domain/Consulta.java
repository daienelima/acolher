package com.acolher.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "consulta")
public class Consulta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1981397116411824859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Integer codigo;
	
	@NotNull
	@Column(name = "data", nullable = false)
	private LocalDateTime data;
	
	@NotNull
	@Column(name = "hora", nullable = false)
	private String hora;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "codigo_endereco", referencedColumnName = "codigo")
	private Endereco endereco;
	
	@ManyToOne
	@JoinColumn(name = "codigo_profissional")
	private Usuario profissional;
	
	@ManyToOne
	@JoinColumn(name = "codigo_instituicao")
	private Instituicao instituicao;
	
	@OneToOne
	@JoinColumn(name = "codigo_paciente")
	private Usuario paciente;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status statusConsulta;
	
	public Consulta() {}
	
	public Consulta(Integer codigo, @NotNull LocalDateTime data, @NotNull String hora, @NotNull Endereco endereco,
			Usuario profissional, Instituicao instituicao, Usuario paciente, @NotNull Status statusConsulta) {
		this.codigo = codigo;
		this.data = data;
		this.hora = hora;
		this.endereco = endereco;
		this.profissional = profissional;
		this.instituicao = instituicao;
		this.paciente = paciente;
		this.statusConsulta = statusConsulta;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Usuario getProfissional() {
		return profissional;
	}

	public void setProfissional(Usuario profissional) {
		this.profissional = profissional;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Status getStatusConsulta() {
		return statusConsulta;
	}

	public void setStatusConsulta(Status statusConsulta) {
		this.statusConsulta = statusConsulta;
	}

}
