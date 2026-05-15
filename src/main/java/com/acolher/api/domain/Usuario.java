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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -4473912353020291828L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Integer codigo;

	@NotEmpty(message = "Nome Completo é obrigatório")
	@Column(name = "nome_completo", nullable = false)
	private String nome_completo;

	@NotEmpty(message = "Data de nascimento é obrigatório")
	@Column(name = "data_nascimento", nullable = false)
	@NotNull
	private String data_nascimento;

	@NotEmpty(message = "Email é obrigatório")
	@NotNull
	@Email
	@Column(name = "email", nullable = false)
	private String email;

	@NotEmpty(message = "Senha é obrigatório")
	@Size(min = 4)
	@Column(name = "password", nullable = false)
	private String password;

	@NotNull
	@OneToOne
	@JoinColumn(name = "codigo_endereco", referencedColumnName = "codigo")
	private Endereco endereco;

	@NotEmpty(message = "Telefone é obrigatório")
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

	public Usuario() {
	}

	public Usuario(Integer codigo, String nome_completo, String data_nascimento, String email, Endereco endereco,
			String telefone, String password, String cpf, String crm_crp, Boolean ativo) {
		this.codigo = codigo;
		this.nome_completo = nome_completo;
		this.data_nascimento = data_nascimento;
		this.email = email;
		this.password = password;
		this.endereco = endereco;
		this.telefone = telefone;
		this.cpf = cpf;
		this.crm_crp = crm_crp;
		this.ativo = ativo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome_completo() {
		return nome_completo;
	}

	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}

	public String getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
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

}
