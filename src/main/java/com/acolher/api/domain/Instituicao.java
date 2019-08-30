package com.acolher.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "instituicao")
public class Instituicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Integer codigo;
	
	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@NotNull
	@Size(min = 14, max = 14)
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "codigo_endereco", referencedColumnName = "codigo")
	private Endereco endereco;
	
	@NotNull
	@Size(min = 10, max = 11)
	@Column(name = "telefone", nullable = false)
	private String telefone;
	
	@NotNull
	@Size(max = 256)
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotNull
	@Size(min = 4)
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@NotNull
	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
	public Instituicao() {
	}
	
	public Instituicao(Integer codigo, String nome, String cnpj, Endereco endereco, String telefone, String email,
			String senha, boolean ativo) {
		this.codigo = codigo;
		this.nome = nome;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
		this.ativo = ativo;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
