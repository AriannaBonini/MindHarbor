package com.example.mindharbor.dao;

import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppuntamentoDAO {
    protected static final String DATA = "Data";
    protected static final String ORA = "Ora";
    protected static final String USERNAME_PAZIENTE = "Username_Paziente";
    protected static final String USERNAME_PSICOLOGO = "Username_Psicologo";
    protected static final String NOME_PSICOLOGO = "Nome";
    protected static final String NOME_PAZIENTE = "Nome";
    protected static final String COGNOME_PSICOLOGO = "Cognome";
    protected static final String COGNOME_PAZIENTE = "Cognome";
    protected static final String USERNAME = "Username";
    protected static final String TABELLA_UTENTE="utente";
    protected static final String TABELLA_APPUNTAMENTO="Appuntamento";


    public List<Appuntamento> trovaAppuntamentoPsicologo(String usernamePsicologo, String selectedTabName) throws SQLException {

        List<Appuntamento> appuntamentoList = new ArrayList<>();

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT " +
                    "a." +  DATA + ", " +
                    "a." + ORA + ", " +
                    "p." + NOME_PSICOLOGO + ", " +
                    "p." + COGNOME_PSICOLOGO + ", " +
                    "pp." + NOME_PAZIENTE + ", " +
                    "pp." + COGNOME_PAZIENTE + ", " +
                    "a." + USERNAME_PAZIENTE + ", " +
                    "a." + USERNAME_PSICOLOGO + " " +
                    "FROM " + TABELLA_APPUNTAMENTO + " a " +
                    "JOIN " + TABELLA_UTENTE + " p ON p." + USERNAME + " = a." + USERNAME_PSICOLOGO + " " +
                    "JOIN " + TABELLA_UTENTE + " pp ON pp." + USERNAME + " = a." + USERNAME_PAZIENTE + " " +
                    "WHERE a." + USERNAME_PSICOLOGO + " = ? ";

        if (selectedTabName.equals("IN PROGRAMMA")) {
            sql += "AND a." + DATA + " >= NOW() ";
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePsicologo);
        } else {
            sql += "AND a." + DATA + " < NOW() " ;
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePsicologo);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Appuntamento appuntamento = new Appuntamento( rs.getString(1),
                    rs.getString(2),
                    "",
                    new Paziente(rs.getString(7), rs.getString(5), rs.getString(6)),
                    new Psicologo(rs.getString(8), rs.getString(3),rs.getString(4)));


            appuntamentoList.add(appuntamento);
        }

        rs.close();
        stmt.close();

        return appuntamentoList;
    }

    public List<Appuntamento> trovaAppuntamentoPaziente(String usernamePaziente, String selectedTabName) throws SQLException {
        List<Appuntamento> appuntamentoList = new ArrayList<>();

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql =  "SELECT " +
                    "a." + DATA + " , " +
                    "a." + ORA + " , " +
                    "p." + NOME_PSICOLOGO + " , " +
                    "p." + COGNOME_PSICOLOGO + " , " +
                    "pp." + NOME_PAZIENTE + " , " +
                    "pp." + COGNOME_PAZIENTE + " , " +
                    "a." + USERNAME_PAZIENTE + " , " +
                    "a." + USERNAME_PSICOLOGO + " " +
                    "FROM " + TABELLA_APPUNTAMENTO + " a " +
                    "JOIN " + TABELLA_UTENTE + " p ON p." + USERNAME + " = a." + USERNAME_PSICOLOGO + " " +
                    "JOIN " + TABELLA_UTENTE + " pp ON pp." + USERNAME + " = a." + USERNAME_PAZIENTE + " " +
                    "WHERE a." + USERNAME_PAZIENTE + " = ? ";

        if (selectedTabName.equals("IN PROGRAMMA")) {
            sql += "AND a." + DATA + " >= NOW() ";
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePaziente);
        } else {
            sql += "AND a." + DATA + " < NOW() ";
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePaziente);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Appuntamento appuntamento = new Appuntamento( rs.getString(1),
                    rs.getString(2),
                    "",
                    new Paziente(rs.getString(7),rs.getString(5), rs.getString(6)),
                    new Psicologo(rs.getString(8), rs.getString(3), rs.getString(4)));

            appuntamentoList.add(appuntamento);
        }

        rs.close();
        stmt.close();

        return appuntamentoList;
    }
}

