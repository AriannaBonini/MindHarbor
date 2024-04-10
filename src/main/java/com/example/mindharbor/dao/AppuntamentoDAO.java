package com.example.mindharbor.dao;

import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppuntamentoDAO {
    protected static final String ID_APPUNTAMENTO = "ID_Appuntamento";
    protected static final String DATA = "Data";
    protected static final String ORA = "Ora";
    protected static final String USERNAME_PAZIENTE = "Username_Paziente";
    protected static final String USERNAME_PSICOLOGO = "Username_Psicologo";
    protected static final String NOME_PSICOLOGO = "Nome";
    protected static final String NOME_PAZIENTE = "Nome";
    protected static final String COGNOME_PSICOLOGO = "Cognome";
    protected static final String COGNOME_PAZIENTE = "Cognome";
    protected static final String USERNAME = "Username";


    public List<Appuntamento> trovaAppuntamento(String username) throws SQLException {

        List<Appuntamento> appuntamentoList = new ArrayList<>();

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT Data, Ora, Ps.Nome, Ps.Cognome, Pa.Nome, Pa.Cognome, Username_Paziente, Username_Psicologo " +
                "FROM Appuntamento " +
                "JOIN Utente AS Pa ON Pa.Username=Username_Paziente " +
                "JOIN Utente AS Ps ON Ps.Username=Username_Psicologo " +
                "WHERE Username_Psicologo= ?";
                // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Appuntamento appuntamento = new Appuntamento( rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    " ");

            appuntamentoList.add(appuntamento);
        }

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return appuntamentoList;
    }
}

