package com.acolher.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "endereco")
public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2783856673178874342L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Integer codigo;

	@NotNull
	@Column(name = "cep", nullable = false)
	private String CEP;

	@NotNull
	@Column(name = "logradouro", nullable = false)
	private String logradouro;

	@NotNull
	@Column(name = "cidade", nullable = false)
	private String cidade;

	@NotNull
	@Size(min = 2, max = 2)
	@Column(name = "uf", nullable = false)
	private String UF;

	@NotNull
	@Column(name = "bairro", nullable = false)
	private String bairro;

	@NotNull
	@Column(name = "numero", nullable = false)
	private String numero;

	@Column(name = "longitude", nullable = false)
	private String longitude;

	@Column(name = "latitude", nullable = false)
	private String latitude;
	
	@OneToOne(mappedBy = "endereco")
	private Usuario usuario;

	public Endereco() {
	}

	public Endereco(Integer codigo, String CEP, String logradouro, String cidade, String UF, String bairro,
			String numero, String longitude, String latitude) {
		this.codigo = codigo;
		this.CEP = CEP;
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.UF = UF;
		this.bairro = bairro;
		this.numero = numero;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
