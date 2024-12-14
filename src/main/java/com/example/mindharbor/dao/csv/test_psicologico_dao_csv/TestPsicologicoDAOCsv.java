package com.example.mindharbor.dao.csv.test_psicologico_dao_csv;

import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.utilities.UtilitiesCSV;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestPsicologicoDAOCsv implements TestPsicologicoDAO {

    @Override
    public void assegnaTest(TestPsicologico test) throws DAOException {
        // Leggi tutte le righe del file CSV come array di colonne
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);

        // Crea un nuovo array per rappresentare il test da aggiungere
        String[] nuovoTest = new String[8];
        nuovoTest[0] = new java.sql.Date(System.currentTimeMillis()).toString(); // Data
        nuovoTest[1] = "0"; // Risultato
        nuovoTest[2] = test.getPsicologo().getUsername(); // Psicologo
        nuovoTest[3] = test.getPaziente().getUsername(); // Paziente
        nuovoTest[4] = test.getTest(); // Test
        nuovoTest[5] = "1"; // Stato Notifica Paziente
        nuovoTest[6] = "0"; // Svolto
        nuovoTest[7] = "0"; // Stato Notifica Psicologo

        // Aggiungi il nuovo test alla lista delle righe
        righeCSV.add(nuovoTest);

        // Scrivi il contenuto aggiornato nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(ConstantsTestPsicologicoCsv.FILE_PATH, righeCSV);
    }

    @Override
    public Integer getNotificaPazientePerTestAssegnato(Utente paziente) throws DAOException {
        return UtilitiesCSV.contaNotifichePaziente(ConstantsTestPsicologicoCsv.FILE_PATH, paziente.getUsername(), ConstantsTestPsicologicoCsv.INDICE_PAZIENTE, ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PAZIENTE);
    }


    @Override
    public void modificaStatoNotificaTest(Utente utente, Paziente pazienteSelezionato) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);

        // Verifica il tipo di utente e modifica il contenuto di conseguenza
        for (String[] colonne : righeCSV) {
            // Esegui la modifica per il paziente
            if (utente.getUserType().equals(UserType.PAZIENTE)) {
                //L'utente è di tipo Paziente
                if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(utente.getUsername()) && colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PAZIENTE].equals("1")) {
                    colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PAZIENTE] = "0"; // Modifica lo stato
                }
            } else {
                //l'utente è di tipo Psicologo
                if (colonne[ConstantsTestPsicologicoCsv.INDICE_PSICOLOGO].equals(utente.getUsername()) &&
                        colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(pazienteSelezionato.getUsername()) &&
                        colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO].equals("1")) {
                    colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO] = "0"; // Modifica lo stato notifica dello psicologo
                }
            }
        }

        // Scrivi il nuovo contenuto nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(ConstantsTestPsicologicoCsv.FILE_PATH, righeCSV);
    }

    @Override
    public List<TestPsicologico> trovaListaTest(Utente paziente) throws DAOException {

        List<TestPsicologico> testPsicologicoList = new ArrayList<>();

        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);

        for (String[] colonne : righeCSV) {
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(paziente.getUsername())) {
                // Converti la data da String a java.sql.Date
                LocalDate localDate = LocalDate.parse(colonne[ConstantsTestPsicologicoCsv.INDICE_DATA]);
                Date data = java.sql.Date.valueOf(localDate);

                TestPsicologico test = new TestPsicologico(
                        data,
                        Integer.parseInt(colonne[ConstantsTestPsicologicoCsv.INDICE_RISULTATO]),
                        colonne[ConstantsTestPsicologicoCsv.INDICE_TEST],
                        Integer.parseInt(colonne[ConstantsTestPsicologicoCsv.INDICE_SVOLTO])
                );
                testPsicologicoList.add(test);
            }
        }
        return testPsicologicoList;
    }

    @Override
    public Integer trovaTestPassati(TestPsicologico testDaAggiungere) throws DAOException {
        Integer testPassati = null;

        // Leggi tutte le righe del file CSV
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);

        // Variabile per tenere traccia del massimo della data
        LocalDate maxData = null;

        // Filtra le righe in base all'username del paziente e al test
        for (String[] colonne : righeCSV) {
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(testDaAggiungere.getPaziente().getUsername())
                    && colonne[ConstantsTestPsicologicoCsv.INDICE_TEST].equals(testDaAggiungere.getTest())
                    && Integer.parseInt(colonne[ConstantsTestPsicologicoCsv.INDICE_SVOLTO]) == 1) {

                LocalDate dataTest = LocalDate.parse(colonne[ConstantsTestPsicologicoCsv.INDICE_DATA]);

                // Aggiorna maxData se trovi una data più recente
                if (maxData == null || dataTest.isAfter(maxData)) {
                    maxData = dataTest;
                    testPassati = Integer.parseInt(colonne[ConstantsTestPsicologicoCsv.INDICE_RISULTATO]);
                }
            }
        }

        aggiornaTestAppenaSvolto(testDaAggiungere);

        return testPassati;
    }


    private void aggiornaTestAppenaSvolto(TestPsicologico testDaAggiungere) throws DAOException {
        // Lettura del file CSV esistente
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);
        List<String[]> righeAggiornate = new ArrayList<>();

        // Aggiornamento delle righe esistenti
        for (String[] colonne : righeCSV) {
            // Controlla se la riga corrisponde al test che vogliamo aggiornare
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(testDaAggiungere.getPaziente().getUsername())
                    && LocalDate.parse(colonne[ConstantsTestPsicologicoCsv.INDICE_DATA]).isEqual(testDaAggiungere.convertiInLocalDate(testDaAggiungere.getData()))) {

                // Aggiorna il risultato
                colonne[ConstantsTestPsicologicoCsv.INDICE_RISULTATO] = String.valueOf(testDaAggiungere.getRisultato());
                colonne[ConstantsTestPsicologicoCsv.INDICE_SVOLTO] = String.valueOf(1);
                colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO] = String.valueOf(1);
            }

            // Aggiungi la riga (aggiornata o meno) alla lista
            righeAggiornate.add(colonne);
        }

        // Scrittura delle righe aggiornate nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(ConstantsTestPsicologicoCsv.FILE_PATH, righeAggiornate);
    }

    @Override
    public Integer getNumTestSvoltiDaNotificare(Utente psicologo) throws DAOException {
        int count = 0;

        // Leggi tutte le righe del file CSV
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);

        // Conta i test da notificare
        for (String[] colonne : righeCSV) {
            if ("1".equals(colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO]) && psicologo.getUsername().equals(colonne[ConstantsTestPsicologicoCsv.INDICE_PSICOLOGO])) {
                count++;
            }
        }

        return count; // Restituisce il conteggio
    }

    @Override
    public Integer getNumTestSvoltiSenzaPrescrizione(Utente utentePsicologo, Paziente paziente) {
        //da modificare la query poiché tocca sia la tabella test psicologico che la tabella terapia.
        return 0;
    }

    public List<TestPsicologico> listaTestSvoltiSenzaPrescrizione(String usernamePaziente, String usernamePsicologo) {
        //anche questo metodo (per lo stesso motivo di quello sopra) va sistemato.
        return null;
    }

    @Override
    public Paziente numTestSvoltiPerPaziente(Utente paziente) throws DAOException {
        // Lettura del file CSV esistente
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);
        int numeroTestSvolti = 0;

        // Calcola il numero di test svolti dal paziente
        for (String[] colonne : righeCSV) {
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(paziente.getUsername())) {
                numeroTestSvolti += Integer.parseInt(colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO]); // Incrementa il conteggio
            }
        }

        // Crea un nuovo oggetto Paziente con il numero di test svolti
        return new Paziente(numeroTestSvolti);
    }

    @Override
    public Integer getNumTestAssegnato(Paziente paziente) throws DAOException {
        int contatore = 0;

        // Leggi tutte le righe del file CSV
        List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsTestPsicologicoCsv.FILE_PATH);

        // Data odierna in formato YYYY-MM-DD
        String dataOdierna = java.time.LocalDate.now().toString();

        // Conta i test assegnati nella data odierna
        for (String[] colonne : righeCSV) {
            // Controlla se il test è assegnato al paziente nella data odierna
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_DATA].equals(dataOdierna) && paziente.getUsername().equals(colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE])) {
                contatore++;
            }
        }

        return contatore;
    }
}

