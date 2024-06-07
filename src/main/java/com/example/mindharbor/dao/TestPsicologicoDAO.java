package com.example.mindharbor.dao;

import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestPsicologicoDAO {

    protected static final String DATA="DataOdierna";
    protected static final String RISULTATO="Risultato";
    protected static final String PSICOLOGO="Psicologo";
    protected static final String PAZIENTE="Paziente";
    protected static final String TEST="Test";


    public void assegnaTest(String usernamePaziente,String usernamePsicologo, String nomeTest) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql = "INSERT INTO testpsicologico(DataOdierna, Risultato, Psicologo, Paziente, Test, Stato) VALUES (?,0,?,?,?, DEFAULT) ";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new Date(System.currentTimeMillis()));
        stmt.setString(2, usernamePsicologo);
        stmt.setString(3, usernamePaziente);
        stmt.setString(4, nomeTest);

        int rs = stmt.executeUpdate();
    }

    public int getTestAssegnato(String username) throws SQLException {
        int count=0;

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT COUNT(*) AS Total " +
                "FROM testpsicologico " +
                "WHERE Paziente = ? AND Stato = 1;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            count = rs.getInt("Total");
        }
        return count;
    }

    public void modificaStatoTest(String username) throws SQLException {

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String updateQuery = "UPDATE testpsicologico " +
                "SET Stato = 0 " +
                "WHERE Paziente = ? AND Stato = 1";

        stmt = conn.prepareStatement(updateQuery);
        stmt.setString(1, username);

        int rowsUpdated = stmt.executeUpdate();
    }

    public List<TestPsicologico> TrovaListaTest(String username) throws SQLException {

        List<TestPsicologico> testPsicologicoList= new ArrayList<>();

        PreparedStatement stmt = null;
        Connection conn = null;

        conn = ConnectionFactory.getConnection();

        String sql= "SELECT Psicologo, Risultato, Test, DataOdierna " +
                "FROM testpsicologico " +
                "WHERE Paziente = ? " ;

        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            TestPsicologico test= new TestPsicologico(rs.getDate(4),
                    rs.getInt(2),
                    rs.getString(1),
                    "",
                    rs.getString(3));

            testPsicologicoList.add(test);
        }

        rs.close();
        stmt.close();

        return testPsicologicoList;
    }
}
