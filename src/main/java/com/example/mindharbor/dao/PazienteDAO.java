package com.example.mindharbor.dao;

import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAO {

    protected static final String NOME= "Nome_Psicologo";

    protected static final String COGNOME= "Cognome_Psicologo";

    protected static final String GENERE= "Genere";



    public List<Paziente> trovaPaziente(String username) throws SQLException {

        List<Paziente> pazienteList = new ArrayList<>();

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT U.Nome, U.Cognome, U.Genere " +
                "FROM Paziente Pa " +
                "JOIN Utente AS U ON U.Username=Pa.Paziente_Username " +
                "WHERE Username_Psicologo= ?;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Paziente paziente = new Paziente( rs.getString(1),
                    rs.getString(2),
                    rs.getString(3));

            pazienteList.add(paziente);
        }

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return pazienteList;
    }
}
