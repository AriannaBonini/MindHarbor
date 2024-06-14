package com.example.mindharbor.dao;

import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.PazientiNumTest;
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

    protected static final String ETA= "Età";

    protected static final String DIAGNOSI= "Diagnosi";

    protected static final String USERNAME= "Username";


    public List<PazientiNumTest> trovaPaziente(String username) throws SQLException {

        List<PazientiNumTest> pazienteList = new ArrayList<>();

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT P.Paziente_Username, COUNT(T.StatoPsicologo), u.Nome, u.Cognome, u.Genere " +
                "FROM paziente P " +
                "LEFT JOIN testpsicologico T ON P.Paziente_Username = T.Paziente " +
                "JOIN utente U ON U.Username=P.Paziente_Username " +
                "WHERE P.Username_Psicologo= ? " +
                "GROUP BY  P.Paziente_Username ";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            PazientiNumTest paziente = new PazientiNumTest( rs.getString(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5));


            pazienteList.add(paziente);
        }

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return pazienteList;
    }

    public Paziente getInfoSchedaPersonale(String username) throws SQLException{
        Paziente paziente=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT U.Nome, U.Cognome, Pa.Età, Pa.Diagnosi, U.Genere " +
                "FROM Paziente Pa " +
                "JOIN Utente AS U ON U.Username=Pa.Paziente_Username " +
                "WHERE Paziente_Username= ?;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

                paziente = new Paziente( rs.getString(1),
                    rs.getString(2),
                        rs.getString(5),
                    "",
                    rs.getInt(3),
                    rs.getString(4));


        }

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return paziente;
    }

    public Paziente getInfoPaziente(String username) throws SQLException {
        Paziente paziente=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT U.Nome, U.Cognome, Pa.Età, U.Genere " +
                "FROM Paziente Pa " +
                "JOIN Utente AS U ON U.Username=Pa.Paziente_Username " +
                "WHERE Paziente_Username= ?;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            paziente = new Paziente( rs.getString(1),
                    rs.getString(2),
                    rs.getString(4),
                    "",
                    rs.getInt(3),
                    "");

        }

        // Closing ResultSet and freeing resources
        rs.close();
        stmt.close();

        return paziente;
    }

    public String TrovaPsicologo(String username) throws SQLException {
        String nomePsicologo=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT Nome, Cognome " +
                "FROM Utente " +
                "JOIN Paziente AS P ON P.Username_Psicologo=Username " +
                "WHERE Paziente_Username= ?;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            nomePsicologo=(rs.getString(1) + " " + rs.getString(2));

        }

        return nomePsicologo;
    }

}
