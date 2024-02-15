package com.example.demomindharbor.logic.utilities;


import com.example.demomindharbor.logic.exceptions.InvalidFormatException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class absDialogNavigatorController {
    /*
    AbsDialogNavigatorController astrae e incapsula la logica comune per la gestione di dialoghi e la navigazione in un'applicazione JavaFX.
    Attraverso l'ereditarietà, le classi derivate possono sfruttare queste funzionalità senza dover gestire direttamente gli alert o la logica
    di navigazione, promuovendo un design modulare e riutilizzabile.
    */
    protected Alert errorAlert; //per mostrare messaggi di errore agli utenti
    protected Alert infoAlert; //per mostrare messaggi informativiagli utenti
    protected TextInputDialog textAlert; //per richiedere input testuale all'utente


    // di seguito troviamo tutti i percorsi (da aggiornare e da scrivere poi con il path completo della loro posizione) delle varie view dell'applicazione

    protected static final String COURSE_COMPONENT = "CourseComponent.fxml";
    protected static final String NOTE_COMPONENT = "NoteComponent.fxml";
    protected static final String NOTE_ACTION_CHOICE = "NotesActionChoice.fxml";
    protected static final String PROFESSOR_COURSES = "ProfessorCourses.fxml";
    protected static final String PROFESSOR_REVISION = "ProfessorRevision.fxml";
    protected static final String PROFESSOR_REVISION_COMPONENT = "ProfessorRevisionComponent.fxml";
    protected static final String PUBLICATION = "Publication.fxml";
    protected static final String STUDENT_REVISION = "StudentRevision.fxml";
    protected static final String STUDENT_REVISION_COMPONENT = "StudentRevisionComponent.fxml";
    protected static final String USER_NOTES = "UserNotes.fxml";

    @FXML
    public void initialize(){
        errorAlert = new Alert(Alert.AlertType.ERROR);
        infoAlert = new Alert(Alert.AlertType.INFORMATION);
        textAlert = new TextInputDialog("");
    }


    protected void showErrorAlert(String title, String header){
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.show();
    }

    protected void showInfoAlert(String title, String header){
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(header);
        infoAlert.show();
    }

    protected String showTextAlert(String title, String header, String content) throws InvalidFormatException {
        textAlert.setTitle(title);
        textAlert.setHeaderText(header);
        textAlert.setContentText(content);

        Optional<String> result = textAlert.showAndWait();

        if(!result.isPresent()) {
            throw new InvalidFormatException("Please enter a response"); //da tradurre in italiano
        }
        return result.get();
    }

    /* qui si implementa il metodo goToPage per navigare tra le varie view
       viene usato il NavigatorSinglaton per cambiare la view corrente con quella specificata in goToPage
       andando a facilitare lo spostamento da un'interfaccie e un'altra.
    */
    protected void goToPage(String page) {
        try{
            NavigatorSingleton.getInstance().gotoPage(page);
        }
        catch (IOException e){
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
