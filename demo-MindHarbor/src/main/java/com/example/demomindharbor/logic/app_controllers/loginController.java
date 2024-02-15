package com.example.demomindharbor.logic.app_controllers;

import com.example.demomindharbor.logic.beans.LoginCredentialBean;
import com.example.demomindharbor.logic.dao.UserDAO;
import com.example.demomindharbor.logic.exceptions.DAOException;
import com.example.demomindharbor.logic.exceptions.SessionUserException;
import com.example.demomindharbor.logic.model.User;

import java.sql.SQLException;

public class loginController extends absController {
    public void login(LoginCredentialBean credentialsBean) throws DAOException, SQLException, SessionUserException {
        int a;
        User user = new UserDAO().findUser(
                credentialsBean.getUsername(),
                credentialsBean.getPassword());

        storeSessionUser(user.getUsername(), user.getName(), user.getSurname(), user.getUserType());
    }
}