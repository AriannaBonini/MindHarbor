package com.example.mindharbor.dao.csv;

import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.Terapia;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.utilities.UtilitiesCSV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TerapiaDAOCsv implements TerapiaDAO {
    protected static final String FILE_PATH="MindHarborDB/csv/terapia.csv";
    protected static final String ERRORE_LETTURA="Errore nella lettura del file CSV";
    protected static final String ERRORE_SCRITTURA="Errore nella scrittura nel file CSV:";
    protected static final Integer INDICE_PSICOLOGO=0;
    protected static final Integer INDICE_PAZIENTE=1;
    protected static final Integer INDICE_TERAPIA=2;
    protected static final Integer INDICE_DATA_TERAPIA =3;
    protected static final Integer INDICE_NOTIFICA_PAZIENTE=5;

    @Override
    public void insertTerapia(Terapia terapia) throws DAOException {
        List<String> righeCSV;

        // Creazione della nuova riga da aggiungere al CSV
        String nuovaRiga = String.join(",",
                terapia.getTestPsicologico().getPsicologo().getUsername(),
                terapia.getTestPsicologico().getPaziente().getUsername(),
                terapia.getTerapia(),
                new java.sql.Date(terapia.getDataTerapia().getTime()).toString(), // Data terapia
                new java.sql.Date(terapia.getTestPsicologico().getData().getTime()).toString(), // Data test
                String.valueOf(1) // Notifica paziente (DEFAULT)
        );

        // Lettura delle righe esistenti nel CSV
        Path path = Paths.get(FILE_PATH);
        try {
            righeCSV = Files.readAllLines(path);
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Aggiungi la nuova riga
        righeCSV.add(nuovaRiga);

        // Scrittura delle righe aggiornate nel file CSV
        try {
            Files.write(path, righeCSV);
        } catch (IOException e) {
            throw new DAOException(ERRORE_SCRITTURA + " " + e.getMessage());
        }
    }

    @Override
    public List<Terapia> getTerapie(Utente utente) throws DAOException {
        List<Terapia> terapie = new ArrayList<>();
        List<String> righeCSV;

        // Lettura delle righe dal file CSV
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Elaborazione delle righe lette
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            // Controllo se l'utente è il paziente
            if (colonne[INDICE_PAZIENTE].equals(utente.getUsername())) {
                Terapia terapia = new Terapia(
                        new TestPsicologico(new Psicologo(colonne[INDICE_PSICOLOGO])),
                        colonne[INDICE_TERAPIA],
                        java.sql.Date.valueOf(colonne[INDICE_DATA_TERAPIA])
                );
                terapie.add(terapia);
            }
        }

        aggiornaStatoNotificaPaziente(utente);

        return terapie;
    }

    /**
     * Aggiorna lo stato di notifica di un paziente specifico nel file CSV.
     * <p>
     * Questo metodo esegue le seguenti operazioni:
     * <ul>
     *     <li>Legge tutte le righe del file CSV che contiene le informazioni sugli utenti e i loro stati di notifica.</li>
     *     <li>Scorre ogni riga del CSV e verifica se corrisponde all'utente paziente passato come parametro.</li>
     *     <li>Se viene trovata una corrispondenza, lo stato di notifica del paziente viene aggiornato a "0", che supponiamo significhi "non notificato".</li>
     *     <li>Aggiunge ogni riga, aggiornata o meno, alla lista delle righe aggiornate.</li>
     *     <li>Riscrive tutte le righe aggiornate nel file CSV.</li>
     * </ul>
     * </p>
     *
     * @param utente L'oggetto {@link Utente} che rappresenta il paziente di cui si desidera aggiornare lo stato di notifica.
     * @throws DAOException Se si verifica un errore durante la lettura o la scrittura del file CSV.
     */
    private void aggiornaStatoNotificaPaziente(Utente utente) throws DAOException {
        List<String> righeCSV = UtilitiesCSV.leggiRigheDaCsv(FILE_PATH);
        List<String> righeAggiornate = new ArrayList<>();

        // Aggiornamento delle righe esistenti
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Supponiamo che il CSV utilizzi la virgola come delimitatore

            // Controlla se la riga corrisponde all'utente paziente
            if (colonne[INDICE_PAZIENTE].equals(utente.getUsername())) {
                // Aggiorna lo stato di notifica del paziente
                colonne[INDICE_NOTIFICA_PAZIENTE] = String.valueOf(0); // Supponiamo che 0 significhi "non notificato"
            }

            // Aggiungi la riga (aggiornata o meno) alla lista
            righeAggiornate.add(String.join(",", colonne));
        }

        // Scrittura delle righe aggiornate nel file CSV
        UtilitiesCSV.scriviRigheAggiornate(FILE_PATH, righeAggiornate);
    }

    @Override
    public Integer getNuoveTerapie(Utente paziente) throws DAOException {
        return UtilitiesCSV.contaNotifichePaziente(FILE_PATH, paziente.getUsername(), INDICE_PAZIENTE, INDICE_NOTIFICA_PAZIENTE);
    }
}
