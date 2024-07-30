package com.example.mindharbor.dao;

import com.example.mindharbor.dao.query_sql.QuerySQLUtenteDAO;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.session.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UtenteDao extends QuerySQLUtenteDAO {

    public Utente trovaUtente(Utente credenzialiUtenteLogin) throws DAOException {
        Utente utente=null;
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLUtenteDAO.CONTROLLO_CREDENZIALI, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, credenzialiUtenteLogin.getUsername());
            stmt.setString(2, credenzialiUtenteLogin.getPassword());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.first()) {
                    utente = getUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
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

    public Utente trovaNomeCognome(Utente utente) throws DAOException {
        Utente infoUtente = null;

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLUtenteDAO.TROVA_NOME_COGNOME, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, utente.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    infoUtente = new Utente("", rs.getString(1), rs.getString(2), "");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return infoUtente;
    }

    public List<Psicologo> listaUtentiDiTipoPsicologo(String usernamePsicologo) throws DAOException {
        //Questo metodo viene utilizzato nella prenotazione dell'appuntamento, quando il paziente deve visualizzare la lista degli psicologi, oppure, nel caso in cui
        //lui abbia già uno psicologo, solo quest'ultimo.
        //Il metodo ci ritorna il nome, il cognome, lo username e il genere dello psicologo o degli psicologi.

        List<Psicologo> listaPsicologi = new ArrayList<>();
        String sql = QuerySQLUtenteDAO.LISTA_PSICOLOGI;

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = (usernamePsicologo != null)
                ? conn.prepareStatement(sql + " AND " + USERNAME + " = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                : conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            if (usernamePsicologo != null) {
                stmt.setString(1, usernamePsicologo);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Psicologo psicologo = new Psicologo(rs.getString(3), rs.getString(1), rs.getString(2), rs.getString(4));
                    listaPsicologi.add(psicologo);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return listaPsicologi;
    }

    public List<Appuntamento> richiestaAppuntamentiInfoPaziente(List<Appuntamento> richiesteAppuntamenti) throws DAOException {
        Connection conn = ConnectionFactory.getConnection();
        try {
            for (Appuntamento appuntamento : richiesteAppuntamenti) {
                try (PreparedStatement stmt = conn.prepareStatement(QuerySQLUtenteDAO.TROVA_INFO_PAZIENTE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

                    stmt.setString(1, appuntamento.getPaziente().getUsername());

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            appuntamento.getPaziente().setNome(rs.getString(1));
                            appuntamento.getPaziente().setCognome(rs.getString(2));
                            appuntamento.getPaziente().setGenere(rs.getString(3));
                        }
                    }
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return richiesteAppuntamenti;
    }


    public Utente trovaInfoUtente(Utente paziente) throws DAOException {
        Utente infoUtente = null;

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLUtenteDAO.TROVA_INFO_PAZIENTE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, paziente.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    infoUtente = new Utente("",rs.getString(1), rs.getString(2), rs.getString(3));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return infoUtente;
    }


}
