package model;

import java.util.Date;

public class Utente {

	private String email;
	private String username;
	private String psw;
	private String nome;
	private String cognome;
	private Boolean isAdmin;
	private Date dataDiNascita;

	public Utente() {
		email = "";
		username = "";
		psw = "";
		nome = "";
		cognome = "";
		isAdmin = false;
		dataDiNascita = null;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPsw() {
		return psw;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}
}
