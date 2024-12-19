package com.example.mindharbor.dao.csv.psicologo_dao_csv;

import com.example.mindharbor.dao.PsicologoDAO;
import com.example.mindharbor.eccezioni.EccezioneDAO;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.utilities.costanti.CostantiLetturaScrittura;
import com.example.mindharbor.utilities.UtilitiesCSV;
import java.util.List;

public class PsicologoDAOCsv implements PsicologoDAO {

    public Psicologo getInfoPsicologo(Psicologo psicologo) throws EccezioneDAO {
        // Leggi le righe dal file CSV usando il CSVReader
        try {
            List<String[]> righeCSV = UtilitiesCSV.leggiRigheDaCsv(ConstantsPsicologoCsv.FILE_PATH, CostantiLetturaScrittura.SOLO_LETTURA);

            // Cerca le informazioni dello psicologo
            for (String[] colonne : righeCSV) {
                // Verifica che lo username dello psicologo corrisponda a quello cercato
                if (colonne[ConstantsPsicologoCsv.INDICE_PSICOLOGO_USERNAME].equals(psicologo.getUsername())) {
                    // Imposta il costo orario e il nome dello studio
                    psicologo.setCostoOrario(Integer.parseInt(colonne[ConstantsPsicologoCsv.INDICE_COSTO_ORARIO]));
                    psicologo.setNomeStudio(colonne[ConstantsPsicologoCsv.INDICE_NOME_STUDIO]);
                    break; // Esci dal ciclo una volta trovato
                }
            }
        } catch (EccezioneDAO e) {
            throw new EccezioneDAO("Errore nella lettura del file CSV: " + e.getMessage(), e);
        }

        return psicologo; // Restituisce l'oggetto psicologo aggiornato
    }
}
