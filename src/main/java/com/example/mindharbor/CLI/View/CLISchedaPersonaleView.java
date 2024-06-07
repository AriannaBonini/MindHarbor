package com.example.mindharbor.CLI.View;

import com.example.mindharbor.beans.PazientiBean;
import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.InputHandler;
import com.example.mindharbor.utilities.OutputHandler;

import java.sql.SQLException;

public class CLISchedaPersonaleView {
    public void displaySchedaPersonale(PazientiBean pazienteBean) {
        OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Scheda Personale:" + ANSI_CODE.ANSI_RESET_BOLD);
        OutputHandler.print("Nome: " + pazienteBean.getNome());
        OutputHandler.print("Cognome: " + pazienteBean.getCognome());
        OutputHandler.print("Età: " + pazienteBean.getEtà());
        OutputHandler.print("Diagnosi: " + pazienteBean.getDiagnosi());
    }
    public void displayOptions(){
        OutputHandler.print("\n" + ANSI_CODE.ANSI_BOLD + "Opzioni:" + ANSI_CODE.ANSI_RESET_BOLD);
        OutputHandler.print("1. Prescrivi un test");
        OutputHandler.print("2. Torna alla lista dei pazienti");
        OutputHandler.print("3. Torna alla homepage");
    }
    public int getUserInput(InputHandler inputHandler){
        return inputHandler.getIntInput(1,3);
    }

    public void displayErrorMessage(SQLException e) {
        OutputHandler.print("Errore durante la visualizzazione della scheda personale: " + e.getMessage());
    }

    public void clean() {
        OutputHandler.clean_page();
    }

    public void displayInvalidOptionMessage() {
        OutputHandler.print("Scelta opzione non valida");
    }
}
