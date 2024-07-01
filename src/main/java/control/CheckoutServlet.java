package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarrelloDAO;
import model.Ordine;
import model.OrdineDAO;
import model.ContieneDAO;
import model.Utente;
import model.Prodotto;
import model.ProdottoDAO;
import model.Indirizzo;
import model.IndirizzoDAO;
import model.Carta;
import model.CartaDAO;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CheckoutServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utente userRegistrato = (Utente) request.getSession().getAttribute("userRegistrato");
        if (userRegistrato == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSession().getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello.jsp");
            return;
        }

        OrdineDAO ordineDAO = new OrdineDAO();
        ContieneDAO contieneDAO = new ContieneDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
        CartaDAO cartaDAO = new CartaDAO();

        int cap = Integer.parseInt(request.getParameter("cap"));
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String via = request.getParameter("via");
        int civico = Integer.parseInt(request.getParameter("civico"));

        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setCap(cap);
        indirizzo.setCitta(citta);
        indirizzo.setProvincia(provincia);
        indirizzo.setVia(via);
        indirizzo.setCivico(civico);
        indirizzo.setUtenteEmail(userRegistrato.getEmail());

        try {
            Indirizzo indirizzoEsistente = indirizzoDAO.doRetrieveByDetails(cap, citta, provincia, via, civico, userRegistrato.getEmail());
            if (indirizzoEsistente == null) {
                indirizzoDAO.doSave(indirizzo);
            } else {
                indirizzo.setId(indirizzoEsistente.getId());
                indirizzoDAO.updateIndirizzo(indirizzo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        String nomeTitolare = request.getParameter("titolare");
        String cognomeTitolare = request.getParameter("cognomeTitolare");
        String numeroCarta = request.getParameter("numeroCarta");
        java.sql.Date scadenza = java.sql.Date.valueOf(request.getParameter("scadenza"));

        Carta carta = new Carta();
        carta.setNumeroCarta(numeroCarta);
        carta.setScadenzaCarta(scadenza);
        carta.setNomeTitolare(nomeTitolare);
        carta.setCognomeTitolare(cognomeTitolare);
        carta.setUtenteEmail(userRegistrato.getEmail());

        try {
            Carta cartaEsistente = cartaDAO.doRetrieveByKey(numeroCarta);
            if (cartaEsistente == null) {
                cartaDAO.doSave(carta);
            } else {
                cartaDAO.updateCarta(carta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        double totalCost = 0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int codice = entry.getKey();
            int quantity = entry.getValue();
            Prodotto prodotto = null;
            try {
                prodotto = prodottoDAO.doRetrieveByKey(codice);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (prodotto != null) {
                totalCost += prodotto.getCosto() * quantity;
            }
        }

        Ordine ordine = new Ordine();
        ordine.setData(new java.util.Date());
        ordine.setCostoTotale(totalCost);
        ordine.setUtenteEmail(userRegistrato.getEmail());

        try {
            ordineDAO.doSave(ordine);

            if (ordine.getCodice() == 0) {
                throw new SQLException("Failed to retrieve order ID.");
            }

            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int codice = entry.getKey();
                int quantity = entry.getValue();
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(codice);
                if (prodotto != null) {
                    double prezzo = prodotto.getCosto();
                    contieneDAO.doSave(ordine.getCodice(), codice, quantity, prezzo);
                }
            }

            CarrelloDAO carrelloDAO = new CarrelloDAO();
            carrelloDAO.clearCart(userRegistrato.getEmail());
            request.getSession().removeAttribute("cart");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
