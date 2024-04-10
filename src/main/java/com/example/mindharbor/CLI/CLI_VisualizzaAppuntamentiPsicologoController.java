package com.example.mindharbor.CLI;

import com.example.mindharbor.dao.AppuntamentoDAO;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.utilities.ANSI_CODE;
import com.example.mindharbor.utilities.OutputHandler;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CLI_VisualizzaAppuntamentiPsicologoController {
    private AppuntamentoDAO appuntamentoDAO;
    public CLI_VisualizzaAppuntamentiPsicologoController(){
        this.appuntamentoDAO = new AppuntamentoDAO();
    }
    public void visualizza(String username){

        Scanner scanner = new Scanner(System.in);
        int x = 1;
        String Scelta;
        String str = "Visualizza Appuntamenti";

        OutputHandler.separetor(str.length());
        OutputHandler.print(str);
        OutputHandler.separetor(str.length());
        OutputHandler.print("");

        List<Appuntamento> appuntamenti = null;

        try {
            appuntamenti = appuntamentoDAO.trovaAppuntamento(username);
            if (appuntamenti.isEmpty()){
                OutputHandler.print("Non ci sono appuntamenti");
            } else {
                OutputHandler.print(ANSI_CODE.ANSI_BOLD + "Appuntamenti trovati:" + ANSI_CODE.ANSI_RESET_BOLD);
                OutputHandler.printAppointments(appuntamenti);
                OutputHandler.print("");
                OutputHandler.print(ANSI_CODE.ANSI_BOLD + "'exit'" + ANSI_CODE.ANSI_RESET_BOLD + "per tornare alla homepage");


                while(true){
                    String scelta = scanner.nextLine();
                    if("exit".equalsIgnoreCase(scelta)){
                        OutputHandler.clean_page();
                        break;
                    }else{
                        OutputHandler.print("inserire " + ANSI_CODE.ANSI_BOLD +  "'exit' " + ANSI_CODE.ANSI_RESET + "per tornare alla homepage");
                    }
                }
            }
        } catch (SQLException e) {
            OutputHandler.print(ANSI_CODE.ANSI_RED + "Errore durante la ricerca degli appuntamenti" + ANSI_CODE.ANSI_RESET);
        }
    }

}
