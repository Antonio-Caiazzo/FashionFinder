package model;

import java.util.Date;

public class Carta {
    private String numeroCarta;
    private Date scadenzaCarta;
    private String nomeTitolare;
    private String cognomeTitolare;
    private String utenteEmail;

    public Carta() {
        this.numeroCarta = "";
        this.scadenzaCarta = null;
        this.nomeTitolare = "";
        this.cognomeTitolare = "";
        this.utenteEmail = "";
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public Date getScadenzaCarta() {
        return scadenzaCarta;
    }

    public void setScadenzaCarta(Date scadenzaCarta) {
        this.scadenzaCarta = scadenzaCarta;
    }

    public String getNomeTitolare() {
        return nomeTitolare;
    }

    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    public String getCognomeTitolare() {
        return cognomeTitolare;
    }

    public void setCognomeTitolare(String cognomeTitolare) {
        this.cognomeTitolare = cognomeTitolare;
    }

    public String getUtenteEmail() {
        return utenteEmail;
    }

    public void setUtenteEmail(String utenteEmail) {
        this.utenteEmail = utenteEmail;
    }
}
