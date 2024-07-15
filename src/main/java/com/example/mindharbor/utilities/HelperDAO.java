package com.example.mindharbor.utilities;

import com.example.mindharbor.exceptions.DAOException;
import com.example.mindharbor.model.Utente;
import java.sql.Connection;
import java.sql.PreparedStatement;

public interface HelperDAO  {
     PreparedStatement createPreparedStatement(Connection conn, String sql, Utente utente) throws DAOException;
     PreparedStatement createPreparedStatement(Connection conn, Utente utente) throws DAOException;

}
