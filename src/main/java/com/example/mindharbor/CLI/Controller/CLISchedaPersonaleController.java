package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLISchedaPersonaleView;
import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.InputHandler;

import java.sql.SQLException;

public class CLISchedaPersonaleController {
    private SessionManager sessionManager;
    private CLISchedaPersonaleView view;
    private CLINavigator cliNavigator;
    private PazienteDAO pazienteDAO;
    private InputHandler inputHandler;

    public CLISchedaPersonaleController(SessionManager sessionManager, CLINavigator cliNavigator){
        this.sessionManager = sessionManager;
        this.cliNavigator = cliNavigator;
        this.view = new CLISchedaPersonaleView();
        this.pazienteDAO = new PazienteDAO();
        this.inputHandler = new InputHandler();
    }

    public void visualizzaSchedaPersonale(String usernamePaziente){
        view.clean();
        try{
            Paziente paziente = pazienteDAO.getInfoSchedaPersonale(usernamePaziente);
            PazientiBean pazienteBean = new PazientiBean(
                    paziente.getNome(),
                    paziente.getCognome(),
                    paziente.getGenere(),
                    paziente.getEtà(),
                    paziente.getDiagnosi(),
                    paziente.getUsername()
                    );

            view.displaySchedaPersonale(pazienteBean);
            view.displayOptions();

            int scelta = view.getUserInput(inputHandler);
            handleUserInput(scelta, usernamePaziente);

        } catch (SQLException e) {
            view.displayErrorMessage(e);
        }
    }
    private void handleUserInput(int scelta, String usernamePaziente){
        switch (scelta){
            case 1:
                // Logica per Seleziona Test
                cliNavigator.selezionaTest(usernamePaziente);
                break;
            case 2:
                // Logica per tornare alla lista dei pazienti
                cliNavigator.showPrescriviTerapia(); // non so se questo porti di nuovo alla lista pazienti corretta, non ne sono certo
                break;
            case 3:
                // Logica per tornare alla homepage
                cliNavigator.showHomepage();
                break;
            default:
                // Mostra un messaggio di errore o un'opzione non valida
                view.displayInvalidOptionMessage();
                visualizzaSchedaPersonale(usernamePaziente);
                break;
        }
    }
}
