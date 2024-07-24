package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SelezionaDataEOraController {
    private final List<LocalTime> timeRules = List.of(
            LocalTime.of(8, 30),
            LocalTime.of(18, 30),
            LocalTime.of(12, 0),
            LocalTime.of(13, 30)
    );

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public InfoUtenteBean getInfoPaziente() {
        return new setInfoUtente().getInfo();
    }
    public void deleteAppuntamento() {NavigatorSingleton.getInstance().deleteAppuntamentoBean();}

    public void setAppuntamento(AppuntamentiBean appuntamento) {NavigatorSingleton.getInstance().setAppuntamentoBean(appuntamento);}

    public AppuntamentiBean getAppuntamento() {return NavigatorSingleton.getInstance().getAppuntamentoBean();}
    public boolean checkTime(String insertTime) throws DateTimeParseException {
        try {
            LocalTime time = LocalTime.parse(insertTime, timeFormatter);

            if (time.isAfter(timeRules.get(0)) && time.isBefore(timeRules.get(1)) && (time.isBefore(timeRules.get(2)) || time.isAfter(timeRules.get(3)))) {
                return true;
            }

        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return false;
    }
}

