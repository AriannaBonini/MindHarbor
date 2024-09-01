package com.example.mindharbor.utilities;

import com.example.mindharbor.exceptions.DAOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UtilitiesCSV {
    private UtilitiesCSV(){}

    /**
     * Scarta l'intestazione di un file CSV.
     * <p>
     * Questo metodo legge la prima riga del file CSV associato al {@link BufferedReader} fornito,
     * che si presuppone sia l'intestazione. Se la riga di intestazione è vuota o il file CSV è vuoto,
     * viene lanciata un'eccezione {@link IOException}.
     * </p>
     *
     * @param br Il {@link BufferedReader} associato al file CSV da cui scartare l'intestazione.
     * @throws IOException Se il file CSV è vuoto o non ha un'intestazione valida.
     */
    public static void scartaIntestazione(BufferedReader br) throws IOException {
        String headerLine = br.readLine();
        if (headerLine == null || headerLine.isEmpty()) {
            throw new IOException("Il file CSV è vuoto o non ha un'intestazione valida.");
        }
    }

    /**
     * Legge tutte le righe da un file CSV specificato e le restituisce come una lista di stringhe.
     * <p>
     * Questo metodo legge il contenuto di un file CSV situato nel percorso specificato
     * e restituisce una lista contenente tutte le righe del file come stringhe.
     * Se si verifica un errore durante la lettura del file, viene lanciata un'eccezione {@link DAOException}.
     * </p>
     *
     * @param filePath Il percorso del file CSV da cui leggere le righe.
     * @return Una lista di stringhe, dove ciascuna stringa rappresenta una riga del file CSV.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    public static List<String> leggiRigheDaCsv(String filePath) throws DAOException {
        List<String> righe;
        Path path = Paths.get(filePath);
        try {
            righe = Files.readAllLines(path);
        } catch (IOException e) {
            throw new DAOException("Errore nella lettura del file CSV: " + e.getMessage(), e);
        }
        return righe;
    }

    /**
     * Scrive una lista di righe aggiornate in un file CSV specificato.
     * <p>
     * Questo metodo sovrascrive il contenuto del file CSV situato nel percorso specificato
     * con le righe fornite nella lista. Se si verifica un errore durante la scrittura del file,
     * viene lanciata un'eccezione {@link DAOException}.
     * </p>
     *
     * @param filePath       Il percorso del file CSV in cui scrivere le righe aggiornate.
     * @param righeAggiornate Una lista di stringhe, dove ciascuna stringa rappresenta una riga da scrivere nel file CSV.
     * @throws DAOException Se si verifica un errore durante la scrittura nel file CSV.
     */
    public static void scriviRigheAggiornate(String filePath, List<String> righeAggiornate) throws DAOException {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, righeAggiornate);
        } catch (IOException e) {
            throw new DAOException("Errore nella scrittura nel file CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Conta il numero di notifiche attive per un paziente specifico in un file CSV.
     * <p>
     * Questo metodo legge tutte le righe di un file CSV e conta quante righe corrispondono
     * a un determinato paziente e hanno una notifica attiva. Le colonne del CSV vengono
     * specificate tramite gli indici forniti come parametri.
     * </p>
     *
     * @param filePath Il percorso del file CSV da leggere.
     * @param username Lo username del paziente di cui contare le notifiche.
     * @param indicePaziente L'indice della colonna che contiene lo username del paziente.
     * @param indiceNotifica L'indice della colonna che contiene lo stato della notifica.
     * @return Il numero di notifiche attive trovate per il paziente specificato.
     * @throws DAOException Se si verifica un errore durante la lettura del file CSV.
     */
    public static int contaNotifichePaziente(String filePath, String username, int indicePaziente, int indiceNotifica) throws DAOException {
        int contatore = 0;
        List<String> righeCSV;

        try {
            righeCSV = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new DAOException("Errore nella lettura del file CSV: " + e.getMessage(), e);
        }
        // Conteggio delle notifiche per il paziente
        for (String riga : righeCSV) {
            String[] colonne = riga.split(","); // Assumiamo che il CSV utilizzi la virgola come delimitatore
            // Verifica se la riga corrisponde al paziente e se la notifica è attiva
            if (colonne[indicePaziente].equals(username) && colonne[indiceNotifica].equals("1")) {
                contatore++;
            }
        }
        return contatore;
    }
}
