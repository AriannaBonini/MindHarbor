package com.example.mindharbor.dao;

import com.example.mindharbor.dao.query_sql.QuerySQLAppuntamentoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Utente;
import com.example.mindharbor.user_type.UserType;
import com.example.mindharbor.model.Appuntamento;
import com.example.mindharbor.model.Paziente;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.ConnectionFactory;
import com.example.mindharbor.utilities.HelperDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppuntamentoDAO extends QuerySQLAppuntamentoDAO implements HelperDAO {
    public List<Appuntamento> trovaAppuntamenti(Utente utente, String selectedTabName)  throws DAOException {
        List<Appuntamento> appuntamentoList = new ArrayList<>();

        String sql = QuerySQLAppuntamentoDAO.TROVA_APPUNTAMENTI;

        if (selectedTabName.equals("IN PROGRAMMA")) {
            sql += QuerySQLAppuntamentoDAO.TROVA_APPUNTAMENTI_IN_PROGRAMMA;
        } else {
            sql += QuerySQLAppuntamentoDAO.TROVA_APPUNTAMENTI_PASSATI;
        }

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = createPreparedStatement(conn, sql, utente);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Appuntamento appuntamento = new Appuntamento(
                        rs.getString(1),
                        rs.getString(2),
                        null,
                        new Paziente(rs.getString(7), rs.getString(5), rs.getString(6)),
                        new Psicologo(rs.getString(8), rs.getString(3), rs.getString(4))
                );

                appuntamentoList.add(appuntamento);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return appuntamentoList;
    }

    public void insertRichiestaAppuntamento(Appuntamento appuntamento) throws DAOException{
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.INSERISCI_RICHIESTA_APPUNTAMENTO)) {

            stmt.setDate(1, Date.valueOf(appuntamento.getData()));
            stmt.setString(2, appuntamento.getOra());
            stmt.setString(3, appuntamento.getPaziente().getUsername());
            stmt.setString(4, appuntamento.getPsicologo().getUsername());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public Integer getNumRicAppDaNotificare(Utente utente) throws DAOException {
        int count = 0;
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = createPreparedStatement(conn,utente);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("Total");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return count;
    }

    public List<Appuntamento> trovaRichiesteAppuntamento(Utente utente) throws DAOException {
        List<Appuntamento> richiesteAppuntamento = new ArrayList<>();

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.TROVA_RICHIESTE_APPUNTAMENTI_PSICOLOGO, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, utente.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appuntamento richiesta = new Appuntamento(
                            rs.getInt(2),
                            new Paziente(rs.getString(3)),
                            rs.getInt(1)
                    );

                    richiesteAppuntamento.add(richiesta);
                }
            }
            richiesteAppuntamento=new UtenteDao().richiestaAppuntamentiInfoPaziente(richiesteAppuntamento);

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return richiesteAppuntamento;
    }

    public void updateStatoNotifica(Appuntamento richiestaAppuntamento) throws DAOException {

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.UPDATE_STATO_NOTIFICA_PSICOLOGO)) {

            stmt.setInt(1, richiestaAppuntamento.getIdAppuntamento());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }


    public Appuntamento getInfoRichiesta(Appuntamento richiestaAppuntamento) throws DAOException {
        Appuntamento richiesta = null;

        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.INFORMAZIONI_RICHIESTA_APPUNTAMENTO, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setInt(1, richiestaAppuntamento.getIdAppuntamento());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    richiesta = new Appuntamento(
                            rs.getString(1),
                            rs.getString(2)
                    );
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return richiesta;
    }

    public void updateRichiesta(Appuntamento appuntamento) throws DAOException {

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.RICHIESTA_DI_APPPUNTAMENTO_ACCETTATA)) {

            stmt.setInt(1, appuntamento.getIdAppuntamento());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    // Chiamata al metodo per aggiungere lo psicologo al paziente fuori dal blocco try-with-resources
        new PazienteDAO().aggiungiPsicologoAlPaziente(appuntamento);
    }


    public void eliminaRichiesteDiAppuntamentoPerAltriPsicologi(Appuntamento appuntamento) throws DAOException{
        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.ELIMINA_RICHIESTE_DI_APPUNTAMENTO)) {

            stmt.setString(1, appuntamento.getPaziente().getUsername());
            stmt.setString(2,appuntamento.getPsicologo().getUsername());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }


    public void eliminaRichiesta(Appuntamento appuntamento) throws DAOException {

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.ELIMINA_RICHIESTA_DI_APPUNTAMENTO)) {

            stmt.setInt(1, appuntamento.getIdAppuntamento());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean getDisp(Integer idAppuntamento, Utente utente) throws DAOException{
        boolean disponibile = false;

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.CONTROLLA_DISPONIBILITA, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setInt(1, idAppuntamento);
            stmt.setString(2, utente.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    disponibile = true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return disponibile;
    }

    public void updateStatoNotificaPaziente(Utente utente) throws DAOException {

        Connection conn = ConnectionFactory.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLAppuntamentoDAO.UPDATE_STATO_NOTIFICA_PAZIENTE)) {

            stmt.setString(1, utente.getUsername());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, String sql, Utente utente) throws DAOException {
        String sqlQuery;
        if (utente.getUserType().equals(UserType.PSICOLOGO)) {
            sqlQuery = sql + QuerySQLAppuntamentoDAO.CONFRONTO_USERNAME_PSICOLOGO;
        } else {
            sqlQuery = sql + QuerySQLAppuntamentoDAO.CONFRONTO_USERNAME_PAZIENTE;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, utente.getUsername());
            return stmt; // Restituisci il PreparedStatement preparato

        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn, Utente utente) throws DAOException{
        String sql;
        if (utente.getUserType().equals(UserType.PSICOLOGO)) {
            sql = QuerySQLAppuntamentoDAO.NUMERO_RICHIESTE_APPUNTAMENTI_DA_NOTIFICARE_PSICOLOGO;
        } else {
            sql = QuerySQLAppuntamentoDAO.NUMERO_NUOVI_APPUNTAMENTI_DA_NOTIFICARE_PAZIENTE;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, utente.getUsername());
            return stmt;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}

