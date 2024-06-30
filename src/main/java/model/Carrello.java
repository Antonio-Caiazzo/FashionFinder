package model;

public class Carrello {
	private String utenteEmail;
	private int prodottoCodice;
	private int quantita;

	public Carrello() {
	}

	public Carrello(String utenteEmail, int prodottoCodice, int quantita) {
		this.utenteEmail = utenteEmail;
		this.prodottoCodice = prodottoCodice;
		this.quantita = quantita;
	}

	public String getUtenteEmail() {
		return utenteEmail;
	}

	public void setUtenteEmail(String utenteEmail) {
		this.utenteEmail = utenteEmail;
	}

	public int getProdottoCodice() {
		return prodottoCodice;
	}

	public void setProdottoCodice(int prodottoCodice) {
		this.prodottoCodice = prodottoCodice;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
}
