package com.example.mindharbor.dao.csv.terapia_dao_csv;

import com.example.mindharbor.dao.TerapiaDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.Terapia;
import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.utilities.UtilitiesCSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

import static com.example.mindharbor.utilities.UtilitiesCSV.leggiRigheDaCsv;
import static com.example.mindharbor.utilities.UtilitiesCSV.scriviRigheAggiornate;

public class TerapiaDAOCsv implements TerapiaDAO {

    private static final Logger logger = LoggerFactory.getLogger(TerapiaDAOCsv.class);

    @Override
    public void insertTerapia(Terapia terapia) throws DAOException {
        // Creazione della nuova riga da aggiungere al CSV
        String[] nuovaRiga = {
                terapia.getTestPsicologico().getPsicologo().getUsername(),
                terapia.getTestPsicologico().getPaziente().getUsername(),
                terapia.getTerapia(),
                new java.sql.Date(terapia.getDataTerapia().getTime()).toString(), // Data terapia
                new java.sql.Date(terapia.getTestPsicologico().getData().getTime()).toString(), // Data test
                String.valueOf(1) // Notifica paziente (DEFAULT)
        };

        // Lettura delle righe esistenti nel CSV tramite la funzione leggiRigheDaCsv
        List<String[]> righeCSV = leggiRigheDaCsv(ConstantsTerapiaCsv.FILE_PATH);

        // Aggiungi la nuova riga
        righeCSV.add(nuovaRiga);

        // Scrittura delle righe aggiornate nel file CSV tramite la funzione scriviRigheAggiornate
        scriviRigheAggiornate(ConstantsTerapiaCsv.FILE_PATH, righeCSV);
    }

    @Override
    public List<Terapia> getTerapie(Utente utente) throws DAOException {
        List<Terapia> terapie = new ArrayList<>();

        // Utilizzo del metodo leggiRigheDaCsv per leggere tutte le righe dal file CSV
        List<String[]> righeCsv = leggiRigheDaCsv(ConstantsTerapiaCsv.FILE_PATH);

        // Elabora le righe lette dal CSV
        for (String[] colonne : righeCsv) {
            // Verifica che la riga abbia abbastanza colonne
            if (colonne.length > Math.max(
                    ConstantsTerapiaCsv.INDICE_PAZIENTE,
                    Math.max(ConstantsTerapiaCsv.INDICE_PSICOLOGO,
                            Math.max(ConstantsTerapiaCsv.INDICE_TERAPIA, ConstantsTerapiaCsv.INDICE_DATA_TERAPIA)))) {

                // Controllo se l'utente è il paziente
                if (colonne[ConstantsTerapiaCsv.INDICE_PAZIENTE].equals(utente.getUsername())) {
                    // Crea un'istanza di Terapia e la aggiunge alla lista
                    Terapia terapia = new Terapia(
                            new TestPsicologico(new Psicologo(colonne[ConstantsTerapiaCsv.INDICE_PSICOLOGO])),
                            colonne[ConstantsTerapiaCsv.INDICE_TERAPIA],
                            java.sql.Date.valueOf(colonne[ConstantsTerapiaCsv.INDICE_DATA_TERAPIA])
                    );
                    terapie.add(terapia);
                }
            } else {
                // Gestione delle righe malformattate
                String rigaMalformata = (colonne.length > 0) ? String.join(",", colonne) : "Riga vuota o nulla";
                logger.warn("Riga malformata: {}", rigaMalformata);
            }
        }

        // Aggiorna lo stato della notifica del paziente
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
        List<String[]> righeCSV = leggiRigheDaCsv(ConstantsTerapiaCsv.FILE_PATH);
        List<String[]> righeAggiornate = new ArrayList<>();

        // Aggiornamento delle righe esistenti
        for (String[] colonne : righeCSV) { // Ogni riga è già un array di stringhe

            // Controlla se la riga corrisponde all'utente paziente
            if (colonne[ConstantsTerapiaCsv.INDICE_PAZIENTE].equals(utente.getUsername())) {
                // Aggiorna lo stato di notifica del paziente
                colonne[ConstantsTerapiaCsv.INDICE_NOTIFICA_PAZIENTE] = String.valueOf(0); // Supponiamo che 0 significhi "non notificato"
            }

            // Aggiungi la riga (aggiornata o meno) alla lista
            righeAggiornate.add(colonne); // Aggiungi direttamente l'array di stringhe
        }

        // Scrittura delle righe aggiornate nel file CSV
        scriviRigheAggiornate(ConstantsTerapiaCsv.FILE_PATH, righeAggiornate);
    }

    @Override
    public Integer getNuoveTerapie(Utente paziente) throws DAOException {
        return UtilitiesCSV.contaNotifichePaziente(ConstantsTerapiaCsv.FILE_PATH, paziente.getUsername(), ConstantsTerapiaCsv.INDICE_PAZIENTE, ConstantsTerapiaCsv.INDICE_NOTIFICA_PAZIENTE);
    }

    public boolean controlloEsistenzaTerapiaPerUnTest(String usernamePsicologo, String usernamePaziente, String dataTestPsicologico) throws DAOException {
        List<String[]> righeCSV = leggiRigheDaCsv(ConstantsTerapiaCsv.FILE_PATH);
        for (String[] colonna : righeCSV) { // Ogni riga è già un array di stringhe
            if (colonna[ConstantsTerapiaCsv.INDICE_PSICOLOGO].equals(usernamePsicologo) && colonna[ConstantsTerapiaCsv.INDICE_PAZIENTE].equals(usernamePaziente) && colonna[ConstantsTerapiaCsv.INDICE_DATA_TEST].equals(dataTestPsicologico)) {
                //è stata trovata una prescrizione per il test
                return false;
            }
        }
        return true;
    }
}
