package model;

public class Contiene {
    private int ordineCodice;
    private int prodottoCodice;
    private int quantita;

    public Contiene() {
        this.ordineCodice = 0;
        this.prodottoCodice = 0;
        this.quantita = 0;
    }

    public int getOrdineCodice() {
        return ordineCodice;
    }

    public void setOrdineCodice(int ordineCodice) {
        this.ordineCodice = ordineCodice;
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
