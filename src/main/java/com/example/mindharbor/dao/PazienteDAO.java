package com.example.mindharbor.dao;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.PazientiNumTest;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAO {
    protected static final String NOME= "Nome";
    protected static final String COGNOME= "Cognome";
    protected static final String GENERE= "Genere";
    protected static final String ETA= "Età";
    protected static final String DIAGNOSI= "Diagnosi";
    protected static final String USERNAME= "Username";
    private static final String TABELLA_UTENTE= "utente";
    private static final String TABELLA_PAZIENTE= "paziente";
    private static final String PAZIENTE_USERNAME= "Paziente_Username";
    private static final String PSICOLOGO_USERNAME= "Username_Psicologo";


    public List<PazientiNumTest> trovaPaziente(String usernamePsicologo) throws SQLException {

        List<PazientiNumTest> pazienteList = new ArrayList<>();

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT P." + PAZIENTE_USERNAME + ", SUM(T.StatoPsicologo), " +
                "U." + NOME + ", U." + COGNOME + ", U." + GENERE + " " +
                "FROM " + TABELLA_PAZIENTE + " P " +
                "LEFT JOIN testpsicologico T ON P." + PAZIENTE_USERNAME + " = T.Paziente " +
                "JOIN " + TABELLA_UTENTE + " U ON U." + USERNAME + " = P." + PAZIENTE_USERNAME + " " +
                "WHERE P." + USERNAME + "_Psicologo = ? " +
                "GROUP BY P." + PAZIENTE_USERNAME;
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePsicologo);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            PazientiNumTest paziente = new PazientiNumTest(rs.getInt(2),
                    new Paziente(0, "", rs.getString(1), rs.getString(3),rs.getString(4), UserType.PAZIENTE, rs.getString(5), "", "" ));


            pazienteList.add(paziente);
        }
        rs.close();
        stmt.close();

        return pazienteList;
    }

    public Paziente getInfoSchedaPersonale(String paziente1) throws SQLException{
        Paziente paziente=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT " +
                TABELLA_UTENTE + "." + NOME + ", " +
                TABELLA_UTENTE + "." + COGNOME + ", " +
                TABELLA_PAZIENTE + "." + ETA + ", " +
                TABELLA_PAZIENTE + "." + DIAGNOSI + ", " +
                TABELLA_UTENTE + "." + GENERE + " " +
                "FROM " + TABELLA_PAZIENTE + " " +
                "JOIN " + TABELLA_UTENTE + " ON " +
                TABELLA_UTENTE + "." + USERNAME + " = " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " " +
                "WHERE " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = ?";


        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, paziente1);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            paziente = new Paziente(rs.getInt(3),rs.getString(4), "", rs.getString(1), rs.getString(2),UserType.PAZIENTE, rs.getString(5),"","");
        }

        rs.close();
        stmt.close();

        return paziente;
    }

    public Paziente getInfoPaziente(Utente utente) throws SQLException {
        Paziente paziente=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT " +
                TABELLA_UTENTE + "." + NOME + ", " +
                TABELLA_UTENTE + "." + COGNOME + ", " +
                TABELLA_PAZIENTE + "." + ETA + ", " +
                TABELLA_UTENTE + "." + GENERE + " " +
                "FROM " + TABELLA_PAZIENTE + " " +
                "JOIN " + TABELLA_UTENTE + " ON " +
                TABELLA_UTENTE + "." + USERNAME + " = " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " " +
                "WHERE " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = ?";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, utente.getUsername());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            paziente = new Paziente(rs.getInt(3),"","", rs.getString(1), rs.getString(2),UserType.PAZIENTE, rs.getString(4), "","");
        }
        rs.close();
        stmt.close();

        return paziente;
    }

    public String TrovaNomeCognomePsicologo(String usernamePaziente) throws SQLException {
        String nomePsicologo=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT " +
                TABELLA_UTENTE + "." + NOME + ", " +
                TABELLA_UTENTE + "." + COGNOME + " " +
                "FROM " + TABELLA_UTENTE + " " +
                "JOIN " + TABELLA_PAZIENTE + " ON " +
                TABELLA_UTENTE + "." + USERNAME + " = " + TABELLA_PAZIENTE + "." + PSICOLOGO_USERNAME + " " +
                "WHERE " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = ?";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePaziente);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            nomePsicologo=(rs.getString(1) + " " + rs.getString(2));

        }
        rs.close();
        stmt.close();

        return nomePsicologo;
    }


    public Utente trovaNomeCognomePaziente(Utente utente) throws SQLException {
        Utente utentePaziente=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql ="SELECT " +
                TABELLA_UTENTE + "." + NOME + ", " +
                TABELLA_UTENTE + "." + COGNOME + " " +
                "FROM " + TABELLA_UTENTE + " " +
                "JOIN " + TABELLA_PAZIENTE + " ON " +
                TABELLA_UTENTE + "." + USERNAME + " = " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " " +
                "WHERE " + TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = ?";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, utente.getUsername());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            utentePaziente = new Utente("", rs.getString(1), rs.getString(2),null);

        }
        rs.close();
        stmt.close();

        return utentePaziente;
    }

    public boolean CheckPaziente(Paziente paziente) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql= "SELECT * " +
                "FROM " + TABELLA_PAZIENTE + " " +
                "JOIN " + TABELLA_UTENTE + " ON " +
                TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = " + TABELLA_UTENTE + "." + USERNAME + " " +
                "WHERE " + TABELLA_PAZIENTE + "." + ETA + " = ? AND " + TABELLA_UTENTE + "." + NOME + " = ? " +
                "AND " + TABELLA_UTENTE + "." + COGNOME + " = ? AND " + TABELLA_UTENTE + "." + USERNAME + " = ? ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1,paziente.getEtà());
        stmt.setString(2,paziente.getNome());
        stmt.setString(3, paziente.getCognome());
        stmt.setString(4, paziente.getUsername());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            rs.close();
            stmt.close();
            return true;
        } else {
            rs.close();
            stmt.close();
            return false;
        }
    }

    public String getUsernamePsicologo(String usernamePaziente) throws SQLException{
        String usernamePsicologo=null;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();
        String sql=" SELECT " + PSICOLOGO_USERNAME + " " +
                "FROM " + TABELLA_PAZIENTE + " " +
                "WHERE " +  PAZIENTE_USERNAME + " = ? ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1,usernamePaziente);

        ResultSet rs= stmt.executeQuery();

        if(rs.next()) {
            usernamePsicologo=rs.getString(1);
        }

        rs.close();
        stmt.close();
        return usernamePsicologo;
    }
 }

