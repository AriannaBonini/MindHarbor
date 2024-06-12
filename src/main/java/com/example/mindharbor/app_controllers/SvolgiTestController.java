package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.DomandeTestBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TestResultBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.mockapi.BoundaryMockAPI;
import com.example.mindharbor.model.DomandeTest;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.setInfoUtente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SvolgiTestController {
    public HomeInfoUtenteBean getPagePazInfo() {
        HomeInfoUtenteBean homeInfoUtente = new setInfoUtente().getInfo();
        return homeInfoUtente;
    }

    public DomandeTestBean CercaDomande(String nomeTest) {
        DomandeTest domandeTest = BoundaryMockAPI.DomandeTest(nomeTest);

        DomandeTestBean domandeBean = new DomandeTestBean(domandeTest.getDomande(), domandeTest.getPunteggi());
        return domandeBean;
    }

    public TestResultBean calcolaRisultato(List<Integer> punteggi, Date dataTest, String nomeTest) throws SQLException {
        TestResultBean testResult= new TestResultBean();
        Integer risultato=0;

        for (int i = 0; i < punteggi.size(); i++) {
            risultato+=punteggi.get(i);
        }
        testResult.setRisultatoUltimoTest(risultato);
        Double progresso=calcolaProgresso(testResult,dataTest,nomeTest);

        testResult.setRisultatoTestPrecedente(progresso);

        return testResult;
    }

    public Double calcolaProgresso(TestResultBean testResult, Date dataTest, String nomeTest) throws SQLException {

        Double progressi = null;

        Integer punteggioPassato = new TestPsicologicoDAO().trovaTestPassati(testResult.getRisultatoUltimoTest(), SessionManager.getInstance().getCurrentUser().getUsername(), dataTest, nomeTest);

        if (punteggioPassato==null) {
            //non ci sono test passati svolti
            return progressi;

        } else {
            if (punteggioPassato == 0) {
                progressi=(double) punteggioPassato*100;
                progressi=Math.round(progressi*100.0)/100.0;
                return progressi;

            }
            System.out.println("attuale:" + testResult.getRisultatoUltimoTest() + "  passato: " + punteggioPassato);

            progressi = (double) (testResult.getRisultatoUltimoTest() - punteggioPassato);
            progressi= (progressi/punteggioPassato)*100;
            progressi= Math.round(progressi * 100.0) / 100.0; //arrotonda a due cifre dopo la virgola
            System.out.println(progressi);


            return progressi;
        }
    }
}

