package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaTestController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.TestBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.Decorator.ImageDecorator;
import com.example.mindharbor.patterns.Decorator.TestDecorator;
import com.example.mindharbor.utilities.LabelDuration;
import com.example.mindharbor.utilities.NavigatorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ListaTestGraphicController {

    @FXML
    private ListView<Node> listViewTest;
    @FXML
    private Label labelPsicologo;
    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label info;
    @FXML
    private Label home;
    private final ListaTestController listaTestController= new ListaTestController();
    private static final Logger logger = LoggerFactory.getLogger(ListaTestGraphicController.class);
    private final NavigatorSingleton navigator = NavigatorSingleton.getInstance();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = listaTestController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());

        modificaStatoNotifica();
        trovaPsicologo();
        popolaLista();

    }

    private void trovaPsicologo() {
        try {
            String nomePsicologo= listaTestController.ricercaPsicologo();
            labelPsicologo.setText(nomePsicologo);
        } catch (DAOException e) {
            logger.info("Errore durante la ricerca dello psicologo: ", e);
        }

    }

    private void modificaStatoNotifica()  {
        try {
            listaTestController.modificaStatoTest();
        } catch (DAOException e ) {
            logger.info("Errore durante la modifica dello stato dei test psicologici", e);
        }
    }

    private void popolaLista() {
        try {
            List<TestBean> listaTest = listaTestController.getListaTest();
            if (listaTest.isEmpty()) {
                info.setText("Non esistono test");
            } else {
                creaVBoxListaTest(listaTest);
            }
        }catch (DAOException e) {
            logger.info("Non non ci sono test", e);
        }
    }

    private void creaVBoxListaTest(List<TestBean> listaTest) {
        listViewTest.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();

        for (TestBean test : listaTest){

            VBox boxTest = new VBox();
            HBox hBoxTest = new HBox();
            ImageView immagineStato = new ImageView();

            Label nomeTest =new Label("\n     NOME:" + " " + test.getNomeTest());
            Label dataTest =new Label("\n     DATA:" + " " + test.getData());

            Label risultatoTest;
            if(test.getRisultato()==null) {
                risultatoTest =new Label("\n     RISULTATO:" + "non presente" );

            } else {
                risultatoTest =new Label("\n     RISULTATO:" + " " + test.getRisultato());
            }

            nomeTest.setTextFill(Color.WHITE);
            risultatoTest.setTextFill(Color.WHITE);

            boxTest.getChildren().addAll(dataTest, nomeTest, risultatoTest);

            ImageDecorator imageDecorator= new TestDecorator(immagineStato,test.getSvolto());
            imageDecorator.loadImage();

            immagineStato.setFitWidth(25);
            immagineStato.setFitHeight(25);

            hBoxTest.getChildren().addAll(immagineStato, boxTest);
            hBoxTest.setSpacing(10);

            items.add(hBoxTest);

            hBoxTest.setUserData(test);
        }
        listViewTest.setFixedCellSize(100);
        listViewTest.getItems().addAll(items);
    }

    @FXML
    public void goToHome() {
        try {
            Stage listaTest = (Stage) home.getScene().getWindow();
            listaTest.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void testSelezionato() {
        try {
            Node nodo = listViewTest.getSelectionModel().getSelectedItem();
            if (nodo == null) {
                return;
            }
            TestBean test = (TestBean) nodo.getUserData();
            if(test.getSvolto()==1) {
                new LabelDuration().Duration(info,"test già effettuato");
                return;
            }
            String nomeTest = test.getNomeTest();
            Date dataTest=test.getData();

            Stage listaTest = (Stage) listViewTest.getScene().getWindow();
            listaTest.close();

            listaTestController.setNomeTestData(nomeTest,dataTest);
            navigator.gotoPage("/com/example/mindharbor/SvolgiTest.fxml");

        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
}


