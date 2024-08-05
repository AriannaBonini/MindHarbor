package com.example.mindharbor.dao.csv;

import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PsicologoDAOCsv implements PsicologoDAO {

    protected static final String FILE_PATH ="MindHarborDB/csv/psicologo.csv";
    protected static final String ERRORE_LETTURA="Errore nella lettura del file CSV";
    protected static final Integer INDICE_COSTO_ORARIO=0;
    protected static final Integer INDICE_NOME_STUDIO=1;
    protected static final Integer INDICE_PSICOLOGO_USERNAME=2;

    public Psicologo getInfoPsicologo(Psicologo psicologo) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca le informazioni dello psicologo
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",");

            //verifichiamo che lo username dello psicologo sia quello cercato
            if (colonne[INDICE_PSICOLOGO_USERNAME].equals(psicologo.getUsername())) {
                psicologo.setCostoOrario(Integer.parseInt(colonne[INDICE_COSTO_ORARIO])); // Imposta il costo orario
                psicologo.setNomeStudio(colonne[INDICE_NOME_STUDIO]); // Imposta il nome dello studio
                break; // Esci dal ciclo una volta trovato
            }
        }

        return psicologo; // Restituisce l'oggetto psicologo aggiornato
    }
}
