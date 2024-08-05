package com.example.mindharbor.app_controllers.paziente.prescrivi_terapia;

import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.beans.TestResultBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.patterns.facade.DAOFactoryFacade;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.SetInfoUtente;
import java.util.Date;

public class SvolgiTestController {
    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    public InfoUtenteBean getInfoPaziente() {return new SetInfoUtente().getInfo();}
    public TestBean getTestSelezionato(){return navigator.getTestBean();}
    public void eliminaTestSelezionato(){navigator.deleteTestBean();}



    public DomandeTestBean cercaDomande(TestBean testSelezionato) {
        return BoundaryMockAPI.domandeTest(testSelezionato.getNomeTest());

    }

    public TestResultBean calcolaRisultato(DomandeTestBean punteggiBean, TestBean testSelezionato) throws DAOException {
        TestResultBean risultatoTest= new TestResultBean();
        try {
            Integer risultato = 0;

            for (Integer punteggio : punteggiBean.getPunteggi()) {
                risultato += punteggio;
            }
            risultatoTest.setRisultatoUltimoTest(risultato);
            Double progresso = calcolaProgresso(risultatoTest,testSelezionato.getData(),testSelezionato.getNomeTest());

            risultatoTest.setRisultatoTestPrecedente(progresso);
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return risultatoTest;
    }

    public Double calcolaProgresso(TestResultBean risultatoTest, Date dataTest, String nomeTest) throws DAOException {
        DAOFactoryFacade daoFactoryFacade=DAOFactoryFacade.getInstance();
        TestPsicologicoDAO testPsicologicoDAO= daoFactoryFacade.getTestPsicologicoDAO();

        double progressi;
        try {

            Integer punteggioPassato = testPsicologicoDAO.trovaTestPassati(new TestPsicologico(dataTest, risultatoTest.getRisultatoUltimoTest(), null, new Paziente(SessionManager.getInstance().getCurrentUser().getUsername()),nomeTest, null));

            if (punteggioPassato == null) {
                return null;

            } else {
                if (punteggioPassato == 0) {
                    progressi = (double) risultatoTest.getRisultatoUltimoTest() * 10;
                    progressi = Math.round(progressi * 100.0) / 100.0;
                    return progressi;

                }
                if (risultatoTest.getRisultatoUltimoTest() == 0) {
                    progressi = (double) (-punteggioPassato) * 10;
                    progressi = Math.round(progressi * 100.0) / 100.0;
                    return progressi;

                }

                progressi = (risultatoTest.getRisultatoUltimoTest() - punteggioPassato);
                progressi = (progressi / Math.abs(punteggioPassato)) * 100;
                progressi = Math.round(progressi * 100.0) / 100.0;

            }
        }catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }

        return progressi;
    }
}


