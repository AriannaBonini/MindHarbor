package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLIVisualizzaAppuntamentiView;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class CLIVisualizzaAppuntamentiPsicologoController {
    private SessionManager sessionManager;
    private CLIVisualizzaAppuntamentiView view;
    private AppuntamentoDAO appuntamentoDAO; // Utile per usare AppuntamentoDAO
    private CLINavigator cliNavigator;

    public CLIVisualizzaAppuntamentiPsicologoController(SessionManager sessionManager, CLINavigator cliNavigator) {
        this.sessionManager = sessionManager;
        this.view = new CLIVisualizzaAppuntamentiView();
        this.appuntamentoDAO = new AppuntamentoDAO();
        this.cliNavigator = cliNavigator;

    }
    public void visualizza() {
        view.cleanPage();
        try {
            String username = sessionManager.getCurrentUser().getUsername();
            List<Appuntamento> appuntamenti = appuntamentoDAO.trovaAppuntamento(username);
            if (appuntamenti.isEmpty()) {
                view.displayNoAppointments();
                return;
            }
            view.displayAppointments(appuntamenti);
            handleUserInput();
        } catch (SQLException e) {
            view.displayErrorMessage("Errore durante la ricerca degli appuntamenti");
        }
    }

    private void handleUserInput() {
        while (true) {
            String scelta = view.getUserInput();
            if ("exit".equalsIgnoreCase(scelta)) {
                view.cleanPage();
                cliNavigator.showHomepage();
                break;
            } else {
                view.displayErrorMessage("Inserire 'exit' per tornare alla homepage");
            }
        }
    }
}
