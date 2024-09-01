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

/**
 * Implementazione dell'interfaccia {@link UtenteDAO} che utilizza un file CSV come fonte di dati per le operazioni di accesso utente.
 * <p>
 * Questa classe fornisce metodi per eseguire operazioni CRUD (Create, Read, Update, Delete) su utenti memorizzati in un file CSV.
 * I metodi inclusi permettono di verificare le credenziali di accesso degli utenti, cercare informazioni come nome e cognome,
 * e restituire una lista di utenti di tipo psicologo. Inoltre, consente di arricchire le informazioni dei pazienti associate
 * agli appuntamenti con dati aggiuntivi come nome, cognome e genere.
 * </p>
 * <p>
 * Il percorso del file CSV è definito dalla costante `FILE_PATH`. Gli errori nella lettura del file sono gestiti tramite
 * l'eccezione personalizzata {@link DAOException}.
 * </p>
 */
public class UtenteDAOCsv implements UtenteDAO {
    protected static final String FILE_PATH="MindHarborDB/csv/utente.csv";
    protected static final String ERRORE_LETTURA="Errore nella lettura del file CSV";

    /**
     * Verifica le credenziali di accesso di un utente contro i dati presenti nel file CSV e,
     * se le credenziali corrispondono, restituisce un oggetto {@link Utente} con le informazioni dell'utente.
     * <p>
     * Questo metodo legge tutte le righe del file CSV specificato, che contiene informazioni sugli utenti.
     * Confronta le credenziali di accesso fornite (`credenzialiUtenteLogin`) con quelle memorizzate nel file.
     * Se viene trovata una corrispondenza tra l'username e la password, viene creato e restituito un oggetto
     * {@link Utente} contenente l'username, il nome, il cognome e il tipo di utente (paziente o psicologo).
     * Se non viene trovata alcuna corrispondenza, il metodo restituisce `null`.
     * </p>
     *
     * @param credenzialiUtenteLogin Un oggetto {@link Utente} contenente l'username e la password dell'utente che sta cercando di accedere.
     * @return Un oggetto {@link Utente} con le informazioni dell'utente se le credenziali corrispondono; `null` se non viene trovata alcuna corrispondenza.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    @Override
    public Utente trovaUtente(Utente credenzialiUtenteLogin) throws DAOException {
        Utente utente = null;
        List<String> righe;

        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }
        for (String riga : righe) {
            String[] colonne = riga.split(",");
            // Supponiamo che l'username sia nella colonna 0 e la password nella colonna 1
            if (colonne[0].equals(credenzialiUtenteLogin.getUsername()) &&
                    colonne[1].equals(credenzialiUtenteLogin.getPassword())) {
                if(Objects.equals(colonne[4], "Paziente")) {
                    utente = new Utente(colonne[0], colonne[2], colonne[3], UserType.PAZIENTE);
                } else {
                    utente = new Utente(colonne[0], colonne[2], colonne[3], UserType.PSICOLOGO);
                }
                break;
            }
        }
        return utente;
    }

    /**
     * Cerca e restituisce le informazioni di nome e cognome di un utente specificato, leggendo da un file CSV.
     * <p>
     * Questo metodo scorre tutte le righe del file CSV specificato e cerca l'utente corrispondente all'username
     * fornito nell'oggetto {@link Utente} passato come parametro. Se viene trovato un utente con lo stesso username,
     * viene creato un nuovo oggetto {@link Utente} contenente il nome e il cognome estratti dal file CSV.
     * </p>
     *
     * @param utente Un oggetto {@link Utente} contenente l'username dell'utente di cui si vogliono recuperare nome e cognome.
     * @return Un oggetto {@link Utente} contenente il nome e il cognome se l'utente è trovato; `null` se l'utente non è trovato.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    @Override
    public Utente trovaNomeCognome(Utente utente) throws DAOException {
        Utente infoUtente = null;
        List<String> righe;
        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }
        for (String riga : righe) {
            String[] colonne = riga.split(",");
            if (colonne[0].equals(utente.getUsername())) {
                infoUtente = new Utente("", colonne[2], colonne[3], ""); // Crea l'oggetto Utente
                break;
            }
        }
        return infoUtente;
    }


    /**
     * Restituisce una lista di oggetti {@link Psicologo} che rappresentano gli utenti di tipo psicologo.
     * <p>
     * Questo metodo viene utilizzato principalmente durante la prenotazione di un appuntamento, quando un paziente
     * deve visualizzare la lista completa degli psicologi disponibili o, se ha già uno psicologo assegnato,
     * visualizzare solo le informazioni di quest'ultimo. Il metodo legge i dati da un file CSV e restituisce una lista
     * di psicologi che includono il nome, il cognome, lo username e il genere.
     * </p>
     *
     * @param usernamePsicologo Lo username di uno psicologo specifico. Se è non null e corrisponde ad uno degli psicologi nel file,
     *                          verranno restituite solo le informazioni di quello psicologo. Se è null, verranno restituiti
     *                          tutti gli psicologi.
     * @return Una lista di oggetti {@link Psicologo} che rappresentano gli psicologi trovati nel file CSV. Se viene specificato
     *         uno username, la lista conterrà al massimo un elemento.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    @Override
    public List<Psicologo> listaUtentiDiTipoPsicologo(String usernamePsicologo) throws DAOException {
        List<Psicologo> listaPsicologi = new ArrayList<>();
        List<String> righe;

        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }
        for (String riga : righe) {
            String[] colonne = riga.split(",");
            if (colonne[4].equals("Psicologo")) { // Controlla il ruolo
                if (usernamePsicologo != null && !colonne[0].equals(usernamePsicologo)) {
                    continue;
                }
                Psicologo psicologo = new Psicologo(colonne[0], colonne[2], colonne[3], colonne[5]); // Crea l'oggetto Psicologo
                listaPsicologi.add(psicologo); // Aggiungi alla lista
            }
        }
        return listaPsicologi;
    }

    /**
     * Arricchisce le informazioni dei pazienti associati agli appuntamenti forniti.
     * <p>
     * Questo metodo prende una lista di appuntamenti (`richiesteAppuntamenti`) e arricchisce
     * le informazioni dei pazienti associati a ciascun appuntamento leggendo i dati da un file CSV.
     * Per ogni appuntamento nella lista, il metodo verifica se l'username del paziente
     * corrisponde a un record nel file CSV. Se viene trovata una corrispondenza, le informazioni
     * del paziente come il nome, il cognome e il genere vengono aggiornate con i valori
     * corrispondenti trovati nel CSV.
     * </p>
     *
     * @param richiesteAppuntamenti Una lista di oggetti {@link Appuntamento} contenente appuntamenti
     *                              per cui si desidera aggiornare le informazioni dei pazienti.
     * @return La lista di appuntamenti con le informazioni dei pazienti aggiornate.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    @Override
    public List<Appuntamento> richiestaAppuntamentiInfoPaziente(List<Appuntamento> richiesteAppuntamenti) throws DAOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
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

    /**
     * Restituisce un oggetto {@link Utente} contenente informazioni specifiche (nome, cognome e genere) di un paziente.
     * <p>
     * Questo metodo cerca nel file CSV un record corrispondente allo username del paziente passato come parametro.
     * Se trovato, restituisce un oggetto {@link Utente} contenente il nome, cognome e genere del paziente.
     * </p>
     *
     * @param paziente Un oggetto {@link Utente} contenente lo username del paziente di cui si vogliono ottenere le informazioni.
     * @return Un oggetto {@link Utente} con nome, cognome e genere del paziente. Restituisce null se non viene trovato nessun paziente con lo username specificato.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    @Override
    public Utente trovaInfoUtente(Utente paziente) throws DAOException {
        Utente infoUtente = null;
        List<String> righe;

        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }
        for (String riga : righe) {
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