package com.example.mindharbor.dao;

import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.*;
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
    protected static final String ID_APPUNTAMENTO="ID_Appuntamento";
    protected static final String STATO_APPUNTAMENTO="StatoAppuntamento";
    protected static final String STATO_NOTIFICA="statoNotificaRichiesta";
    protected static final String GENERE= "Genere";
    protected static final String STATO_NOTIFICA_PAZIENTE="statoNotificaPaziente";


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
                    "WHERE a." + USERNAME_PSICOLOGO + " = ? AND " + STATO_APPUNTAMENTO + " = 1 ";

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
                    null,
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
                    "WHERE a." + USERNAME_PAZIENTE + " = ? AND " + STATO_APPUNTAMENTO + " = 1 ";

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
                    null,
                    new Paziente(rs.getString(7),rs.getString(5), rs.getString(6)),
                    new Psicologo(rs.getString(8), rs.getString(3), rs.getString(4)));

            appuntamentoList.add(appuntamento);
        }

        rs.close();
        stmt.close();

        return appuntamentoList;
    }

    public void insertRichiestaAppuntamento(Appuntamento appuntamento) throws SQLException{
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql= "INSERT INTO " + TABELLA_APPUNTAMENTO + " ( " +
                ID_APPUNTAMENTO + ", " +
                DATA + ", " +
                ORA + " , " +
                USERNAME_PAZIENTE + " , " +
                USERNAME_PSICOLOGO + " , " +
                STATO_APPUNTAMENTO + " , " +
                STATO_NOTIFICA + " , " +
                STATO_NOTIFICA_PAZIENTE + " ) " +
            "VALUES (DEFAULT, ? , ? , ? , ? , DEFAULT, DEFAULT, DEFAULT ) ";

        stmt = conn.prepareStatement(sql);
        stmt.setDate(1, Date.valueOf(appuntamento.getData()));
        stmt.setString(2, appuntamento.getOra());
        stmt.setString(3,appuntamento.getPaziente().getUsername()) ;
        stmt.setString(4, appuntamento.getPsicologo().getUsername());

        stmt.executeUpdate();
        stmt.close();
    }

    public Integer getNumRicAppDaNotificare(String usernamePsicologo,String usernamePaziente) throws SQLException {
        int count=0;

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        if(usernamePaziente==null && usernamePsicologo!=null) {

            String sql = "SELECT COUNT(*) AS Total " +
                    "FROM " + TABELLA_APPUNTAMENTO + " " +
                    "WHERE " + USERNAME_PSICOLOGO + " = ?  AND " + STATO_NOTIFICA + " = 1 ";
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePsicologo);

        }else {
            String sql = "SELECT COUNT(*) AS Total " +
                    "FROM " + TABELLA_APPUNTAMENTO + " " +
                    "WHERE " + USERNAME_PAZIENTE + " = ?  AND " + STATO_NOTIFICA_PAZIENTE + " = 1 ";
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePaziente);


        }

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            count = rs.getInt("Total");
        }
        rs.close();
        stmt.close();

        return count;
    }

    public List<Appuntamento> trovaRichiesteAppuntamento(String usernamePsicologo) throws SQLException {
        List<Appuntamento> richiesteAppuntamento=new ArrayList<>();
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql="SELECT " +
                " u. " + NOME_PAZIENTE + " , " +
                " u. " + COGNOME_PAZIENTE + " , " +
                " a. " + STATO_NOTIFICA + " , " +
                " u. " + GENERE + " , " +
                " a. " + ID_APPUNTAMENTO + " " +
                "FROM " + TABELLA_APPUNTAMENTO + " a " +
                "JOIN " + TABELLA_UTENTE + " u ON u." + USERNAME + " = a." + USERNAME_PAZIENTE + " " +
                "WHERE " + STATO_APPUNTAMENTO + " = 0 " + " AND " + USERNAME_PSICOLOGO + " = ? ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePsicologo);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Appuntamento richiesta= new Appuntamento(null,
                    null,
                    rs.getInt(5),
                    new Paziente(null,"","", rs.getString(1), rs.getString(2), UserType.PAZIENTE,rs.getString(4),"",""),
                    null,
                    rs.getInt(3));

            richiesteAppuntamento.add(richiesta);
        }

        rs.close();
        stmt.close();

        return richiesteAppuntamento;

    }

    public void updateStatoNotifica(Integer idRichiesta) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String updateQuery= "UPDATE " + TABELLA_APPUNTAMENTO + " " +
                "SET " + STATO_NOTIFICA +  " = 0 " + " " +
                "WHERE " + ID_APPUNTAMENTO + " = ? ";
        stmt = conn.prepareStatement(updateQuery);
        stmt.setInt(1, idRichiesta);

        stmt.executeUpdate();

        stmt.close();

    }


    public Appuntamento getInfoRichiesta(Integer idAppuntamento) throws SQLException {
        Appuntamento richiesta=null;

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql="SELECT " +
                " u. " + NOME_PAZIENTE + " , " +
                " u. " + COGNOME_PAZIENTE + " , " +
                " a. " + DATA + " , " +
                " a. " + ORA + " , " +
                " u. " + GENERE + " " +
                "FROM " + TABELLA_APPUNTAMENTO + " a " +
                "JOIN " + TABELLA_UTENTE + " u ON u. " + USERNAME + " = a. " + USERNAME_PAZIENTE + " " +
                "WHERE " + ID_APPUNTAMENTO + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, idAppuntamento);

        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            richiesta= new Appuntamento(rs.getString(3),
                    rs.getString(4),
                    null,
                    new Paziente(0,"","",rs.getString(1),rs.getString(2),UserType.PAZIENTE,rs.getString(5),"",""),
                    null,
                    null);
        }

        return richiesta;

    }

    public void updateRichiesta(Integer idAppuntamento) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String updateQuery= "UPDATE " + TABELLA_APPUNTAMENTO + " " +
                "SET " + STATO_APPUNTAMENTO +  " = 1 " + " , " + STATO_NOTIFICA_PAZIENTE + " = 1 " + " " +
                "WHERE " + ID_APPUNTAMENTO + " = ? ";
        stmt = conn.prepareStatement(updateQuery);
        stmt.setInt(1, idAppuntamento);

        stmt.executeUpdate();

        stmt.close();

    }

    public void deleteRichiesta(Integer idAppuntamento) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String deleteQuery= "DELETE FROM " + TABELLA_APPUNTAMENTO + " " +
                "WHERE " + ID_APPUNTAMENTO + " = ? ";
        stmt = conn.prepareStatement(deleteQuery);
        stmt.setInt(1, idAppuntamento);

        stmt.executeUpdate();

        stmt.close();
    }

    public boolean getDisponibilità(Integer idAppuntamento, String usernamePsicologo) throws SQLException{
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();
        String sql="SELECT " + " a. " + ID_APPUNTAMENTO + " " +
                "FROM " + TABELLA_APPUNTAMENTO + " a " +
                "WHERE " + " a. " + ID_APPUNTAMENTO + " = ? AND " + USERNAME_PSICOLOGO + " = ? " + " AND " +
                "NOT EXISTS ( SELECT 1 " +
                "FROM " + TABELLA_APPUNTAMENTO + " a1 " +
                "WHERE " + " a1. " + STATO_APPUNTAMENTO + " = 1 AND " +
                " a1. " + DATA + " = a. " + DATA + " AND " + " a1. " + USERNAME_PSICOLOGO + " = a. " + USERNAME_PSICOLOGO + " AND " +
                " a1. " + ORA + " = a. " + ORA + " ) ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1,idAppuntamento);
        stmt.setString(2, usernamePsicologo);

        ResultSet rs=stmt.executeQuery();

        if(rs.next()) {
            rs.close();
            stmt.close();
            return true;
        }
        rs.close();
        stmt.close();
        return false;
    }

    public void updateStatoNotificaPaziente(String usernamePaziente) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String updateQuery= "UPDATE " + TABELLA_APPUNTAMENTO + " " +
                "SET " + STATO_NOTIFICA_PAZIENTE +  " = 0 " +
                "WHERE " + USERNAME_PAZIENTE + " = ? AND " + STATO_NOTIFICA_PAZIENTE + " = 1 ";
        stmt = conn.prepareStatement(updateQuery);
        stmt.setString(1, usernamePaziente);

        stmt.executeUpdate();

        stmt.close();

    }
}

