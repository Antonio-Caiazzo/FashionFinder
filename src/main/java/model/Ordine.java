package model;

import java.util.Date;

public class Ordine {
	private int codice;
	private Date data;
	private double costoTotale;
	private String utenteEmail;

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getCostoTotale() {
		return costoTotale;
	}

	public void setCostoTotale(double costoTotale) {
		this.costoTotale = costoTotale;
	}

	public String getUtenteEmail() {
		return utenteEmail;
	}

	public void setUtenteEmail(String utenteEmail) {
		this.utenteEmail = utenteEmail;
	}
}
