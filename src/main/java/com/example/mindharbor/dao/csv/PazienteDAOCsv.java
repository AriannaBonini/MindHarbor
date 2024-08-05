package com.example.mindharbor.dao.csv;

import com.example.mindharbor.dao.PazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAOCsv implements PazienteDAO {

    protected static final String FILE_PATH="MindHarborDB/csv/paziente.csv";
    protected static final String ERRORE_LETTURA="Errore nella lettura del file CSV";
    protected static final String ERRORE_SCRITTURA="Errore nella scrittura nel file CSV:";

    protected static final Integer INDICE_ANNI=0;
    protected static final Integer INDICE_DIAGNOSI=1;
    protected static final Integer INDICE_PAZIENTE_USERNAME=2;
    protected static final Integer INDICE_PSICOLOGO_USERNAME=3;


    @Override
    public List<Paziente> trovaPaziente(Utente psicologo) throws DAOException {
        List<Paziente> pazienteList = new ArrayList<>();
        UtenteDAOCsv utenteDAOCsv =new UtenteDAOCsv();
        Utente utente;

        TestPsicologicoDAOCsv testPsicologicoDAOCsv =new TestPsicologicoDAOCsv();
        Paziente numeroTestPaziente;

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Filtra i pazienti associati allo psicologo
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            // Verifichiamo che lo username dello psicologo sia quello corretto
            if (colonne[INDICE_PSICOLOGO_USERNAME].equals(psicologo.getUsername())) {
                Paziente paziente = new Paziente(colonne[INDICE_PAZIENTE_USERNAME]); // username del paziente

                utente= utenteDAOCsv.trovaInfoUtente(paziente);
                paziente.setNome(utente.getNome());
                paziente.setCognome(utente.getCognome());
                paziente.setGenere(utente.getGenere());

                numeroTestPaziente= testPsicologicoDAOCsv.numTestSvoltiPerPaziente(paziente);
                //con questa chiamata otteniamo il numero dei test svolti dal paziente da notificare allo psicologo
                paziente.setNumeroTest(numeroTestPaziente.getNumeroTest());

                pazienteList.add(paziente);
            }
        }

        return pazienteList;
    }

    @Override
    public Paziente getInfoSchedaPersonale(Paziente pazienteSelezionato) throws DAOException {
        // Questo metodo viene utilizzato per prendere dalla persistenza la diagnosi e l'età del paziente.

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH)); // Assicurati di specificare il percorso corretto
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca il paziente selezionato
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            // Supponiamo che l'username del paziente sia nella colonna 0
            if (colonne[INDICE_PAZIENTE_USERNAME].equals(pazienteSelezionato.getUsername())) {
                // Imposta l'età e la diagnosi
                pazienteSelezionato.setAnni(Integer.parseInt(colonne[INDICE_ANNI]));
                pazienteSelezionato.setDiagnosi(colonne[INDICE_DIAGNOSI]);
                break; // Esci dal ciclo una volta trovato il paziente
            }
        }

        return pazienteSelezionato;
    }

    @Override
    public boolean checkAnniPaziente(Paziente paziente) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Controlla se l'età del paziente corrisponde a quella fornita
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            //verifichiamo che lo username e l'età siano quelli corretti.
            if (colonne[INDICE_PAZIENTE_USERNAME].equals(paziente.getUsername()) && Integer.parseInt(colonne[INDICE_ANNI]) == paziente.getAnni()) {
                return true; // Trovato il paziente con l'età corretta
            }
        }

        return false; // Paziente non trovato o età non corrispondente
    }

    @Override
    public String getUsernamePsicologo(Utente paziente) throws DAOException {
        String usernamePsicologo = null;

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca l'username dello psicologo associato al paziente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            //verifichiamo che lo username del paziente sia quello cercato
            if (colonne[INDICE_PAZIENTE_USERNAME].equals(paziente.getUsername())) {
                usernamePsicologo = colonne[INDICE_PSICOLOGO_USERNAME]; // Ottieni l'username dello psicologo
                break; // Esci dal ciclo una volta trovato
            }
        }

        return usernamePsicologo; // Restituisce l'username dello psicologo o null se non trovato
    }



    @Override
    public void aggiungiPsicologoAlPaziente(Appuntamento appuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> file;
        try {
            file = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder recordAggiornato = new StringBuilder();

        // Aggiorna il nome utente dello psicologo nel CSV
        boolean recordUpdated = false;
        for (String riga : file) {
            String[] colonne = riga.split(",");

            // Controlla che il nome utente del paziente sia quello corretto
            if (colonne[INDICE_PAZIENTE_USERNAME].equals(appuntamento.getPaziente().getUsername())) {
                // Aggiorna il nome utente dello psicologo (supponiamo che sia nella seconda colonna)
                colonne[INDICE_PSICOLOGO_USERNAME] = appuntamento.getPsicologo().getUsername(); // Imposta il nome utente dello psicologo
                recordUpdated = true;
            }

            // Ricostruisci la riga e aggiungila al contenuto aggiornato
            recordAggiornato.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        if (!recordUpdated) {
            throw new DAOException("Paziente con username " + appuntamento.getPaziente().getUsername() + " non trovato nel file CSV.");
        }

        // Scrivi il contenuto aggiornato nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(recordAggiornato.toString());
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA +  " " + e.getMessage());
        }
    }

}


