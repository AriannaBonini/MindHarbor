package com.example.mindharbor.dao;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UtenteDao {
    protected static final String USERNAME = "Username";

    protected static final String NOME = "Nome";

    protected static final String COGNOME = "Cognome";

    protected static final String PSW = "Password";

    protected static final String RUOLO = "Categoria";

    private Connection connection;

    public Utente TrovaUtente(String username, String password) throws SQLException, DAOException {
        PreparedStatement stmt = null;
        Connection conn = null;
        Utente utente = null;

        conn = DatabaseConnection.getConnection();


        String sql = "SELECT * FROM Utente WHERE Username = ? AND Password = ?;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if(!rs.first()) {
            throw new DAOException("Utente non trovato");

        }

        // Repositioning of the cursor
        rs.first();

        utente = getUser(rs);

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return utente;
    }


    protected Utente getUser(ResultSet rs) throws SQLException {
        Utente utente;
        UserType type;
        if (rs.getString(RUOLO).equals("Paziente")) {
            type = UserType.PAZIENTE;
        } else {
            type = UserType.PSICOLOGO;
        }
        utente = new Utente(
                rs.getString(USERNAME),
                rs.getString(NOME),
                rs.getString(COGNOME),
                type);
        return utente;
    }
}