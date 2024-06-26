package model;

import java.util.Date;

public class Utente {

	private String email;
	private String username;
	private String psw;
	private String nome;
	private String cognome;
	private Boolean isAdmin;
	private Date data_di_nascita;

	public Utente() {
		email = "";
		username = "";
		psw = "";
		nome = "";
		cognome = "";
		isAdmin = false;
		data_di_nascita = null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getdata_di_nascita() {
		return data_di_nascita;
	}

	public void setdata_di_nascita(Date data_di_nascita) {
		this.data_di_nascita = data_di_nascita;
	}

}
