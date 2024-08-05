package com.example.mindharbor.dao.csv;

import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.dao.mysql.PazienteDAOMySql;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppuntamentoDAOCsv implements AppuntamentoDAO {
    protected static final String FILE_PATH ="MindHarborDB/csv/appuntamento.csv";
    protected static final String ERRORE_LETTURA="Errore nella lettura del file CSV";
    protected static final String ERRORE_SCRITTURA="Errore nella scrittura nel file CSV:";
    protected static final Integer INDICE_ID_APPUNTAMENTO=0;
    protected static final Integer INDICE_DATA=1;
    protected static final Integer INDICE_ORA=2;
    protected static final Integer INDICE_USERNAME_PAZIENTE=3;
    protected static final Integer INDICE_USERNAME_PSICOLOGO=4;
    protected static final Integer INDICE_STATO_APPUNTAMENTO=5;
    protected static final Integer INDICE_STATO_NOTIFICA_PSICOLOGO=6;
    protected static final Integer INDICE_STATO_NOTIFICA_PAZIENTE=7;

    @Override
    public List<Appuntamento> trovaAppuntamentiPaziente(Utente paziente, String selectedTabName) throws DAOException {
        List<Appuntamento> appuntamentoPazienteList = new ArrayList<>();

        LocalDate dataCorrente = LocalDate.now();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String s= br.readLine();

            while ((line = s) != null) {
                String[] colonne = line.split(",");

                String data = colonne[INDICE_DATA];
                String ora = colonne[INDICE_ORA];
                String username = colonne[INDICE_USERNAME_PAZIENTE];

                // Verifica se l'username corrisponde al paziente
                if (username.equals(paziente.getUsername())) {
                    //conversione in questo formato yyyy-MM-dd
                    LocalDate appuntamentoDate = LocalDate.parse(data);

                    boolean verifica = (selectedTabName.equals("IN PROGRAMMA") && appuntamentoDate.isAfter(dataCorrente)) ||
                            (selectedTabName.equals("PASSATI") && appuntamentoDate.isBefore(dataCorrente));

                    if (verifica) {
                        Appuntamento appuntamento = new Appuntamento(data, ora);
                        appuntamentoPazienteList.add(appuntamento);
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentoPazienteList;
    }

    @Override
    public List<Appuntamento> trovaAppuntamentiPsicologo(Utente psicologo, String selectedTabName) throws DAOException {
        List<Appuntamento> appuntamentoPsicologoList = new ArrayList<>();

        LocalDate dataCorrente = LocalDate.now();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String s= br.readLine();

            while ((line = s) != null) {
                String[] colonne = line.split(",");

                String data = colonne[INDICE_DATA];
                String ora = colonne[INDICE_ORA];
                String usernamePaziente = colonne[INDICE_USERNAME_PAZIENTE];
                String usernamePsicologo = colonne[INDICE_USERNAME_PSICOLOGO];

                if (usernamePsicologo.equals(psicologo.getUsername())) {
                    LocalDate dataAppuntamento = LocalDate.parse(data);

                    // Controlla se la data soddisfa i criteri per l'aggiunta
                    boolean verifica = (selectedTabName.equals("IN PROGRAMMA") && dataAppuntamento.isAfter(dataCorrente)) ||
                            (selectedTabName.equals("PASSATI") && dataAppuntamento.isBefore(dataCorrente));

                    if (verifica) {
                        Appuntamento appuntamento = new Appuntamento(
                                dataAppuntamento.toString(),
                                ora,
                                new Paziente(usernamePaziente)
                        );
                        appuntamentoPsicologoList.add(appuntamento);
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return appuntamentoPsicologoList;
    }

    @Override
    public void insertRichiestaAppuntamento(Appuntamento appuntamento) throws DAOException{
        // Apre il file CSV in modalità append
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Crea una stringa per il record da scrivere nel CSV
            String nuovoRecord = String.join(",",
                    String.valueOf(controllaUltimoIDAppuntamento()),
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

    private Integer controllaUltimoIDAppuntamento() throws DAOException{
        int maxId=0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String s= br.readLine();

            while ((line = s) != null) {
                String[] colonne = line.split(","); // Assumiamo che il CSV sia separato da virgole
                int id = Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]);
                if (id > maxId) {
                    maxId = id; // Aggiorna il massimo ID trovato
                }
            }
        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return maxId+1;
    }

    @Override
    public  Integer getNumRicAppDaNotificare(Utente utente) throws DAOException {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String s= br.readLine();

            while ((line = s) != null) {
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

    public List<Appuntamento> trovaRichiesteAppuntamento(Utente utente) throws DAOException {
        List<Appuntamento> richiesteAppuntamento = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String s= br.readLine();

            while ((line = s) != null) {
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

    @Override
    public void updateStatoNotifica(Appuntamento richiestaAppuntamento) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> file;
        try {
            file = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder recordAggiornato = new StringBuilder();

        // Aggiorna lo stato di notifica nel CSV
        for (String riga : file) {
            String[] colonne = riga.split(",");

            // controlliamo che l'id appuntamento sia quello corretto
            if (Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]) == richiestaAppuntamento.getIdAppuntamento()) {
                // Aggiorna lo stato di notifica (supponiamo che sia nella quinta colonna)
                colonne[INDICE_STATO_NOTIFICA_PSICOLOGO] = "0"; // Imposta stato notifica psicologo a 0
            }

            // Ricostruisci la riga e aggiungila al contenuto aggiornato
            recordAggiornato.append(String.join(",", colonne)).append(System.lineSeparator());
        }

        // Scrivi il contenuto aggiornato nel file
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
            String line;
            String s=br.readLine();

            while ((line = s) != null) {
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

    @Override
    public void updateRichiesta(Appuntamento appuntamento) throws DAOException {
        List<String> righeCSV;
        Path path = Paths.get(FILE_PATH);
        try {
            righeCSV = Files.readAllLines(path);
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        List<String> righeAggiornate = new ArrayList<>();

        // Aggiornamento dello stato dell'appuntamento
        for (String riga : righeCSV) {
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
        try {
            Files.write(path, righeAggiornate);
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA + " " + e.getMessage());
        }

        // Chiamata al metodo per aggiungere lo psicologo al paziente
        new PazienteDAOMySql().aggiungiPsicologoAlPaziente(appuntamento);
    }

    @Override
    public void eliminaRichiesteDiAppuntamentoPerAltriPsicologi(Appuntamento appuntamento) throws DAOException{
        // Leggi tutte le righe del file CSV
        List<String> file;
        try {
            file = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder nuovoContenuto = new StringBuilder();

        // Filtra le righe che devono essere mantenute
        for (String riga : file) {
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
        List<String> file;
        try {
            file = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Stringa per costruire il nuovo contenuto del file
        StringBuilder nuovoContenuto = new StringBuilder();

        // Filtra le righe che devono essere mantenute
        for (String riga : file) {
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
    public boolean getDisp(Integer idAppuntamento, Utente utente) throws DAOException{
        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }


        // Verifica la disponibilità dell'appuntamento
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            // Controlla se l'appuntamento corrisponde e se è associato all'utente
            if (Integer.parseInt(colonne[INDICE_ID_APPUNTAMENTO]) == idAppuntamento && colonne[INDICE_USERNAME_PSICOLOGO].equals(utente.getUsername())) {

                // Ora verifichiamo se esiste un conflitto
                for (String innerRiga : righeCSV) {
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
