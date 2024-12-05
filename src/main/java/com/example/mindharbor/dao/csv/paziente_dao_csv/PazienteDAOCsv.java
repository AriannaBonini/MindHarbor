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

        try (BufferedReader br = new BufferedReader(new FileReader(ConstantsPazienteCsv.FILE_PATH))) {
            UtilitiesCSV.scartaIntestazione(br);  // Scarta l'intestazione se presente
            String line;

            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(",", -1);
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
        } catch (IOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage(), e);
        }
        return pazienteList;
    }

    @Override
    public Paziente getInfoSchedaPersonale(Paziente pazienteSelezionato) throws DAOException {
        // Questo metodo viene utilizzato per prendere dalla persistenza la diagnosi e l'età del paziente.

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsPazienteCsv.FILE_PATH)); // Assicurati di specificare il percorso corretto
        } catch (IOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca il paziente selezionato
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");
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
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsPazienteCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Controlla se l'età del paziente corrisponde a quella fornita
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            //verifichiamo che lo username e l'età siano quelli corretti.
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(paziente.getUsername()) && Integer.parseInt(colonne[ConstantsPazienteCsv.INDICE_ANNI]) == paziente.getAnni()) {
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
            righeCSV = Files.readAllLines(Paths.get(ConstantsPazienteCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca l'username dello psicologo associato al paziente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            //verifichiamo che lo username del paziente sia quello cercato
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(paziente.getUsername())) {
                usernamePsicologo = colonne[ConstantsPazienteCsv.INDICE_PSICOLOGO_USERNAME]; // Ottieni l'username dello psicologo
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
            file = Files.readAllLines(Paths.get(ConstantsPazienteCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder recordAggiornato = new StringBuilder();

        // Aggiorna il nome utente dello psicologo nel CSV
        boolean recordUpdated = false;
        for (String riga : file) {
            String[] colonne = riga.split(",");

            // Controlla che il nome utente del paziente sia quello corretto
            if (colonne[ConstantsPazienteCsv.INDICE_PAZIENTE_USERNAME].equals(appuntamento.getPaziente().getUsername())) {
                // Aggiorna il nome utente dello psicologo (supponiamo che sia nella seconda colonna)
                colonne[ConstantsPazienteCsv.INDICE_PSICOLOGO_USERNAME] = appuntamento.getPsicologo().getUsername(); // Imposta il nome utente dello psicologo
                recordUpdated = true;
            }

            // Ricostruisci la riga e aggiungila al contenuto aggiornato
            recordAggiornato.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        if (!recordUpdated) {
            throw new DAOException("Paziente con username " + appuntamento.getPaziente().getUsername() + " non trovato nel file CSV.");
        }

        // Scrivi il contenuto aggiornato nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ConstantsPazienteCsv.FILE_PATH))) {
            writer.write(recordAggiornato.toString());
        } catch (IOException e) {
            throw new DAOException(ConstantsPazienteCsv.ERRORE_SCRITTURA +  " " + e.getMessage());
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


