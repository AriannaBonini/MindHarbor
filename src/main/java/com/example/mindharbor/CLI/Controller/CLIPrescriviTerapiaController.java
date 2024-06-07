package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLIPrescriviTerapiaView;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.InputHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CLIPrescriviTerapiaController {
    private SessionManager sessionManager;
    private CLIPrescriviTerapiaView view;
    private PazienteDAO pazienteDAO;
    private CLINavigator cliNavigator;
    private InputHandler inputHandler;

    public CLIPrescriviTerapiaController(SessionManager sessionManager, CLINavigator cliNavigator) {
        this.sessionManager = sessionManager;
        this.view = new CLIPrescriviTerapiaView();
        this.pazienteDAO = new PazienteDAO();
        this.cliNavigator = cliNavigator;
        this.inputHandler = new InputHandler();
    }

    public void start() {
        view.clean();
        try {
            String username = sessionManager.getCurrentUser().getUsername();
            List<Paziente> pazienti = pazienteDAO.trovaPaziente(username);


            if (pazienti.isEmpty()) {
                view.displayErrorMessagePatient();
                return;
            }

            // Convertiamo la lista di pazienti in lista di bean
            List<PazientiBean> beans = new ArrayList<>();
            for (Paziente paziente : pazienti) {
                PazientiBean bean = new PazientiBean(
                        paziente.getNome(),
                        paziente.getCognome(),
                        paziente.getGenere(),
                        paziente.getEtà(),
                        paziente.getDiagnosi(),
                        paziente.getUsername()
                );
                beans.add(bean);
            }

            view.displayPatientsBean(beans);

            // QUI BISOGNA GESTIRE MEGLIO LA SCELTA
            int scelta = view.getUserInput(inputHandler,0, beans.size());
            if (scelta == 0) {
                cliNavigator.showHomepage();
                return;
            }
            int pat = scelta-1;
            PazientiBean selectedPatient = beans.get(pat);

            // qui va aggiunta la logica per la scheda personale del paziente
            cliNavigator.showSchedaPersonale(selectedPatient.getUsername());

/*
            // Qui aggiungi la logica per "Prescrivi Terapia", "Seleziona Test", ecc.
            CLIScegliTestController scegliTestController = new CLIScegliTestController(sessionManager, cliNavigator, selectedPatient.getUsername());
            scegliTestController.selezionaTest();

 */


        } catch (SQLException e) {
            view.displayErrorMessageForm(e);
        }
    }
}
