package com.example.mindharbor.CLI.Controller;

import com.example.mindharbor.CLI.Navigator.CLINavigator;
import com.example.mindharbor.CLI.View.CLIScegliTestView;
import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.patterns.ClassObserver;
import com.example.mindharbor.session.SessionManager;

import java.sql.SQLException;
import java.util.List;

public class CLIScegliTestController {

    private SessionManager sessionManager;
    private CLIScegliTestView view;
    private CLINavigator cliNavigator;
    private String usernamePaziente;
    private ClassObserver observer = new ClassObserver();

    public CLIScegliTestController(SessionManager sessionManager, CLINavigator cliNavigator, String usernamePaziente) {
        this.sessionManager = sessionManager;
        this.view = new CLIScegliTestView();
        this.cliNavigator = cliNavigator;
        this.usernamePaziente = usernamePaziente;
    }
    public void selezionaTest() {
        try {
            List<String> listaTestPsicologici = BoundaryMockAPI.TestPsicologici();
            view.displayTests(listaTestPsicologici);
            int scelta = view.getUserInput(listaTestPsicologici);
            if (scelta == 0) {
                // se la scelta è 0 => problema, quindi torna alla home
                cliNavigator.showHomepage();
                return;
            }
            String nomeTest = listaTestPsicologici.get(scelta - 1);

            notificaTest(usernamePaziente, nomeTest);

            view.displaySuccess(nomeTest);


            cliNavigator.showHomepage(); // Questa riga crea problemi, perche in showhomepage si fa il cleanpage e quindi l'utente non ha modo di vedere il messaggio di successo.

        } catch (SQLException e) {
            view.displayErrorMessage("Errore durante la selezione del test: " + e.getMessage());
        } catch (Exception e) {
            view.displayErrorMessage("Errore di sistema: " + e.getMessage());
        }
    }
    private void notificaTest(String usernamePaziente, String nomeTest) throws SQLException {
        PazienteDAO pazienteDao = new PazienteDAO();
        TestPsicologicoDAO testPsicologicoDAO = new TestPsicologicoDAO();
        String usernamePsicologo = pazienteDao.TrovaPsicologo(usernamePaziente);
        testPsicologicoDAO.assegnaTest(usernamePaziente, usernamePsicologo, nomeTest);

        // Notifichiamo l'observer
        observer.notifyObservers(sessionManager.getCurrentUser().getUserType());
    }
}
