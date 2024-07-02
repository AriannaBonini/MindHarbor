package com.example.mindharbor.graphic_controllers;

import com.example.mindharbor.app_controllers.ListaPsicologiController;
import com.example.mindharbor.beans.HomeInfoUtenteBean;
import com.example.mindharbor.beans.PazientiNumTestBean;
import com.example.mindharbor.beans.PsicologoBean;
import com.example.mindharbor.exceptions.DAOException;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ListaPsicologiGraphicController {

    @FXML
    private ListView<Node> ListViewPsicologo;
    @FXML
    private ImageView TornaIndietro;
    @FXML
    private Label LabelNomePaziente, Home;
    @FXML
    private Text ListaVuota;

    private final NavigatorSingleton navigator= NavigatorSingleton.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ListaPsicologiGraphicController.class);
    private final ListaPsicologiController listaPsicologiController= new ListaPsicologiController();

    public void initialize() {
        HomeInfoUtenteBean infoUtenteBean = listaPsicologiController.getInfoPaziente();
        LabelNomePaziente.setText(infoUtenteBean.getNome() + " " + infoUtenteBean.getCognome());
        PopolaLista();
    }
    private void PopolaLista() {
        try {
            List<PsicologoBean> listaPsicologiBean = ListaPsicologiController.getListaPsicologi();
            CreaVBoxListaPsicologi(listaPsicologiBean);
        }catch (DAOException e) {
            logger.info("Non non ci sono appuntamenti", e);
            ListaVuota.setText("Non esistono appuntamenti");
        }
    }
    private void CreaVBoxListaPsicologi(List<PsicologoBean> listaPsicologiBean) {
        ListViewPsicologo.getItems().clear();

        ObservableList<Node> items = FXCollections.observableArrayList();
        for(PsicologoBean psiBean : listaPsicologiBean) {

            VBox BoxPsicologo=new VBox();
            HBox HBoxPsicologo=new HBox();
            ImageView ImmaginePsicologo= new ImageView();
            Image image;

            Label NomePsicologo = new Label("\n     NOME:" + " " + psiBean.getNome());
            Label CognomePsicologo = new Label("     COGNOME:" + " " + psiBean.getCognome());

            NomePsicologo.setTextFill(Color.WHITE);
            CognomePsicologo.setTextFill(Color.WHITE);

            BoxPsicologo.getChildren().addAll(NomePsicologo,CognomePsicologo);

            if (psiBean.getGenere().equals("M")) {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaMaschio.png")));
                ImmaginePsicologo.setImage(image);

            } else {
                image= new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/mindharbor/Img/IconaFemmina.png")));
                ImmaginePsicologo.setImage(image);
            }
            HBoxPsicologo.getChildren().addAll(ImmaginePsicologo,BoxPsicologo);
            items.add(HBoxPsicologo);

            HBoxPsicologo.setUserData(psiBean);

        }
        ListViewPsicologo.setFixedCellSize(100);
        ListViewPsicologo.getItems().addAll(items);

    }

    @FXML
    public void goToHome() {
        try {
            listaPsicologiController.deleteAppuntamento();

            Stage listaPsicologi = (Stage) Home.getScene().getWindow();
            listaPsicologi.close();

            navigator.gotoPage("/com/example/mindharbor/HomePaziente.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void TornaIndietro() {
        try {
            Stage listaPsicologi= (Stage) TornaIndietro.getScene().getWindow();
            listaPsicologi.close();

            navigator.gotoPage("/com/example/mindharbor/InserisciInfo.fxml");

        }catch(IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }
    }

    @FXML
    public void NodoSelezionato() {
        try {
            Node nodo = ListViewPsicologo.getSelectionModel().getSelectedItem();
            if(nodo==null) {
                return;
            }

            PsicologoBean psicologo =(PsicologoBean) nodo.getUserData();
            Stage ListaPsicologi = (Stage) ListViewPsicologo.getScene().getWindow();
            ListaPsicologi.close();

            listaPsicologiController.setUsername(psicologo.getUsername());

            navigator.gotoPage("/com/example/mindharbor/RichiediPrenotazione.fxml");
        } catch (IOException e) {
            logger.error("Impossibile caricare l'interfaccia", e);
        }

    }
}
