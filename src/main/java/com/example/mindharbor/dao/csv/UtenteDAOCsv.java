package com.example.mindharbor.dao.csv;

import com.example.mindharbor.dao.UtenteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UtenteDAOCsv implements UtenteDAO {
    protected static final String FILE_PATH="MindHarborDB/csv/utente.csv";
    protected static final String ERRORE_LETTURA="Errore nella lettura del file CSV";

    @Override
    public Utente trovaUtente(Utente credenzialiUtenteLogin) throws DAOException {
        Utente utente = null;

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Controlla le credenziali dell'utente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            // Supponiamo che l'username sia nella colonna 0 e la password nella colonna 1
            if (colonne[0].equals(credenzialiUtenteLogin.getUsername()) &&
                    colonne[1].equals(credenzialiUtenteLogin.getPassword())) {

                // Se le credenziali corrispondono, crea l'oggetto Utente
                if(Objects.equals(colonne[4], "Paziente")) {
                    utente = new Utente(colonne[0], colonne[2], colonne[3], UserType.PAZIENTE);
                } else {
                    utente = new Utente(colonne[0], colonne[2], colonne[3], UserType.PSICOLOGO);
                }

                break; // Esci dal ciclo una volta trovato
            }
        }

        return utente; // Restituisce l'oggetto utente o null se non trovato
    }

    @Override
    public Utente trovaNomeCognome(Utente utente) throws DAOException {
        Utente infoUtente = null;

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca il nome e cognome dell'utente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            //verifichiamo che lo username sia quello cercato
            if (colonne[0].equals(utente.getUsername())) {
                infoUtente = new Utente("", colonne[2], colonne[3], ""); // Crea l'oggetto Utente
                break; // Esci dal ciclo una volta trovato
            }
        }

        return infoUtente; // Restituisce l'oggetto Utente o null se non trovato
    }

    @Override
    public List<Psicologo> listaUtentiDiTipoPsicologo(String usernamePsicologo) throws DAOException {
        // Questo metodo viene utilizzato nella prenotazione dell'appuntamento, quando il paziente deve visualizzare la lista degli psicologi,
        // oppure, nel caso in cui lui abbia già uno psicologo, solo quest'ultimo.
        // Il metodo ci ritorna il nome, il cognome, lo username e il genere dello psicologo o degli psicologi.

        List<Psicologo> listaPsicologi = new ArrayList<>();

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca gli psicologi nel CSV
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            if (colonne[4].equals("Psicologo")) { // Controlla il ruolo
                if (usernamePsicologo != null && !colonne[0].equals(usernamePsicologo)) {
                    continue;
                } // Controlla se lo username corrisponde
                Psicologo psicologo = new Psicologo(colonne[0], colonne[2], colonne[3], colonne[5]); // Crea l'oggetto Psicologo
                listaPsicologi.add(psicologo); // Aggiungi alla lista
            }
        }

        return listaPsicologi; // Restituisci la lista di psicologi trovati
    }



    @Override
    public List<Appuntamento> richiestaAppuntamentiInfoPaziente(List<Appuntamento> richiesteAppuntamenti) throws DAOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String s= br.readLine();

            while ((line = s) != null) {
                String[] colonne = line.split(",");

                String username= colonne[0];
                String nome = colonne[2];
                String cognome = colonne[3];
                String genere = colonne[5];

                for(Appuntamento app: richiesteAppuntamenti) {
                    if(username.equals(app.getPaziente().getUsername())) {
                        app.getPaziente().setNome(nome);
                        app.getPaziente().setCognome(cognome);
                        app.getPaziente().setGenere(genere);

                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return richiesteAppuntamenti;
    }

    @Override
    public Utente trovaInfoUtente(Utente paziente) throws DAOException {
        Utente infoUtente = null;

        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca le informazioni dell'utente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            // Assumendo che l'username si trovi nella prima colonna (0)
            if (colonne[0].equals(paziente.getUsername())) {
                infoUtente = new Utente("", colonne[2], colonne[3], colonne[5]); // Nome, Cognome, Genere
                break; // Esci dal ciclo una volta trovata l'informazione
            }
        }

        return infoUtente;
    }

}
