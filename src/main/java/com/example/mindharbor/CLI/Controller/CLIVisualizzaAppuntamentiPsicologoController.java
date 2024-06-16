package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLIVisualizzaAppuntamentiView;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
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
            List<AppuntamentiBean> beanList = new ArrayList<>();

            if (appuntamenti.isEmpty()) {
                view.displayNoAppointments();
                return;
            }

            // Adesso riempio la bean appuntamento
            for (Appuntamento appuntamento : appuntamenti) {
                AppuntamentiBean bean = new AppuntamentiBean(
                        appuntamento.getData(),
                        appuntamento.getOra(),
                        appuntamento.getUsernamePsicologo(),
                        appuntamento.getUsernamePsicologo(),
                        appuntamento.getNomePaziente(),
                        appuntamento.getCognomePaziente(),
                        appuntamento.getNomePsicologo(),
                        appuntamento.getCognomePsicologo(),
                        appuntamento.getIdAppuntamento()
                );
                beanList.add(bean);
            }
            view.displayAppointmentsBean(beanList);
            handleUserInput();

        } catch (SQLException e) {
            view.displayErrorMessage("Errore durante la ricerca degli appuntamenti");
        }
    }

    private void handleUserInput() throws SQLException {
        int scelta = view.getUserInput();
        if (scelta == 0) {
            view.cleanPage();
            cliNavigator.showHomepage();
        }
    }
}
