package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.beans.TestResultBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.DomandeTest;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.util.Date;
import java.util.List;

public class SvolgiTestController {
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    public InfoUtenteBean getInfoPaziente() {
        return new setInfoUtente().getInfo();
    }
    public TestBean getTestSelezionato(){return navigator.getTestBean();}
    public void deleteTestSelezionato(){navigator.deleteTestBean();}



    public DomandeTestBean cercaDomande(TestBean testSelezionato) {
        DomandeTest domandeTest = BoundaryMockAPI.DomandeTest(testSelezionato.getNomeTest());

        return new DomandeTestBean(domandeTest.getDomande(), domandeTest.getPunteggi());
    }

    public TestResultBean calcolaRisultato(List<Integer> punteggi, TestBean testSelezionato) throws DAOException {
        TestResultBean testResult= new TestResultBean();
        try {
            Integer risultato = 0;

            for (Integer integer : punteggi) {
                risultato += integer;
            }
            testResult.setRisultatoUltimoTest(risultato);
            Double progresso = calcolaProgresso(testResult,testSelezionato.getData(),testSelezionato.getNomeTest());

            testResult.setRisultatoTestPrecedente(progresso);
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return testResult;
    }

    public Double calcolaProgresso(TestResultBean testResult, Date dataTest, String nomeTest) throws DAOException {

        double progressi;
        try {

            Integer punteggioPassato = new TestPsicologicoDAO().trovaTestPassati(new TestPsicologico(dataTest, testResult.getRisultatoUltimoTest(), null, new Paziente(SessionManager.getInstance().getCurrentUser().getUsername()),nomeTest, null));

            if (punteggioPassato == null) {
                return null;

            } else {
                if (punteggioPassato == 0) {
                    progressi = (double) testResult.getRisultatoUltimoTest() * 10;
                    progressi = Math.round(progressi * 100.0) / 100.0;
                    return progressi;

                }
                if (testResult.getRisultatoUltimoTest() == 0) {
                    progressi = (double) (-punteggioPassato) * 10;
                    progressi = Math.round(progressi * 100.0) / 100.0;
                    return progressi;

                }

                progressi = testResult.getRisultatoUltimoTest() - punteggioPassato;
                progressi = (progressi / Math.abs(punteggioPassato)) * 100;
                progressi = Math.round(progressi * 100.0) / 100.0;

            }
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }

        return progressi;
    }
}


