package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.paziente.prenota_appuntamento.RichiestaAppuntamentoController;
import com.example.mindharbor.beans.AppuntamentiBean;
import com.example.mindharbor.beans.InfoUtenteBean;
import com.example.mindharbor.constants.Constants;
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


public class SelezionaDataEOraGraphicController {
    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label avanti;
    @FXML
    private Label home;
    @FXML
    private Label info;
    @FXML
    private TextField orario;
    @FXML
    private DatePicker data;

    private static final Logger logger = LoggerFactory.getLogger(SelezionaDataEOraGraphicController.class);
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();
    private final RichiestaAppuntamentoController richiestaAppuntamentoController = new RichiestaAppuntamentoController();
    private AppuntamentiBean appuntamento;

    public void initialize() {
        InfoUtenteBean infoUtenteBean = richiestaAppuntamentoController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        dataRules();

        if((appuntamento= richiestaAppuntamentoController.getRichiestaAppuntamentoScelta())!=null) {
            orario.setText(appuntamento.getOra());
            LocalDate dataLocale = LocalDate.parse(appuntamento.getData());
            this.data.setValue(dataLocale);
        }else {appuntamento=new AppuntamentiBean();}
    }

    private void dataRules() {
        // Disabilito la possibilità di inserimento manuale da parte del paziente.
        data.getEditor().setDisable(true);
        data.getEditor().setFocusTraversable(false);

        data.setDayCellFactory(picker -> new DateCell() {
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
            richiestaAppuntamentoController.eliminaRichiestaAppuntamentoScelta();
            Stage selezionaDataEOra = (Stage) home.getScene().getWindow();
            selezionaDataEOra.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        } catch (IOException e) {
            logger.error(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }
    }

    @FXML
    public void clickAvanti() {
        if (data.getValue() == null || orario.getText().isEmpty()) {
            new LabelDuration().duration(info, "Compilare tutti i campi");
        } else {

            try {
                if (!richiestaAppuntamentoController.controllaOrario(orario.getText())) {
                    new LabelDuration().duration(info, "Orario non valido");
                } else {
                    appuntamento.setData(String.valueOf(data.getValue()));
                    appuntamento.setOra(orario.getText());

                    prossimaInterfaccia(appuntamento);
                }
            } catch (IllegalArgumentException e) {
                new LabelDuration().duration(info, "Il formato deve essere: HH:mm");
            }
        }
    }

    private void prossimaInterfaccia(AppuntamentiBean appuntamento) {
        try {
            Stage selezionaDataEOra = (Stage) avanti.getScene().getWindow();
            selezionaDataEOra.close();

            richiestaAppuntamentoController.setRichiestaAppuntamentoScelta(appuntamento);

            navigator.gotoPage("/com/example/mindharbor/InserisciInfo.fxml");
        }catch (IOException e) {
            logger.info(Constants.IMPOSSIBILE_CARICARE_INTERFACCIA, e);
        }
    }
}

