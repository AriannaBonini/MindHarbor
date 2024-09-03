package com.example.mindharbor.dao.csv;

import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.mysql.PazienteDAOMySql;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.utilities.UtilitiesCSV;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppuntamentoDAOCsv implements AppuntamentoDAO {

    // in CSV non c'è il DBMS che controlla.

    protected static final String FILE_PATH = "MindHarborDB/csv/appuntamento.csv";
    protected static final String ERRORE_LETTURA = "Errore nella lettura del file CSV";
    protected static final String ERRORE_SCRITTURA = "Errore nella scrittura nel file CSV:";
    protected static final Integer INDICE_ID_APPUNTAMENTO = 0;
    protected static final Integer INDICE_DATA = 1;
    protected static final Integer INDICE_ORA = 2;
    protected static final Integer INDICE_USERNAME_PAZIENTE = 3;
    protected static final Integer INDICE_USERNAME_PSICOLOGO = 4;
    protected static final Integer INDICE_STATO_APPUNTAMENTO = 5;
    protected static final Integer INDICE_STATO_NOTIFICA_PSICOLOGO = 6;
    protected static final Integer INDICE_STATO_NOTIFICA_PAZIENTE = 7;

    /**
     * Recupera una lista di appuntamenti per un determinato paziente in base alla scheda selezionata ("IN PROGRAMMA" o "PASSATI").
     * <p>
     * Questo metodo utilizza un file CSV per ottenere gli appuntamenti associati allo username di un paziente specifico.
     * Filtra gli appuntamenti in base alla data corrente e alla scheda selezionata, restituendo solo quelli rilevanti.
     * </p>
     *
     * @param paziente        Un oggetto {@link Utente} che rappresenta il paziente di cui si vogliono recuperare gli appuntamenti.
     * @param selectedTabName Una stringa che indica la scheda selezionata ("IN PROGRAMMA" per appuntamenti futuri,
     *                        "PASSATI" per appuntamenti già avvenuti).
     * @return Una lista di oggetti {@link Appuntamento} che rappresentano gli appuntamenti del paziente in base ai criteri di ricerca.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV o l'elaborazione dei dati.
     */
    @Override
    public List<Appuntamento> trovaAppuntamentiPaziente(Utente paziente, String selectedTabName) throws DAOException {
        List<Appuntamento> appuntamentoPsicologoList;
        LocalDate dataCorrente = LocalDate.now();

        try {
            appuntamentoPsicologoList = leggiAppuntamentiDaCsv(paziente, selectedTabName, dataCorrente, false);
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentoPsicologoList;
    }

    /**
     * Recupera una lista di appuntamenti per un determinato psicologo in base alla scheda selezionata ("IN PROGRAMMA" o "PASSATI").
     * <p>
     * Questo metodo utilizza un file CSV per ottenere gli appuntamenti associati allo username di uno psicologo specifico.
     * Filtra gli appuntamenti in base alla data corrente e alla scheda selezionata, restituendo solo quelli che sono rilevanti.
     * </p>
     *
     * @param psicologo      Un oggetto {@link Utente} che rappresenta lo psicologo di cui si vogliono recuperare gli appuntamenti.
     * @param selectedTabName Una stringa che indica la scheda selezionata ("IN PROGRAMMA" per appuntamenti futuri,
     *                        "PASSATI" per appuntamenti già avvenuti).
     * @return Una lista di oggetti {@link Appuntamento} che rappresentano gli appuntamenti dello psicologo in base ai criteri di ricerca.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV o l'elaborazione dei dati.
     */
    @Override
    public List<Appuntamento> trovaAppuntamentiPsicologo(Utente psicologo, String selectedTabName) throws DAOException {
        List<Appuntamento> appuntamentoPsicologoList;
        LocalDate dataCorrente = LocalDate.now();

        try {
            appuntamentoPsicologoList = leggiAppuntamentiDaCsv(psicologo, selectedTabName, dataCorrente, true);
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentoPsicologoList;
    }

    /**
     * Recupera una lista di appuntamenti dal file CSV in base all'utente e al tipo di visualizzazione richiesto.
     * <p>
     * Questo metodo è utilizzato sia per recuperare gli appuntamenti di uno psicologo che di un paziente, a seconda del parametro booleano `tipo`.
     * Se `tipo` è `true`, il metodo filtra gli appuntamenti per uno psicologo specifico e include le informazioni sui pazienti.
     * Se `tipo` è `false`, il metodo filtra gli appuntamenti per un paziente specifico.
     * </p>
     *
     * @param user            Un oggetto {@link Utente} che rappresenta l'utente (psicologo o paziente) di cui si vogliono recuperare gli appuntamenti.
     * @param selectedTabName Una stringa che indica la scheda selezionata ("IN PROGRAMMA" per appuntamenti futuri,
     *                        "PASSATI" per appuntamenti già avvenuti).
     * @param dataCorrente    La data corrente utilizzata per filtrare gli appuntamenti in base alla scheda selezionata.
     * @param tipo            Un booleano che determina se l'utente è uno psicologo (true) o un paziente (false).
     * @return Una lista di oggetti {@link Appuntamento} che rappresentano gli appuntamenti dell'utente in base ai criteri di ricerca.
     * @throws IOException    Se si verifica un errore durante la lettura del file CSV.
     * @throws DAOException   Se si verifica un errore specifico durante l'elaborazione dei dati dal file CSV.
     */
    private List<Appuntamento> leggiAppuntamentiDaCsv(Utente user, String selectedTabName, LocalDate dataCorrente, boolean tipo) throws IOException, DAOException {
        UtenteDAOCsv utenteDAOCsv = new UtenteDAOCsv();
        List<Appuntamento> appuntamenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            UtilitiesCSV.scartaIntestazione(br);
            String line;

            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(",");
                if (isUserMatch(user, colonne, tipo)) {
                    LocalDate dataAppuntamento = LocalDate.parse(colonne[INDICE_DATA]);
                    if (isAppuntamentoValido(dataAppuntamento, dataCorrente, selectedTabName)) {
                        Appuntamento appuntamento = creaAppuntamento(colonne, tipo, utenteDAOCsv);
                        appuntamenti.add(appuntamento);
                    }
                }
            }
        }
        return appuntamenti;
    }

    /**
     * Verifica se l'utente specificato corrisponde al paziente o allo psicologo nel record CSV fornito.
     * <p>
     * Questo metodo controlla se l'username dell'utente corrisponde a quello del paziente o dello psicologo
     * all'interno del record CSV, in base al tipo di utente specificato. Se il parametro `tipo` è `true`,
     * il metodo confronta l'username dello psicologo; se `false`, confronta l'username del paziente.
     * </p>
     *
     * @param user   L'oggetto {@link Utente} di cui si vuole verificare la corrispondenza dell'username.
     * @param colonne Un array di stringhe contenente i valori delle colonne di un record CSV.
     * @param tipo   Un booleano che indica il tipo di utente. Se `true`, confronta lo psicologo; se `false`, confronta il paziente.
     * @return `true` se l'username dell'utente corrisponde a quello specificato nel CSV, `false` altrimenti.
     */
    private boolean isUserMatch(Utente user, String[] colonne, boolean tipo) {
        String usernamePaziente = colonne[INDICE_USERNAME_PAZIENTE];
        String usernamePsicologo = colonne[INDICE_USERNAME_PSICOLOGO];
        return tipo ? usernamePsicologo.equals(user.getUsername()) : usernamePaziente.equals(user.getUsername());
    }

    /**
     * Verifica se un appuntamento è valido in base alla data corrente e alla scheda selezionata.
     * <p>
     * Questo metodo determina se un appuntamento deve essere incluso nell'elenco dei risultati
     * in base alla scheda selezionata dall'utente. Se la scheda selezionata è "IN PROGRAMMA", l'appuntamento
     * è considerato valido solo se la sua data è successiva alla data corrente. Se la scheda selezionata è "PASSATI",
     * l'appuntamento è considerato valido solo se la sua data è antecedente alla data corrente.
     * </p>
     *
     * @param dataAppuntamento La data dell'appuntamento da verificare.
     * @param dataCorrente     La data corrente rispetto alla quale confrontare l'appuntamento.
     * @param selectedTabName  Il nome della scheda selezionata ("IN PROGRAMMA" o "PASSATI").
     * @return `true` se l'appuntamento è valido in base ai criteri di verifica; `false` altrimenti.
     */
    private boolean isAppuntamentoValido(LocalDate dataAppuntamento, LocalDate dataCorrente, String selectedTabName) {
        return (selectedTabName.equals("IN PROGRAMMA") && dataAppuntamento.isAfter(dataCorrente)) ||
                (selectedTabName.equals("PASSATI") && dataAppuntamento.isBefore(dataCorrente));
    }

    /**
     * Crea un oggetto {@link Appuntamento} basato sulle informazioni fornite nelle colonne del file CSV.
     * <p>
     * Questo metodo costruisce un oggetto {@link Appuntamento} utilizzando i dati passati nel parametro `colonne`.
     * Se l'utente è uno psicologo (indicato dal parametro `tipo`), il metodo recupera ulteriori informazioni
     * sul paziente associato all'appuntamento e include queste informazioni nell'oggetto {@link Appuntamento}.
     * Se l'utente è un paziente, viene creato un {@link Appuntamento} con informazioni semplificate.
     * </p>
     *
     * @param colonne     Un array di stringhe che rappresenta una riga del file CSV, con ogni elemento corrispondente a una colonna.
     * @param tipo        Un valore booleano che indica se l'utente è uno psicologo (`true`) o un paziente (`false`).
     * @param utenteDAOCsv Un'istanza di {@link UtenteDAOCsv} utilizzata per recuperare le informazioni aggiuntive sul paziente.
     * @return Un oggetto {@link Appuntamento} creato utilizzando i dati forniti.
     * @throws DAOException Se si verifica un errore durante il recupero delle informazioni aggiuntive sul paziente.
     */
    private Appuntamento creaAppuntamento(String[] colonne, boolean tipo, UtenteDAOCsv utenteDAOCsv) throws DAOException {
        String data = colonne[INDICE_DATA];
        String ora = colonne[INDICE_ORA];
        if (tipo) {
            String usernamePaziente = colonne[INDICE_USERNAME_PAZIENTE];
            Utente paziente = utenteDAOCsv.trovaNomeCognome(new Utente(usernamePaziente));
            return new Appuntamento(data, ora, new Paziente(usernamePaziente, paziente.getNome(), paziente.getCognome()));
        } else {
            return new Appuntamento(data, ora);
        }
    }

    @Override
    public void insertRichiestaAppuntamento(Appuntamento appuntamento) throws DAOException {
        // bisogna aggiungere i controlli se quella richiesta puo essere aggiunta.

        // Apre il file CSV in modalità append
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Crea una stringa per il record da scrivere nel CSV
            String nuovoRecord = String.join(",",
                    String.valueOf(calcolaIDAppuntamento()),
                    appuntamento.getData(),
                    appuntamento.getOra(),
                    appuntamento.getPaziente().getUsername(),
                    appuntamento.getPsicologo().getUsername(),
                    "0",
                    "1",
                    "0"
            );

            // Scrive il record nel file CSV
            bw.write(nuovoRecord);
            bw.newLine(); // Aggiunge una nuova riga
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Calcola il prossimo ID disponibile per un nuovo appuntamento, basato sugli ID esistenti nel file CSV.
     * <p>
     * Questo metodo legge tutte le righe del file CSV contenente gli appuntamenti e trova il valore massimo
     * di ID presente. Restituisce il prossimo ID disponibile, che è il valore massimo trovato incrementato di uno.
     * </p>
     *
     * @return Un intero che rappresenta il prossimo ID disponibile per un nuovo appuntamento.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    private Integer calcolaIDAppuntamento() throws DAOException {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(","); // Assumiamo che il CSV sia separato da virgole
                int id = Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]);
                if (id > maxId) {
                    maxId = id; // Aggiorna il massimo ID trovato
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return maxId + 1;
    }

    @Override
    public Integer getNumRicAppDaNotificare(Utente utente) throws DAOException {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            UtilitiesCSV.scartaIntestazione(br);
            String line;
            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(",");

                // Verifica se l'utente è un paziente e se l'username corrisponde
                if (utente.getUserType().equals(UserType.PAZIENTE) && colonne[INDICE_USERNAME_PAZIENTE].equals(utente.getUsername()) && colonne[INDICE_STATO_NOTIFICA_PAZIENTE].equals("1")) {
                    count++;
                }

                // Verifica Se l'utente è uno psicologo, controlla se l'username corrisponde
                if (utente.getUserType().equals(UserType.PSICOLOGO) && colonne[INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername()) && colonne[INDICE_STATO_NOTIFICA_PSICOLOGO].equals("1")) {
                    count++;
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return count;
    }

    /**
     * Recupera tutte le richieste di appuntamento per uno psicologo specifico che non sono state ancora accettate.
     * <p>
     * Questo metodo scorre le righe di un file CSV contenente gli appuntamenti e cerca le richieste di appuntamento
     * (ovvero appuntamenti con stato pari a 0) per uno psicologo specifico, identificato dall'username passato nel parametro.
     * Le richieste di appuntamento trovate vengono quindi arricchite con ulteriori informazioni sui pazienti
     * chiamando il metodo `richiestaAppuntamentiInfoPaziente`.
     * </p>
     *
     * @param utente Un oggetto {@link Utente} che rappresenta lo psicologo per il quale si desiderano trovare le richieste di appuntamento.
     * @return Una lista di oggetti {@link Appuntamento} che rappresentano le richieste di appuntamento trovate per lo psicologo specificato.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    @Override
    public List<Appuntamento> trovaRichiesteAppuntamento(Utente utente) throws DAOException {
        List<Appuntamento> richiesteAppuntamento = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(",");
                // Controlla se l'username dello psicologo corrisponde e se lo stato appuntamento è pari a 0
                if (colonne[INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername()) && Integer.parseInt(colonne[INDICE_STATO_APPUNTAMENTO]) == 0) {
                    Appuntamento richiesta = new Appuntamento(
                            Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]),
                            new Paziente(colonne[INDICE_USERNAME_PAZIENTE]),
                            Integer.valueOf(colonne[INDICE_STATO_NOTIFICA_PSICOLOGO])
                    );
                    richiesteAppuntamento.add(richiesta);
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        richiesteAppuntamento = new UtenteDAOCsv().richiestaAppuntamentiInfoPaziente(richiesteAppuntamento);
        return richiesteAppuntamento;
    }

    /**
     * Aggiorna lo stato di notifica di uno specifico appuntamento, impostando lo stato di notifica dello psicologo a "0".
     * <p>
     * Questo metodo scorre tutte le righe di un file CSV contenente appuntamenti e trova l'appuntamento corrispondente
     * all'ID specificato nell'oggetto {@link Appuntamento} passato come parametro. Una volta trovato, imposta lo stato
     * di notifica dello psicologo a "0" per quell'appuntamento. Dopo aver aggiornato lo stato, il metodo riscrive tutte
     * le righe del file CSV con le modifiche apportate.
     * </p>
     *
     * @param richiestaAppuntamento Un oggetto {@link Appuntamento} che rappresenta l'appuntamento di cui si desidera aggiornare lo stato di notifica.
     * @throws DAOException Se si verifica un errore durante la lettura o la scrittura del file CSV.
     */
    @Override
    public void updateStatoNotifica(Appuntamento richiestaAppuntamento) throws DAOException {
        StringBuilder recordAggiornato = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            // Scarta l'intestazione
            UtilitiesCSV.scartaIntestazione(br);
            String line;
            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(",");
                if (Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]) == richiestaAppuntamento.getIdAppuntamento()) {
                    colonne[INDICE_STATO_NOTIFICA_PSICOLOGO] = "0"; // Imposta stato notifica psicologo a 0
                }
                recordAggiornato.append(String.join(",", colonne)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }
        // Scrittura delle righe aggiornate nel file CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(recordAggiornato.toString());
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public Appuntamento getInfoRichiesta(Appuntamento richiestaAppuntamento) throws DAOException {
        Appuntamento richiesta = null;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            UtilitiesCSV.scartaIntestazione(br);
            String line;
            while ((line = br.readLine()) != null) {
                String[] colonne = line.split(",");

                // Verifica se l'ID corrisponde
                if (colonne[INDICE_ID_APPUNTAMENTO].equals(String.valueOf(richiestaAppuntamento.getIdAppuntamento()))) {
                    String data = colonne[INDICE_DATA];
                    String ora = colonne[INDICE_ORA];

                    richiesta = new Appuntamento(data, ora);
                    break; // Esci dal ciclo una volta trovata la richiesta
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return richiesta;
    }

    /**
     * Aggiorna lo stato di una richiesta di appuntamento nel file CSV e aggiorna le notifiche
     * del paziente e dello psicologo associati a tale appuntamento.
     * <p>
     * Questo metodo esegue le seguenti operazioni:
     * <ul>
     *     <li>Legge tutte le righe del file CSV contenente gli appuntamenti.</li>
     *     <li>Verifica quale riga del CSV corrisponde all'appuntamento da aggiornare, confrontando l'ID dell'appuntamento.</li>
     *     <li>Se l'ID corrisponde, aggiorna lo stato dell'appuntamento per indicare che è stato accettato
     *         e imposta lo stato di notifica del paziente a "1" (notificato).</li>
     *     <li>Riscrive tutte le righe aggiornate nel file CSV.</li>
     *     <li>Chiama un metodo per associare lo psicologo al paziente.</li>
     * </ul>
     * </p>
     *
     * @param appuntamento L'oggetto {@link Appuntamento} che contiene le informazioni dell'appuntamento da aggiornare.
     * @throws DAOException Se si verifica un errore durante la lettura o la scrittura del file CSV.
     */
    @Override
    public void updateRichiesta(Appuntamento appuntamento) throws DAOException {
        List<String> righe = UtilitiesCSV.leggiRigheDaCsv(FILE_PATH);
        List<String> righeAggiornate = new ArrayList<>();

        // Aggiornamento dello stato dell'appuntamento
        for (String riga : righe) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            // Controlla se la riga corrisponde all'appuntamento che vogliamo aggiornare
            if (Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]) == appuntamento.getIdAppuntamento()) {
                colonne[INDICE_STATO_APPUNTAMENTO] = "1"; // Stato appuntamento accettato
                colonne[INDICE_STATO_NOTIFICA_PAZIENTE] = "1"; // Notifica paziente aggiornata
            }

            // Aggiungi la riga (aggiornata o meno) alla lista
            righeAggiornate.add(String.join(",", colonne));
        }

        // Scrittura delle righe aggiornate nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(FILE_PATH, righeAggiornate);

        // Chiamata al metodo per aggiungere lo psicologo al paziente
        new PazienteDAOMySql().aggiungiPsicologoAlPaziente(appuntamento);
    }

    @Override
    public void eliminaRichiesteDiAppuntamentoPerAltriPsicologi(Appuntamento appuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righe;
        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder nuovoContenuto = new StringBuilder();

        // Filtra le righe che devono essere mantenute
        for (String riga : righe) {
            String[] colonne = riga.split(",");

            // Controlla se il nome utente del paziente corrisponde e se il nome utente dello psicologo è diverso
            if (colonne[INDICE_USERNAME_PAZIENTE].equals(appuntamento.getPaziente().getUsername()) && !colonne[INDICE_USERNAME_PSICOLOGO].equals(appuntamento.getPsicologo().getUsername())) {
                continue;
            }

            nuovoContenuto.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        // Scrivi il contenuto aggiornato nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(nuovoContenuto.toString());
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public void eliminaRichiesta(Appuntamento appuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righe;
        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder nuovoContenuto = new StringBuilder();

        // Filtra le righe che devono essere mantenute
        for (String riga : righe) {
            String[] colonne = riga.split(",");

            // Se l'ID dell'appuntamento corrisponde, salta questa riga
            if (Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]) == appuntamento.getIdAppuntamento()) {
                continue; // Salta questa riga
            }

            // Mantieni questa riga
            nuovoContenuto.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        // Scrivi il contenuto aggiornato nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(nuovoContenuto.toString());
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public boolean getDisp(Integer idAppuntamento, Utente utente) throws DAOException {

        // Leggi tutte le righe del file CSV
        List<String> righe;
        try {
            righe = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Verifica la disponibilità dell'appuntamento
        for (String riga : righe) {
            String[] colonne = riga.split(",");

            // Controlla se l'appuntamento corrisponde e se è associato all'utente
            if (Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]) == idAppuntamento && colonne[INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername())) {

                // Ora verifichiamo se esiste un conflitto
                for (String innerRiga : righe) {
                    String[] innerColonne = innerRiga.split(",");

                    // Controlla se l'appuntamento è confermato (stato = 1) e ha la stessa data, ora e psicologo
                    if (Integer.parseInt(innerColonne[INDICE_STATO_APPUNTAMENTO]) == 1 &&
                            innerColonne[INDICE_DATA].equals(colonne[INDICE_DATA]) && // Data
                            innerColonne[INDICE_ORA].equals(colonne[INDICE_ORA]) && // Ora
                            innerColonne[INDICE_USERNAME_PSICOLOGO].equals(colonne[INDICE_USERNAME_PSICOLOGO])) { // Psicologo
                        return false; // Appuntamento non disponibile a causa di conflitto
                    }
                }
                // Se siamo arrivati qui, significa che non ci sono conflitti
                return true; // Appuntamento disponibile
            }
        }
        // Se non abbiamo trovato l'appuntamento, restituendo false
        return false;
    }

    @Override
    public void aggiornaStatoNotificaPaziente(Utente utente) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> file;
        try {
            file = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // StringBuilder per costruire il nuovo contenuto del file
        StringBuilder nuovoContenuto = new StringBuilder();

        // Aggiorna lo stato di notifica nel CSV
        for (String riga : file) {
            String[] colonne = riga.split(",");


            // Se il nome utente del paziente corrisponde e lo stato di notifica è 1, aggiorna lo stato a 0
            if (colonne[INDICE_USERNAME_PAZIENTE].equals(utente.getUsername()) && colonne[INDICE_STATO_NOTIFICA_PAZIENTE].equals("1")) {
                colonne[INDICE_STATO_NOTIFICA_PAZIENTE] = "0"; // Aggiorna lo stato di notifica del paziente a 0
            }

            // Ricostruisci la riga e aggiungila al contenuto aggiornato
            nuovoContenuto.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        // Scrivi il contenuto aggiornato nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(nuovoContenuto.toString());
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }
}
