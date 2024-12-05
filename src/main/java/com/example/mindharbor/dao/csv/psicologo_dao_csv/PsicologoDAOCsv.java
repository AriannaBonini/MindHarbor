package com.example.mindharbor.dao.csv.psicologo_dao_csv;

import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PsicologoDAOCsv implements PsicologoDAO {

    public Psicologo getInfoPsicologo(Psicologo psicologo) throws DAOException {
        // Leggi tutte le righe del file CSV
        List<String> righeCSV;
        try {
            righeCSV = Files.readAllLines(Paths.get(ConstantsPsicologoCsv.FILE_PATH));
        } catch (IOException e) {
            throw new DAOException(ConstantsPsicologoCsv.ERRORE_LETTURA + " " + e.getMessage());
        }

        // Cerca le informazioni dello psicologo
        for (String riga : righeCSV) {
            String[] colonne = riga.split(",",-1);

            //verifichiamo che lo username dello psicologo sia quello cercato
            if (colonne[ConstantsPsicologoCsv.INDICE_PSICOLOGO_USERNAME].equals(psicologo.getUsername())) {
                psicologo.setCostoOrario(Integer.parseInt(colonne[ConstantsPsicologoCsv.INDICE_COSTO_ORARIO])); // Imposta il costo orario
                psicologo.setNomeStudio(colonne[ConstantsPsicologoCsv.INDICE_NOME_STUDIO]); // Imposta il nome dello studio
                break; // Esci dal ciclo una volta trovato
            }
        }

        return psicologo; // Restituisce l'oggetto psicologo aggiornato
    }
}
