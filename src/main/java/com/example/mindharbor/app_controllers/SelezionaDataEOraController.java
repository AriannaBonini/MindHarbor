package com.example.mindharbor.app_controllers;

import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.NavigatorSingleton;
import com.example.mindharbor.utilities.setInfoUtente;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class SelezionaDataEOraController {
    private final List<LocalTime> TimeRules = List.of(
            LocalTime.of(8, 30),
            LocalTime.of(18, 30),
            LocalTime.of(12, 0),
            LocalTime.of(13, 30)
    );

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public HomeInfoUtenteBean getInfoPaziente() {
        return new setInfoUtente().getInfo();
    }
    public void deleteAppuntamento() {NavigatorSingleton.getInstance().deleteAppuntamentoBean();}

    public void setAppuntamento(AppuntamentiBean appuntamento) {NavigatorSingleton.getInstance().setAppuntamentoBean(appuntamento);}

    public AppuntamentiBean getAppuntamento() {return NavigatorSingleton.getInstance().getAppuntamentoBean();}
    public boolean CheckTime(String insertTime) throws DateTimeParseException {
        try {
            LocalTime Time = LocalTime.parse(insertTime, timeFormatter);

            if (Time.isAfter(TimeRules.get(0)) && Time.isBefore(TimeRules.get(1)) && (Time.isBefore(TimeRules.get(2)) || Time.isAfter(TimeRules.get(3)))) {
                return true;
            }

        }catch (DateTimeParseException e) {
            throw e;
        }
        return false;
    }
}

