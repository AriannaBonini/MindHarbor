package com.example.mindharbor.dao.csv.test_psicologico_dao_csv;

import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.utilities.UtilitiesCSV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestPsicologicoDAOCsv implements TestPsicologicoDAO {

    @Override
    public void assegnaTest(TestPsicologico test) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Crea una stringa per il nuovo test da aggiungere
        StringBuilder nuovoTest = new StringBuilder();
        nuovoTest.append(new java.sql.Date(System.currentTimeMillis())).append(","); // Data
        nuovoTest.append("0,"); // Risultato
        nuovoTest.append(test.getPsicologo().getUsername()).append(","); // Psicologo
        nuovoTest.append(test.getPaziente().getUsername()).append(","); // Paziente
        nuovoTest.append(test.getTest()).append(","); // Test
        nuovoTest.append("1,"); // Stato Notifica Paziente
        nuovoTest.append("0,"); // Svolto
        nuovoTest.append("0"); // Stato Notifica Psicologo

        // Aggiungi il nuovo test alla lista delle righe
        righeCSV.add(nuovoTest.toString());

        // Scrivi il contenuto aggiornato nel file CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ConstantsTestPsicologicoCsv.FILE_PATH))) {
            for (String riga : righeCSV) {
                writer.write(riga);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public Integer getNotificaPazientePerTestAssegnato(Utente paziente) throws DAOException {
        return UtilitiesCSV.contaNotifichePaziente(ConstantsTestPsicologicoCsv.FILE_PATH, paziente.getUsername(), ConstantsTestPsicologicoCsv.INDICE_PAZIENTE, ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PAZIENTE);
    }


    @Override
    public void modificaStatoNotificaTest(Utente utente, Paziente pazienteSelezionato) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // StringBuilder per il nuovo contenuto del CSV
        StringBuilder nuovoContenuto = new StringBuilder();

        // Verifica il tipo di utente e modifica il contenuto di conseguenza
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

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

            // Ricostruisci la riga e aggiungila al nuovo contenuto
            nuovoContenuto.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        // Scrivi il nuovo contenuto nel file CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ConstantsTestPsicologicoCsv.FILE_PATH))) {
            writer.write(nuovoContenuto.toString());
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public List<TestPsicologico> trovaListaTest(Utente paziente) throws DAOException {
        List<TestPsicologico> testPsicologicoList = new ArrayList<>();

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Filtra le righe in base all'username del paziente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            // Assumiamo che PAZIENTE sia nella colonna corretta
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(paziente.getUsername())) {
                TestPsicologico test = new TestPsicologico(
                        java.sql.Date.valueOf(colonne[ConstantsTestPsicologicoCsv.INDICE_DATA]),
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
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Variabile per tenere traccia del massimo della data
        LocalDate maxData = null;

        // Filtra le righe in base all'username del paziente e al test
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

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
        List<String> righeCSV;
        Path path = Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH);
        try {
            righeCSV = Files.readAllLines(path);
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        List<String> righeAggiornate = new ArrayList<>();

        // Aggiornamento delle righe esistenti
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            // Controlla se la riga corrisponde al test che vogliamo aggiornare
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(testDaAggiungere.getPaziente().getUsername())
                    && LocalDate.parse(colonne[ConstantsTestPsicologicoCsv.INDICE_DATA]).isEqual(testDaAggiungere.convertiInLocalDate(testDaAggiungere.getData()))) {

                // Aggiorna il risultato
                colonne[ConstantsTestPsicologicoCsv.INDICE_RISULTATO] = String.valueOf(testDaAggiungere.getRisultato());
                colonne[ConstantsTestPsicologicoCsv.INDICE_SVOLTO]= String.valueOf(1);
                colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO]=String.valueOf(1);
            }

            // Aggiungi la riga (aggiornata o meno) alla lista
            righeAggiornate.add(String.join(",", colonne));
        }

        // Scrittura delle righe aggiornate nel file CSV
        try {
            Files.write(path, righeAggiornate);
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public Integer getNumTestSvoltiDaNotificare(Utente psicologo) throws DAOException {
        int count = 0;

        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Conta i test da notificare
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            if ("1".equals(colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO]) && psicologo.getUsername().equals(colonne[ConstantsTestPsicologicoCsv.INDICE_PSICOLOGO])) {
                count++;
            }
        }

        return count; // Restituisce il conteggio
    }

    @Override
    public Integer getNumTestSvoltiSenzaPrescrizione(Utente utentePsicologo, Paziente paziente) throws DAOException {
        //da modificare la query poiché tocca sia la tabella test psicologico che la tabella terapia.
        return 0;
    }

    public List<TestPsicologico> listaTestSvoltiSenzaPrescrizione(String usernamePaziente, String usernamePsicologo) throws DAOException {
        //anche questo metodo (per lo stesso motivo di quello sopra) va sistemato.
        return null;
    }

    @Override
    public Paziente numTestSvoltiPerPaziente(Utente paziente) throws DAOException {
        Paziente numeroTestSvoltiPaziente;

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        int numeroTestSvolti = 0;

        // Calcola il numero di test svolti dal paziente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            if (colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE].equals(paziente.getUsername())) {
                numeroTestSvolti += Integer.parseInt(colonne[ConstantsTestPsicologicoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO]); // Incrementa il conteggio
            }
        }

        // Crea un nuovo oggetto Paziente con il numero di test svolti
        numeroTestSvoltiPaziente = new Paziente(numeroTestSvolti);

        return numeroTestSvoltiPaziente;
    }

    @Override
    public Integer getNumTestAssegnato(Paziente paziente) throws DAOException {
        int contatore = 0;

        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsTestPsicologicoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsTestPsicologicoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Data odierna in formato YYYY-MM-DD
        String dataOdierna = java.time.LocalDate.now().toString();

        // Conta i test assegnati nella data odierna
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            // Controlla se il test è assegnato al paziente nella data odierna
            if (colonne[ConstantsTestPsicologicoCsv.INDICE_DATA].equals(dataOdierna) && paziente.getUsername().equals(colonne[ConstantsTestPsicologicoCsv.INDICE_PAZIENTE])) {
                contatore++;
            }
        }

        return contatore;
    }
}

