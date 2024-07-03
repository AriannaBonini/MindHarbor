package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaPsicologiController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.patterns.decorator.GenereDecorator;
import com.example.mindharbor.patterns.decorator.ImageDecorator;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;

public class ListaPsicologiGraphicController {

    @FXML
    private ListView<Node> listViewPsicologo;
    @FXML
    private ImageView tornaIndietro;
    @FXML
    private Label labelNomePaziente;
    @FXML
    private Label home;
    @FXML
    private Text listaVuota;

    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ListaPsicologiGraphicController.class);
    private final ListaPsicologiController listaPsicologiController= new ListaPsicologiController();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = listaPsicologiController.getInfoPaziente();
        labelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        popolaLista();
    }
    private void popolaLista() {
        try {
            List<PsicologoBean> listaPsicologiBean = ListaPsicologiController.getListaPsicologi();
            creaVBoxListaPsicologi(listaPsicologiBean);
        }catch (DAOException e) {
            logger.info("Non non ci sono appuntamenti", e);
            listaVuota.setText("Non esistono appuntamenti");
        }
    }
    private void creaVBoxListaPsicologi(List<PsicologoBean> listaPsicologiBean) {
        listViewPsicologo.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();
        for(PsicologoBean psiBean : listaPsicologiBean) {

            VBox boxPsicologo =new VBox();
            HBox hBoxPsicologo =new HBox();
            ImageView immaginePsicologo = new ImageView();

            Label nomePsicologo = new Label("\n     NOME:" + " " + psiBean.getNome());
            Label cognomePsicologo = new Label("     COGNOME:" + " " + psiBean.getCognome());

            nomePsicologo.setTextFill(Color.WHITE);
            cognomePsicologo.setTextFill(Color.WHITE);

            boxPsicologo.getChildren().addAll(nomePsicologo, cognomePsicologo);

            ImageDecorator imageDecorator= new GenereDecorator(immaginePsicologo,psiBean.getGenere());
            imageDecorator.loadImage();

            hBoxPsicologo.getChildren().addAll(immaginePsicologo, boxPsicologo);
            items.add(hBoxPsicologo);

            hBoxPsicologo.setUserData(psiBean);

        }
        listViewPsicologo.setFixedCellSize(100);
        listViewPsicologo.getItems().addAll(items);

    }

    @FXML
    public void goToHome() {
        try {
            listaPsicologiController.deleteAppuntamento();

            Stage listaPsicologi = (Stage) home.getScene().getWindow();
            listaPsicologi.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia della Home", e);
        }
    }

    @FXML
    public void tornaIndietro() {
        try {
            Stage listaPsicologi= (Stage) tornaIndietro.getScene().getWindow();
            listaPsicologi.close();

            navigator.gotoPage("/com/example/mindharbor/InserisciInfo.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void nodoSelezionato() {
        try {
            Node nodo = listViewPsicologo.getSelectionModel().getSelectedItem();
            if(nodo==null) {
                return;
            }

            PsicologoBean psicologo =(PsicologoBean) nodo.getUserData();
            Stage listaPsicologi = (Stage) listViewPsicologo.getScene().getWindow();
            listaPsicologi.close();

            listaPsicologiController.setUsername(psicologo.getUsername());

            navigator.gotoPage("/com/example/mindharbor/RichiediPrenotazione.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
}
