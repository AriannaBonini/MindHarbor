package com.example.mindharbor.dao.csv.paziente_dao_csv;

import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.dao.csv.test_psicologico_dao_csv.TestPsicologicoDAOCsv;
import com.example.mindharbor.dao.csv.utente_dao_csv.UtenteDAOCsv;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.utilities.UtilitiesCSV;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAOCsv implements PazienteDAO {

    public List<Paziente> trovaPazienti(Utente psicologo) throws DAOException {
        List<Paziente> pazienteList = new ArrayList<>();
        UtenteDAOCsv utenteDAOCsv = new UtenteDAOCsv();
        Utente utente;
        TestPsicologicoDAOCsv testPsicologicoDAOCsv = new TestPsicologicoDAOCsv();
        Paziente numeroTestPaziente;

        try {
            // Usa il CSVReader per leggere le righe del CSV
            List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsPazienteCsv.FILE_PATH);

            // Scarta l'intestazione (la prima riga) nel CSV
            // Le righe restanti sono quelle dei pazienti
            righeCSV.removeFirst();  // Rimuove la prima riga che contiene l'intestazione

            for (String[] colonne : righeCSV) {
                String pazienteUsername = colonne[2].trim();
                String usernamePsicologo = colonne[3].trim();
                if (!pazienteUsername.isBlank() && !usernamePsicologo.isEmpty()) {
                    Paziente paziente = new Paziente(pazienteUsername);
                    utente = utenteDAOCsv.trovaInfoUtente(paziente);

                    if (usernamePsicologo.equals(psicologo.getUsername()) && utente != null) {
                        paziente = creaIstanzaPaziente(utente, colonne);

                        numeroTestPaziente = testPsicologicoDAOCsv.numTestSvoltiPerPaziente(paziente);

                        if (numeroTestPaziente != null) {
                            paziente.setNumeroTest(numeroTestPaziente.getNumeroTest());
                        }
                        pazienteList.add(paziente);
                    }
                }
            }
        } catch (DAOException e) {
            throw new DAOException("Errore nella lettura del file CSV: " + e.getMessage(), e);
        }

        return pazienteList;
    }

    @Override
    public Paziente getInfoSchedaPersonale(Paziente pazienteSelezionato) throws DAOException {
        // Questo metodo viene utilizzato per prendere dalla persistenza la diagnosi e l'età del paziente.

        // Leggi tutte le righe del file CSV
        List<String[]> righeCSV;
        try {
            righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsPazienteCsv.FILE_PATH); // Usa il metodo leggiRigheDaCsv per leggere con CSVReader
        } catch (DAOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage(), e);
        }

        // Cerca il paziente selezionato
        for (String[] colonne : righeCSV) {
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(pazienteSelezionato.getUsername())) {
                // Imposta l'età e la diagnosi
                pazienteSelezionato.setAnni(Integer.parseInt(colonne[ConstantsPazienteCsv.INDICE_ANNI]));
                pazienteSelezionato.setDiagnosi(colonne[ConstantsPazienteCsv.INDICE_DIAGNOSI]);
                break; // Esci dal ciclo una volta trovato il paziente
            }
        }

        return pazienteSelezionato;
    }

    @Override
    public boolean checkAnniPaziente(Paziente paziente) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String[]> righeCSV;
        try {
            righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsPazienteCsv.FILE_PATH); // Usa CSVReader tramite leggiRigheDaCsv
        } catch (DAOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage(), e);
        }

        // Controlla se l'età del paziente corrisponde a quella fornita
        for (String[] colonne : righeCSV) {
            // Verifica che lo username e l'età siano corretti
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(paziente.getUsername())
                    && Integer.parseInt(colonne[ConstantsPazienteCsv.INDICE_ANNI]) == paziente.getAnni()) {
                return true; // Trovato il paziente con l'età corretta
            }
        }

        return false; // Paziente non trovato o età non corrispondente
    }

    @Override
    public String getUsernamePsicologo(Utente paziente) throws DAOException {
        String usernamePsicologo = null;

        // Leggi tutte le righe del file CSV utilizzando CSVReader
        List<String[]> righeCSV;
        try {
            righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsPazienteCsv.FILE_PATH); // Usa il metodo leggiRigheDaCsv
        } catch (DAOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage(), e);
        }

        // Cerca l'username dello psicologo associato al paziente
        for (String[] colonne : righeCSV) {
            // Verifica che lo username del paziente corrisponda a quello cercato
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(paziente.getUsername())) {
                usernamePsicologo = colonne[ConstantsPazienteCsv.INDICE_PSICOLOGO_USERNAME]; // Ottieni l'username dello psicologo
                break; // Esci dal ciclo una volta trovato
            }
        }

        return usernamePsicologo; // Restituisce l'username dello psicologo o null se non trovato
    }



    @Override
    public void aggiungiPsicologoAlPaziente(Appuntamento appuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV utilizzando CSVReader
        List<String[]> righeCSV;
        try {
            righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsPazienteCsv.FILE_PATH); // Usa il metodo leggiRigheDaCsv
        } catch (DAOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage(), e);
        }

        // Variabile per sapere se il record è stato aggiornato
        boolean recordUpdated = false;

        // Aggiorna il nome utente dello psicologo nel CSV
        for (String[] colonne : righeCSV) {
            // Verifica se lo username del paziente è quello cercato
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(appuntamento.getPaziente().getUsername())) {
                // Imposta il nome utente dello psicologo (supponiamo che sia nella colonna giusta)
                colonne[ConstantsPazienteCsv.INDICE_PSICOLOGO_USERNAME] = appuntamento.getPsicologo().getUsername(); // Imposta lo psicologo
                recordUpdated = true;
                break; // Esci non appena il record viene aggiornato
            }
        }

        if (!recordUpdated) {
            throw new DAOException("Paziente con username " + appuntamento.getPaziente().getUsername() + " non trovato nel file CSV.");
        }

        // Scrivi il contenuto aggiornato nel file CSV utilizzando CSVWriter
        try {
            UtilitiesCSV.scriviRigheAggiornate(ConstantsPazienteCsv.FILE_PATH, righeCSV); // Usa il metodo per scrivere nel CSV
        } catch (DAOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_SCRITTURA + " " + e.getMessage(), e);
        }
    }

    private Paziente creaIstanzaPaziente(Utente utente, String[] rigaPaziente) {
        Paziente paziente = new Paziente(utente.getUsername(), utente.getNome(), utente.getCognome());
        paziente.setGenere(utente.getGenere());
        paziente.setAnni(Integer.parseInt(rigaPaziente[0]));
        paziente.setDiagnosi(rigaPaziente[1]);
        return paziente;
    }
}


