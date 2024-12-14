package com.example.mindharbor.dao.csv.appuntamento_dao_csv;

import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.csv.utente_dao_csv.UtenteDAOCsv;
import com.example.mindharbor.dao.mysql.PazienteDAOMySql;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.utilities.UtilitiesCSV;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AppuntamentoDAOCsv implements AppuntamentoDAO {

    // in CSV non c'è il DBMS che controlla.

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

        appuntamentoPsicologoList = leggiAppuntamentiDaCsv(paziente, selectedTabName, dataCorrente, false);
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

        appuntamentoPsicologoList = leggiAppuntamentiDaCsv(psicologo, selectedTabName, dataCorrente, true);
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
     * @param utente            Un oggetto {@link Utente} che rappresenta l'utente (psicologo o paziente) di cui si vogliono recuperare gli appuntamenti.
     * @param tabSelezionato Una stringa che indica la scheda selezionata ("IN PROGRAMMA" per appuntamenti futuri,
     *                        "PASSATI" per appuntamenti già avvenuti).
     * @param dataCorrente    La data corrente utilizzata per filtrare gli appuntamenti in base alla scheda selezionata.
     * @param tipo            Un booleano che determina se l'utente è uno psicologo (true) o un paziente (false).
     * @return Una lista di oggetti {@link Appuntamento} che rappresentano gli appuntamenti dell'utente in base ai criteri di ricerca.
     * @throws DAOException   Se si verifica un errore specifico durante l'elaborazione dei dati dal file CSV.
     */
    private List<Appuntamento> leggiAppuntamentiDaCsv(Utente utente, String tabSelezionato, LocalDate dataCorrente, boolean tipo) throws DAOException {
        UtenteDAOCsv utenteDAOCsv = new UtenteDAOCsv();
        List<Appuntamento> appuntamenti = new ArrayList<>();

        // Utilizziamo il metodo leggiRigheDaCsv per leggere tutte le righe del file CSV
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        // Itera attraverso le righe lette
        for (String[] colonne : righe) {
            // Verifica se l'utente corrisponde con i dati nella riga, utilizzando il tipo come filtro
            if (tipoUtente(utente, colonne, tipo)) {
                // Parsing della data dell'appuntamento
                LocalDate dataAppuntamento = LocalDate.parse(colonne[ConstantsAppuntamentoCsv.INDICE_DATA]);

                // Verifica se l'appuntamento è valido
                if (appuntamentoValido(dataAppuntamento, dataCorrente, tabSelezionato)) {
                    // Crea l'appuntamento e aggiungilo alla lista
                    Appuntamento appuntamento = creaAppuntamento(colonne, tipo, utenteDAOCsv);
                    appuntamenti.add(appuntamento);
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
     * @param utente   L'oggetto {@link Utente} di cui si vuole verificare la corrispondenza dell'username.
     * @param colonne Un array di stringhe contenente i valori delle colonne di un record CSV.
     * @param tipo   Un booleano che indica il tipo di utente. Se `true`, confronta lo psicologo; se `false`, confronta il paziente.
     * @return `true` se l'username dell'utente corrisponde a quello specificato nel CSV, `false` altrimenti.
     */
    private boolean tipoUtente(Utente utente, String[] colonne, boolean tipo) {
        String usernamePaziente = colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PAZIENTE];
        String usernamePsicologo = colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO];
        return tipo ? usernamePsicologo.equals(utente.getUsername()) : usernamePaziente.equals(utente.getUsername());
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
    private boolean appuntamentoValido(LocalDate dataAppuntamento, LocalDate dataCorrente, String selectedTabName) {
        return (selectedTabName.equals(UtilitiesCSV.IN_PROGRAMMA) && dataAppuntamento.isAfter(dataCorrente)) ||
                (selectedTabName.equals(UtilitiesCSV.PASSATI) && dataAppuntamento.isBefore(dataCorrente));
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
        String data = colonne[ConstantsAppuntamentoCsv.INDICE_DATA];
        String ora = colonne[ConstantsAppuntamentoCsv.INDICE_ORA];
        if (tipo) {
            String usernamePaziente = colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PAZIENTE];
            Utente paziente = utenteDAOCsv.trovaNomeCognome(new Utente(usernamePaziente));
            return new Appuntamento(data, ora, new Paziente(usernamePaziente, paziente.getNome(), paziente.getCognome()));
        } else {
            return new Appuntamento(data, ora);
        }
    }

    @Override
    public void insertRichiestaAppuntamento(Appuntamento appuntamento) throws DAOException {
        // bisogna aggiungere i controlli se quella richiesta puo essere aggiunta.

        // Legge tutte le righe del CSV
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        // Crea una nuova riga per l'appuntamento
        String[] nuovoRecord = new String[] {
                String.valueOf(calcolaIDAppuntamento()), // Calcolo ID unico per l'appuntamento
                appuntamento.getData(),                  // Data dell'appuntamento
                appuntamento.getOra(),                   // Ora dell'appuntamento
                appuntamento.getPaziente().getUsername(),// Username del paziente
                appuntamento.getPsicologo().getUsername(),// Username dello psicologo
                ConstantsAppuntamentoCsv.RICHIESTA_IN_ATTESA,       // Stato iniziale
                ConstantsAppuntamentoCsv.NOTIFICA_PSICOLOGO_ATTIVA,  // Notifica attiva per lo psicologo
                ConstantsAppuntamentoCsv.NOTIFICA_PAZIENTE_NON_ATTIVA // Notifica non attiva per il paziente
        };

        // Aggiunge il nuovo record alla lista delle righe
        righe.add(nuovoRecord);

        // Scrive tutte le righe aggiornate nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(ConstantsAppuntamentoCsv.FILE_PATH, righe);
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
        try {
            List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);
            for (String[] riga : righe) {
                int id = Integer.parseInt(riga[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO]);
                if (id > maxId) {
                    maxId = id; // Aggiorna il massimo ID trovato
                }
            }
        } catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return maxId + 1;
    }

    @Override
    public Integer getNumRicAppDaNotificare(Utente utente) throws DAOException {
        int count = 0;
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);
        for (String[] colonne : righe) {
            if (utente.getUserType().equals(UserType.PAZIENTE) && colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PAZIENTE].equals(utente.getUsername()) && colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PAZIENTE].equals("1")) {
                count++;
            }
            if (utente.getUserType().equals(UserType.PSICOLOGO) && colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername()) && colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO].equals("1")) {
                count++;
            }
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

            List<String[]> risultati = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

            for(String[] colonne: risultati) {
                // Controlla se l'username dello psicologo corrisponde e se lo stato appuntamento è pari a 0
                if (colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername()) && Integer.parseInt(colonne[ConstantsAppuntamentoCsv.INDICE_STATO_APPUNTAMENTO]) == 0) {
                    Appuntamento richiesta = new Appuntamento(
                            Integer.parseInt(colonne[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO]),
                            new Paziente(colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PAZIENTE]),
                            Integer.valueOf(colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO])
                    );
                    richiesteAppuntamento.add(richiesta);
                }
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
        List<String[]> risultati = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);
        List<String[]> recordAggiornati = new ArrayList<>();

        for(String[] colonne: risultati) {
            if (Integer.parseInt(colonne[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO]) == richiestaAppuntamento.getIdAppuntamento()) {
                colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PSICOLOGO] = "0"; // bisogna rimuovere la stringa "0" e usare una costante
            }
            recordAggiornati.add(colonne);
        }

        UtilitiesCSV.scriviRigheAggiornate(ConstantsAppuntamentoCsv.FILE_PATH, recordAggiornati);
    }

    @Override
    public Appuntamento getInfoRichiesta(Appuntamento richiestaAppuntamento) throws DAOException {
        Appuntamento richiesta = null;

        List<String[]> recordLetti = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        for(String[] colonne : recordLetti) {
            if(colonne[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO].equals(String.valueOf(richiestaAppuntamento.getIdAppuntamento()))) {
                String data = colonne[ConstantsAppuntamentoCsv.INDICE_DATA];
                String ora = colonne[ConstantsAppuntamentoCsv.INDICE_ORA];
                richiesta = new Appuntamento(data, ora);
                break;
            }
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
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);
        List<String[]> righeAggiornate = new ArrayList<>();

        // Aggiornamento dello stato dell'appuntamento
        for (String[] colonne : righe) {

            // Controlla se la riga corrisponde all'appuntamento che vogliamo aggiornare
            if (Integer.parseInt(colonne[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO]) == appuntamento.getIdAppuntamento()) {
                colonne[ConstantsAppuntamentoCsv.INDICE_STATO_APPUNTAMENTO] = "1"; // Stato appuntamento accettato
                colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PAZIENTE] = "1"; // Notifica paziente aggiornata
            }

            // Aggiungi la riga (aggiornata o meno) alla lista
            righeAggiornate.add(colonne);
        }

        // Scrittura delle righe aggiornate nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(ConstantsAppuntamentoCsv.FILE_PATH, righeAggiornate);

        // Chiamata al metodo per aggiungere lo psicologo al paziente
        new PazienteDAOMySql().aggiungiPsicologoAlPaziente(appuntamento);
    }

    @Override
    public void eliminaRichiesteDiAppuntamentoPerAltriPsicologi(Appuntamento appuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        // Lista per memorizzare le righe aggiornate
        List<String[]> righeAggiornate = righe.stream().filter(colonne ->
                !colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PAZIENTE].equals(appuntamento.getPaziente().getUsername())
                        || colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO].equals(appuntamento.getPsicologo().getUsername())
        ).toList();

        // Scrivi il contenuto aggiornato nel file
        UtilitiesCSV.scriviRigheAggiornate(ConstantsAppuntamentoCsv.FILE_PATH, righeAggiornate);
    }

    @Override
    public void eliminaRichiesta(Appuntamento appuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        // Usa lo streaming e il metodo filter per ottenere una lista di tutti gli appuntamenti eccetto quello da eliminare
        List<String[]> righeAggiornate = righe.stream().filter(colonne ->
                        Integer.parseInt(colonne[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO]) != appuntamento.getIdAppuntamento())
                .toList();  // Modifica qui

        // Scrivi il contenuto aggiornato nel file
        UtilitiesCSV.scriviRigheAggiornate(ConstantsAppuntamentoCsv.FILE_PATH, righeAggiornate);
    }

    @Override
    public boolean getDisp(Integer idAppuntamento, Utente utente) throws DAOException {

        // Leggi tutte le righe del file CSV
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        // Verifica la disponibilità dell'appuntamento
        for (String[] colonne : righe) {

            // Controlla se l'appuntamento corrisponde e se è associato all'utente
            if (Integer.parseInt(colonne[ConstantsAppuntamentoCsv.INDICE_ID_APPUNTAMENTO]) == idAppuntamento && colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername())) {

                // Ora verifichiamo se esiste un conflitto
                /*
                all'interno del blocco che verifica la disponibilità dell'appuntamento,
                abbiamo sostituito il ciclo for interno che verifica i conflitti con uno stream e metodo `anyMatch()`.
                Se `anyMatch()` restituisce `true`, significa che è stato trovato un conflitto e l'appuntamento non è disponibile.
                Se non viene trovato alcun conflitto, `anyMatch()` restituisce `false` e l'appuntamento è disponibile.
                 */
                boolean conflitto = righe.stream()
                        .anyMatch(innerColonne ->
                                Integer.parseInt(innerColonne[ConstantsAppuntamentoCsv.INDICE_STATO_APPUNTAMENTO]) == 1 &&
                                        innerColonne[ConstantsAppuntamentoCsv.INDICE_DATA].equals(colonne[ConstantsAppuntamentoCsv.INDICE_DATA]) &&
                                        innerColonne[ConstantsAppuntamentoCsv.INDICE_ORA].equals(colonne[ConstantsAppuntamentoCsv.INDICE_ORA]) &&
                                        innerColonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO].equals(colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PSICOLOGO]));

                // Appuntamento disponibile
                return !conflitto; // Appuntamento non disponibile a causa di conflitto
            }
        }

        return false; // Se non abbiamo trovato l'appuntamento, restituendo false
    }

    @Override
    public void aggiornaStatoNotificaPaziente(Utente utente) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String[]> righe = UtilitiesCSV.leggiRigheDaCsv(ConstantsAppuntamentoCsv.FILE_PATH);

        // Aggiorna lo stato di notifica nel CSV
        List<String[]> righeAggiornate = new ArrayList<>();
        for (String[] colonne : righe) {
            // Se il nome utente del paziente corrisponde e lo stato di notifica è 1, aggiorna lo stato a 0
            if (colonne[ConstantsAppuntamentoCsv.INDICE_USERNAME_PAZIENTE].equals(utente.getUsername()) && colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PAZIENTE].equals("1")) {
                colonne[ConstantsAppuntamentoCsv.INDICE_STATO_NOTIFICA_PAZIENTE] = "0"; // Aggiorna lo stato di notifica del paziente a 0
            }
            righeAggiornate.add(colonne);
        }

        // Scrivi il contenuto aggiornato nel file
        UtilitiesCSV.scriviRigheAggiornate(ConstantsAppuntamentoCsv.FILE_PATH, righeAggiornate);
    }
}
