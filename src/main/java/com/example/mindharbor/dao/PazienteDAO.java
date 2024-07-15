package com.example.mindharbor.dao;

import com.example.mindharbor.dao.query_sql.QuerySQLPazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.user_type.UserType;
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

public class PazienteDAO extends QuerySQLPazienteDAO {

    public List<Paziente> trovaPaziente(String usernamePsicologo) throws DAOException {
        List<Paziente> pazienteList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.TROVA_PAZIENTE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, usernamePsicologo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Paziente paziente = new Paziente(rs.getString(1),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getInt(2));

                    pazienteList.add(paziente);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return pazienteList;
    }

    public Paziente getInfoSchedaPersonale(String username) throws DAOException{
        Paziente paziente = null;

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.INFO_SCHEDA_PERSONALE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    List<Object> parametri = new ArrayList<>();
                    parametri.add(rs.getString(4));
                    parametri.add("");
                    parametri.add(rs.getInt(3));

                    paziente = new Paziente("", rs.getString(1), rs.getString(2), UserType.PAZIENTE, rs.getString(5), "", parametri);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return paziente;
    }

    public Paziente getInfoPaziente(Utente utente) throws DAOException {
        Paziente paziente=null;

        PreparedStatement stmt;
        Connection conn;

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
            List<Object> parametri=new ArrayList<>();
            parametri.add("");
            parametri.add("");
            parametri.add( rs.getInt(3));
            paziente = new Paziente("", rs.getString(1), rs.getString(2), UserType.PAZIENTE,  rs.getString(4),"",parametri);
        }
        rs.close();
        stmt.close();

        return paziente;
    }

    public String TrovaNomeCognomePsicologo(String usernamePaziente) throws SQLException {
        String nomePsicologo=null;

        PreparedStatement stmt;
        Connection conn;

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

        PreparedStatement stmt;
        Connection conn;

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

    public boolean checkPaziente(Paziente paziente) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql= "SELECT * " +
                "FROM " + TABELLA_PAZIENTE + " " +
                "JOIN " + TABELLA_UTENTE + " ON " +
                TABELLA_PAZIENTE + "." + PAZIENTE_USERNAME + " = " + TABELLA_UTENTE + "." + USERNAME + " " +
                "WHERE " + TABELLA_PAZIENTE + "." + ETA + " = ? AND " + TABELLA_UTENTE + "." + NOME + " = ? " +
                "AND " + TABELLA_UTENTE + "." + COGNOME + " = ? AND " + TABELLA_UTENTE + "." + USERNAME + " = ? ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, (Integer) paziente.getParametri().get(2));
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

    public String getUsernamePsicologo(Utente paziente) throws SQLException{
        String usernamePsicologo=null;

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();
        String sql=" SELECT " + PSICOLOGO_USERNAME + " " +
                "FROM " + TABELLA_PAZIENTE + " " +
                "WHERE " +  PAZIENTE_USERNAME + " = ? ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1,paziente.getUsername());

        ResultSet rs= stmt.executeQuery();

        if(rs.next()) {
            usernamePsicologo=rs.getString(1);
        }

        rs.close();
        stmt.close();
        return usernamePsicologo;
    }

    public void aggiungiPsicologoAlPaziente(Appuntamento appuntamento) {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String updateQuery= "UPDATE " + TABELLA_PAZIENTE + " " +
                "SET " + PSICOLOGO_USERNAME +  " = ? " + " " +
                "WHERE " + PAZIENTE_USERNAME + " = ? ";
        stmt = conn.prepareStatement(updateQuery);
        stmt.setString(1, appuntamento.getPsicologo().getUsername());
        stmt.setString(2, appuntamento.getPaziente().getUsername());

        stmt.executeUpdate();

        stmt.close();
    }
 }

