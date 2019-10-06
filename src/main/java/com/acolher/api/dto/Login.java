package com.acolher.api.dto;

import java.io.Serializable;

public class Login implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7264078355197580562L;
	private String email;
	private String senha;
	
	public Login() {
	}
	
	public Login(String email, String senha) {
		this.email = email;
		this.senha = senha;
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

}
