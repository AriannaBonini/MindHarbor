package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLIPrescriviTerapiaView;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CLIPrescriviTerapiaController {
    private SessionManager sessionManager;
    private CLIPrescriviTerapiaView view;
    private AppuntamentoDAO appuntamentoDAO; // Utile per usare AppuntamentoDAO
    private CLINavigator cliNavigator;

    public CLIPrescriviTerapiaController(SessionManager sessionManager, CLINavigator cliNavigator) {
        this.sessionManager = sessionManager;
        this.view = new CLIPrescriviTerapiaView();
        this.appuntamentoDAO = new AppuntamentoDAO();
        this.cliNavigator = cliNavigator;
    }

    public void start() {
        try {
            String username = sessionManager.getCurrentUser().getUsername();
            List<Appuntamento> appuntamenti = appuntamentoDAO.trovaAppuntamento(username);
            if (appuntamenti.isEmpty()) {
                view.displayErrorMessage("Non ci sono appuntamenti");
                return;
            }

            // Convertiamo la lista di Appuntamenti in lista di bean
            List<AppuntamentiBean> beans = new ArrayList<>();
            for (Appuntamento appuntamento : appuntamenti) {
                AppuntamentiBean bean = new AppuntamentiBean(
                        appuntamento.getData(),
                        appuntamento.getOra(),
                        appuntamento.getNomePsicologo(),
                        appuntamento.getCognomePsicologo(),
                        appuntamento.getNomePaziente(),
                        appuntamento.getCognomePaziente(),
                        appuntamento.getUsernamePaziente(),
                        appuntamento.getUsernamePsicologo(),
                        appuntamento.getIdAppuntamento()
                );
                beans.add(bean);
            }

            view.displayAppointmentsBean(beans);

            int num_app = 0;

            int scelta = view.getUserInput();
            if (scelta == 0) {
                cliNavigator.showHomepage();
                return;
            }
            num_app = scelta-1;
            AppuntamentiBean selectedAppuntamento = beans.get(num_app);
            // Qui aggiungi la logica per "Prescrivi Terapia", "Seleziona Test", ecc.

        } catch (SQLException e) {
            view.displayErrorMessage("Errore durante la ricerca degli appuntamenti");
        }
    }
}
