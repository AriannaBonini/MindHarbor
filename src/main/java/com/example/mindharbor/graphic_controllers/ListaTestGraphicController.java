package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaTestController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.dao.TestPsicologicoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.SessionManager;
import com.example.mindharbor.utilities.LabelDuration;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ListaTestGraphicController {

    @FXML
    private ListView<Node> ListViewTest;
    @FXML
    private Label LabelPsicologo, LabelNomePaziente, Info, Home;
    private final ListaTestController listaTestController= new ListaTestController();
    private static final Logger logger = LoggerFactory.getLogger(ListaTestGraphicController.class);
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = listaTestController.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        ModificaStatoNotifica();
        TrovaPsicologo();
        PopolaLista();

    }

    private void TrovaPsicologo() {
        try {
            String nomePsicologo= listaTestController.ricercaPsicologo();
            LabelPsicologo.setText(nomePsicologo);
        } catch (DAOException e) {
            logger.info("Errore durante la ricerca dello psicologo: ", e);
        }

    }

    private void ModificaStatoNotifica()  {
        try {
            listaTestController.modificaStatoTest();
        } catch (DAOException e ) {
            logger.info("Errore durante la modifica dello stato dei test psicologici", e);
        }
    }

    private void PopolaLista() {
        try {
            List<TestBean> listaTest = listaTestController.getListaTest();
            if (listaTest.isEmpty()) {
                Info.setText("Non esistono test");
            } else {
                CreaVBoxListaTest(listaTest);
            }
        }catch (DAOException e) {
            logger.info("Non non ci sono test", e);
        }
    }

    private void CreaVBoxListaTest(List<TestBean> listaTest) {
        ListViewTest.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (TestBean test : listaTest){

            VBox BoxTest= new VBox();
            HBox HBoxTest= new HBox();
            Image image;
            ImageView ImmagineStato= new ImageView();

            Label NomeTest=new Label("\n     NOME:" + " " + test.getNomeTest());
            Label DataTest=new Label("\n     DATA:" + " " + test.getData());

            Label RisultatoTest;
            if(test.getRisultato()==null) {
                RisultatoTest=new Label("\n     RISULTATO:" + "non presente" );

            } else {
                RisultatoTest=new Label("\n     RISULTATO:" + " " + test.getRisultato());
            }

            NomeTest.setTextFill(Color.WHITE);
            RisultatoTest.setTextFill(Color.WHITE);

            BoxTest.getChildren().addAll(DataTest,NomeTest,RisultatoTest);

            if (test.getSvolto()==0) {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/NonCompletato.png")));
                ImmagineStato.setImage(image);
            } else {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/Completato.png")));
                ImmagineStato.setImage(image);
            }

            ImmagineStato.setFitWidth(25);
            ImmagineStato.setFitHeight(25);

            HBoxTest.getChildren().addAll(ImmagineStato, BoxTest);
            HBoxTest.setSpacing(10);

            items.add(HBoxTest);

            HBoxTest.setUserData(test);
        }
        ListViewTest.setFixedCellSize(100);
        ListViewTest.getItems().addAll(items);
    }

    @FXML
    public void goToHome() {
        try {
            Stage ListaTest = (Stage) Home.getScene().getWindow();
            ListaTest.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void TestSelezionato() {
        try {
            Node nodo = ListViewTest.getSelectionModel().getSelectedItem();
            if (nodo == null) {
                return;
            }
            TestBean test = (TestBean) nodo.getUserData();
            if(test.getSvolto()==1) {
                new LabelDuration().Duration(Info,"test già effettuato");
                return;
            }
            String nomeTest = test.getNomeTest();
            Date dataTest=test.getData();

            Stage ListaTest = (Stage) ListViewTest.getScene().getWindow();
            ListaTest.close();

            listaTestController.setNomeTestData(nomeTest,dataTest);
            navigator.gotoPage("/com/example/mindharbor/SvolgiTest.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
}


