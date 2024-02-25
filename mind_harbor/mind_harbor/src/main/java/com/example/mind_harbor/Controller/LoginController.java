package com.example.mind_harbor.Controller;

import com.example.mind_harbor.DAO.UserDAO;
import com.example.mind_harbor.Model.Utente;


public class LoginController {
    private UserDAO userDAO;

    public LoginController(){
        this.userDAO = new UserDAO();
    }
    public interface LoginResulHendler{
        void onLoginSuccess(String role);
        void onLoginFailed(String message);
    }
    public void attemptLogin(String username, String password, LoginResulHendler handler){
        try{
            Utente user = userDAO.validate(username, password);
            if(user != null){
                // andato bene
                handler.onLoginSuccess(user.getRuolo());
            } else{
                // andato male
                handler.onLoginFailed("Username o password non corretti.");
            }
        }catch (Exception e){
            handler.onLoginFailed("Errore di connessione al database.");
        }
    }
}