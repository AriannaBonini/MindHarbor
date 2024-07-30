package com.example.mindharbor.dao;

import com.example.mindharbor.dao.query_sql.QuerySQLPazienteDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.session.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PazienteDAO extends QuerySQLPazienteDAO {

    public List<Paziente> trovaPaziente(Utente psicologo) throws DAOException {
        //Questo metodo viene utilizzato per prendere dalla persistenza lo username, il nome, il cognome e il genere del Paziente.
        //Viene utilizzato dallo psicologo per ottenere la lista dei suoi pazienti.
        List<Paziente> pazienteList = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.TROVA_PAZIENTE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, psicologo.getUsername());
            //Con questa query sql ottengo lo username di tutti i pazienti di un determinato psicologo.

            UtenteDao utenteDao=new UtenteDao();
            Utente utente;

            TestPsicologicoDAO testPsicologicoDAO=new TestPsicologicoDAO();
            Paziente numeroTestPaziente;



            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Paziente paziente = new Paziente(rs.getString(1));
                    utente=utenteDao.trovaInfoUtente(paziente);

                    paziente.setNome(utente.getNome());
                    paziente.setCognome(utente.getCognome());
                    paziente.setGenere(utente.getGenere());


                    numeroTestPaziente=testPsicologicoDAO.numTestSvoltiPerPaziente(paziente);
                    //con questa chiamata otteniamo il numero dei test svolti dal paziente da notificare allo psicologo
                    paziente.setNumeroTest(numeroTestPaziente.getNumeroTest());

                    pazienteList.add(paziente);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return pazienteList;
    }

    public Paziente getInfoSchedaPersonale(Paziente pazienteSelezionato) throws DAOException{
        //questo metodo viene utilizzato per prendere dalla persistenza la diagnosi e l'età del paziente.

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.INFO_SCHEDA_PERSONALE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, pazienteSelezionato.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pazienteSelezionato.setAnni(rs.getInt(1));
                    pazienteSelezionato.setDiagnosi(rs.getString(2));
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return pazienteSelezionato;
    }

    public boolean checkAnniPaziente(Paziente paziente) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.CHECK_ANNI_PAZIENTE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, paziente.getUsername());
            stmt.setInt(2, paziente.getAnni());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public String getUsernamePsicologo(Utente paziente) throws DAOException{
        String usernamePsicologo = null;
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.USERNAME_PSICOLOGO, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, paziente.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usernamePsicologo = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return usernamePsicologo;
    }

    public void aggiungiPsicologoAlPaziente(Appuntamento appuntamento) throws DAOException{

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPazienteDAO.AGGIUNGI_PSICOLOGO_AL_PAZIENTE)) {

            stmt.setString(1, appuntamento.getPsicologo().getUsername());
            stmt.setString(2, appuntamento.getPaziente().getUsername());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
 }

