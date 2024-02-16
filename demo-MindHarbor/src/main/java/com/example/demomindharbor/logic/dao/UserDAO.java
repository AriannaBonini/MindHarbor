package com.example.demomindharbor.logic.dao;

import com.example.demomindharbor.logic.enums.UserTypes;
import com.example.demomindharbor.logic.exceptions.DAOException;
import com.example.demomindharbor.logic.model.User;
import com.example.demomindharbor.logic.session.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    protected static final String USERNAME = "Username";

    protected static final String NAME = "Nome";

    protected static final String SURNAME = "Surname";

    protected static final String PSW = "Password";

    protected static final String RUOLO = "Ruolo";

    protected User getUser(ResultSet rs) throws SQLException {
        User user;
        UserTypes type;
        if (rs.getString(RUOLO).equals("Paziente")) {
            type = UserTypes.PAZIENTE;
        } else {
            type = UserTypes.PSICOLOGO;
        }
        user = new User(
                rs.getString(USERNAME),
                rs.getString(NAME),
                rs.getString(SURNAME),
                type);
        return user;
    }

    //chiamate al DB
    public User findUsername(String username) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        User user = null;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM User WHERE" + USERNAME + "=?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1,username);

        ResultSet rs = stmt.executeQuery();

        // Verifica se il risultato è vuoto
        if(!rs.first()) {

            return null;
        }

        // riposizionamento del cursore
        rs.first();


        user = getUser(rs);

        // chiusura del resultSet e liberazione delle risorse
        rs.close();
        stmt.close();

        return user;
    }

    public User findUser(String username, String password) throws DAOException, SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        User user = null;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT * FROM User WHERE " + USERNAME + " = ? AND " + PSW + " = ?;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        // Verify if ResultSet is empty
        if(!rs.first()) {
            throw new DAOException("Utente non trovato");
        }

        // Repositioning of the cursor
        rs.first();

        user = getUser(rs);

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return user;
    }
    public Integer registerUser(String username, String name, String surname, String psw, String ruolo) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        Integer result = -1;

        conn = ConnectionFactory.getConnection();

        String sql = "INSERT INTO User (Username, Nome, Surname, Password, Ruolo) VALUES(?, ?, ?, ?, ?)";

        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);
        stmt.setString(2, name);
        stmt.setString(3, surname);
        stmt.setString(4, psw);
        stmt.setString(5, ruolo);

        result = stmt.executeUpdate();

        if (result > 0) {
            Logger.getAnonymousLogger().log(Level.INFO, "ROW INSERTED");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "ROW NOT INSERTED");
        }

        stmt.close();

        return result;
    }
}
