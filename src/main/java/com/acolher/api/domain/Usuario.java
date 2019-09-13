package com.acolher.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "usuario")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4473912353020291828L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Integer codigo;
	
	@NotNull
	@Column(name = "nomeCompleto", nullable = false)
	private String nomeCompleto;

	@NotNull
	@Column(name = "dataNascimento", nullable = false)
	private String dtNascimento;

	@NotNull
	@Email
	@Column(name = "email", nullable = false)
	private String email;

	@NotNull
	@Size(min = 4)
	@Column(name = "password", nullable = false)
	private String password;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "codigo_endereco", referencedColumnName = "codigo")
    private Endereco endereco;

	@NotNull
	@Size(min = 10, max = 11)
	@Column(name = "telefone", nullable = false)
	private String telefone;
	
	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "cpf", nullable = false)
	private String cpf;
	
	@NotNull
	@Column(name = "CRM_CRP")
	private String crm_crp;

	@NotNull
	@Column(name = "ativo", nullable = false)
	private Boolean ativo;
	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCrm_crp() {
		return crm_crp;
	}

	public void setCrm_crp(String crm_crp) {
		this.crm_crp = crm_crp;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Usuario(Integer codigo, @NotNull String nomeCompleto, @NotNull String dtNascimento, @NotNull String email,
			@NotNull String password, Endereco endereco, @NotNull String telefone, @NotNull String cpf,
			@NotNull String crm_crp, @NotNull Boolean ativo) {
		super();
		this.codigo = codigo;
		this.nomeCompleto = nomeCompleto;
		this.dtNascimento = dtNascimento;
		this.email = email;
		this.password = password;
		this.endereco = endereco;
		this.telefone = telefone;
		this.cpf = cpf;
		this.crm_crp = crm_crp;
		this.ativo = ativo;
	}

	public Usuario() {
		super();	
	}
}
