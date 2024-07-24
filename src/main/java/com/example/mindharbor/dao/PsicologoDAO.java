package com.example.mindharbor.dao;

import com.example.mindharbor.dao.query_sql.QuerySQLPsicologoDAO;
import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PsicologoDAO extends QuerySQLPsicologoDAO {

    public Psicologo getInfoPsicologo(Psicologo psicologo) throws DAOException{
        Connection conn = ConnectionFactory.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(QuerySQLPsicologoDAO.INFO_PSICOLOGO, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            stmt.setString(1, psicologo.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    psicologo.setCostoOrario(rs.getInt(1));
                    psicologo.setNomeStudio(rs.getString(2));
                }
            }
        } catch (SQLException e) {
           throw new DAOException(e.getMessage());
        }

        return psicologo;
    }
}
