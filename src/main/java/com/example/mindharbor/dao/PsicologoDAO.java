package com.example.mindharbor.dao;

import com.example.mindharbor.Enum.UserType;
import com.example.mindharbor.model.Psicologo;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PsicologoDAO {
    protected static final String USERNAME = "Psicologo_Username";
    protected static final String TABELLA_PSICOLOGO = "psicologo";
    protected static final String UTENTE_NOME = "Nome";
    protected static final String UTENTE_COGNOME = "Cognome";
    protected static final String UTENTE_CATEGORIA = "Categoria";
    protected static final String UTENTE_GENERE = "Genere";
    protected static final String TABELLA_UTENTE = "utente";
    protected static final String UTENTE_USERNAME = "Username";

    public List<Psicologo> ListaPsicologi(String usernamePaziente) throws SQLException {

        List<Psicologo> listaPsicologi = new ArrayList<>();
        String usernamePsicologo;
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

            String sql = "SELECT " + UTENTE_NOME + " ," + UTENTE_COGNOME + " ," + USERNAME + " , " + UTENTE_GENERE + " " +
                    "FROM " + TABELLA_PSICOLOGO + " " +
                    "JOIN " + TABELLA_UTENTE + " ON " +
                    TABELLA_UTENTE + "." + UTENTE_USERNAME + " = " + TABELLA_PSICOLOGO + "." + USERNAME + " " +
                    "WHERE " + UTENTE_CATEGORIA + " = 'Psicologo' ";

            if((usernamePsicologo=new PazienteDAO().getUsernamePsicologo(usernamePaziente))!=null) {
                sql += "AND " + TABELLA_UTENTE + "." + UTENTE_USERNAME + " = ?";
                stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1,usernamePsicologo);
            } else {
                stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Psicologo psicologo = new Psicologo(0, "", rs.getString(3), rs.getString(1), rs.getString(2), UserType.PSICOLOGO, rs.getString(4), " ");

                listaPsicologi.add(psicologo);
            }
            rs.close();
            stmt.close();
            return listaPsicologi;
    }
}
