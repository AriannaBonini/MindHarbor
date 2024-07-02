package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.SelezionaDataEOraController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.utilities.LabelDuration;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class SelezionaDataEOraGraphicController {
    @FXML
    private Label LabelNomePaziente, Avanti, Home, Info;
    @FXML
    private TextField Orario;
    @FXML
    private DatePicker Data;

    private static final Logger logger = LoggerFactory.getLogger(SelezionaDataEOraGraphicController.class);
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();
    private final SelezionaDataEOraController selezionaDataEOraController= new SelezionaDataEOraController();
    private AppuntamentiBean appuntamento;

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = selezionaDataEOraController.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        DataRules();

        if((appuntamento=selezionaDataEOraController.getAppuntamento())!=null) {
            Orario.setText(appuntamento.getOra());
            LocalDate data= LocalDate.parse(appuntamento.getData());
            Data.setValue(data);
        }else {appuntamento=new AppuntamentiBean();}
    }

    private void DataRules() {
        Data.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                setDisable(isBeforeTodayOrWeekend(date));
            }
        });
    }

    private boolean isBeforeTodayOrWeekend(LocalDate date) {
        return date.isBefore(LocalDate.now()) || isWeekend(date);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }


    @FXML
    public void goToHome() {
        try {
            selezionaDataEOraController.deleteAppuntamento();
            Stage SelezionaDataEOra = (Stage) Home.getScene().getWindow();
            SelezionaDataEOra.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void ClickAvanti() {
        if (Data.getValue() == null || Orario.getText().isEmpty()) {
            new LabelDuration().Duration(Info, "Compilare tutti i campi");
        } else {

            try {
                if (!selezionaDataEOraController.CheckTime(Orario.getText())) {
                    new LabelDuration().Duration(Info, "Orario non valido");
                } else {
                    appuntamento.setData(String.valueOf(Data.getValue()));
                    appuntamento.setOra(Orario.getText());

                    goToInfo(appuntamento);
                }
            } catch (DateTimeParseException e) {
                new LabelDuration().Duration(Info, "Il formato deve essere: HH:mm");
            }
        }
    }

    private void goToInfo(AppuntamentiBean appuntamento) {
        try {
            Stage SelezionaDataEOra = (Stage) Avanti.getScene().getWindow();
            SelezionaDataEOra.close();

            selezionaDataEOraController.setAppuntamento(appuntamento);

            navigator.gotoPage("/com/example/mindharbor/InserisciInfo.fxml");
        }catch (IOException e) {
            logger.info("Impossibile caricare l'interfaccia ", e);
        }
    }
}

