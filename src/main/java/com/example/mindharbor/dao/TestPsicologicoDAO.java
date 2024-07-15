package com.example.mindharbor.dao;

import com.example.mindharbor.model.TestPsicologico;
import com.example.mindharbor.session.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestPsicologicoDAO {

    protected static final String DATA="DataOdierna";
    protected static final String RISULTATO="Risultato";
    protected static final String PSICOLOGO="Psicologo";
    protected static final String PAZIENTE="Paziente";
    protected static final String TEST="Test";
    protected static final String SVOLTO="Svolto";
    protected static final String STATOPSICOLOGO="statoNotificaPsicologo";
    protected static final String TABELLA_TESTPSICOLOGICO= "testpsicologico";
    protected static final String TERAPIA= "terapia";
    protected static final String STATO= "statoNotificaPaziente";
    protected static final String DATATEST="DataTest";


    public void assegnaTest(TestPsicologico test) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql = "INSERT INTO " + TABELLA_TESTPSICOLOGICO + " (" +
                DATA + ", " +
                RISULTATO + ", " +
                PSICOLOGO + ", " +
                PAZIENTE + ", " +
                TEST + ", " +
                STATO + ", " +
                SVOLTO + ", " +
                STATOPSICOLOGO + ") " +
                "VALUES (?, DEFAULT, ?, ?, ?, DEFAULT, DEFAULT, DEFAULT)";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        stmt.setString(2, test.getPsicologo().getUsername());
        stmt.setString(3, test.getPaziente().getUsername());
        stmt.setString(4, test.getTest());

        stmt.executeUpdate();
        stmt.close();
    }

    public Integer getNotificaPazientePerTestAssegnato(String usernamePaziente) throws SQLException {

        int count=0;
        Connection conn = ConnectionFactory.getConnection();

        String sql = "SELECT COUNT(*) AS Total " +
                "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                "WHERE " + PAZIENTE + " = ? AND " +  STATO + " = 1;";
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        PreparedStatement stmt = conn.prepareStatement(sql,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePaziente);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            count = rs.getInt("Total");
        }

        rs.close();
        stmt.close();

        return count;
    }

    public void modificaStatoNotificaTest(String usernamePsicologo, String notificato, String usernamePaziente) throws SQLException {

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        if(notificato.equalsIgnoreCase("paziente")) {

            String updateQuery = "UPDATE " +  TABELLA_TESTPSICOLOGICO + " " +
                    "SET " +  STATO + " = 0 " +
                    "WHERE " +  PAZIENTE +  " = ? AND " +  STATO + " = 1";

            stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, usernamePaziente);
        } else {
            String updateQuery= "UPDATE " + TABELLA_TESTPSICOLOGICO + " " +
                                "SET " + STATOPSICOLOGO +  " = 0 " +
                                "WHERE " + PSICOLOGO + " = ? AND " + STATOPSICOLOGO + " = 1 AND " +  PAZIENTE  + " = ? ";

            stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, usernamePsicologo);
            stmt.setString(2, usernamePaziente);

        }
        stmt.executeUpdate();
        stmt.close();
    }

    public List<TestPsicologico> TrovaListaTest(String usernamePaziente) throws SQLException {

        List<TestPsicologico> testPsicologicoList= new ArrayList<>();

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql= "SELECT " + RISULTATO + " , " + TEST + " , " +  DATA + " , " +  SVOLTO  + " " +
                "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                "WHERE " +  PAZIENTE + " = ? " ;

        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePaziente);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            TestPsicologico test= new TestPsicologico(rs.getDate(3),
                    rs.getInt(1),
                    null,
                    null,
                    rs.getString(2),
                    rs.getInt(4));

            testPsicologicoList.add(test);
        }

        rs.close();
        stmt.close();

        return testPsicologicoList;
    }

    public Integer trovaTestPassati(TestPsicologico testDaAggiungere) throws SQLException {

        Integer testPassati=null;
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql= "SELECT " + RISULTATO + " " +
                "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                "WHERE " + DATA + " = (SELECT MAX( " + DATA + ") " +
                "FROM " + TABELLA_TESTPSICOLOGICO + " "  +
                "WHERE " + PAZIENTE + " = ? AND " +  SVOLTO + " = 1 AND " + TEST + " = ? ) ";

        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(2, testDaAggiungere.getTest());
        stmt.setString(1,testDaAggiungere.getPaziente().getUsername());


        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            testPassati=rs.getInt(1);
        }

        rs.close();
        stmt.close();

        aggiornaTestAppenaSvolto(testDaAggiungere);
        return testPassati;

    }

    private void aggiornaTestAppenaSvolto(TestPsicologico testDaAggiungere) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String updateQuery= "UPDATE " +  TABELLA_TESTPSICOLOGICO + " " +
                "SET " + RISULTATO + " = ? , " +  SVOLTO + " = 1 , " +  STATOPSICOLOGO + " = 1  " +
                "WHERE " + PAZIENTE + " = ? AND " +  DATA + " = ? ";

        stmt = conn.prepareStatement(updateQuery);
        stmt.setInt(1, testDaAggiungere.getRisultato());
        stmt.setString(2, testDaAggiungere.getPaziente().getUsername());
        stmt.setDate(3, (java.sql.Date) testDaAggiungere.getData());

        stmt.executeUpdate();

        stmt.close();
    }

    public Integer getNumTestSvoltiDaNotificare(String usernamePsicologo, String usernamePaziente) throws SQLException {
        int count=0;

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        if (usernamePaziente.isEmpty()) {

            String sql = "SELECT COUNT(*) AS Total " +
                    "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                    "WHERE " + STATOPSICOLOGO + " = 1 AND " + PSICOLOGO + " = ? ";

            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePsicologo);

        } else {
            String sql = "SELECT COUNT(*) AS Total " +
                    "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                    "WHERE " + STATOPSICOLOGO + " = 1 AND " + PSICOLOGO + " = ? AND " +  PAZIENTE + " = ? ";

            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, usernamePsicologo);
            stmt.setString(2, usernamePaziente);

        }

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            count = rs.getInt("Total");
        }
        rs.close();
        stmt.close();

        return count;

    }

    public Integer getNumTestSvoltiSenzaPrescrizione(String usernamePsicologo, String usernamePaziente) throws SQLException {
        int count=0;

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql = "SELECT COUNT(*) AS Total " +
                "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                "WHERE " + SVOLTO + " = 1 AND " + PSICOLOGO + " = ? AND " + PAZIENTE + " = ? AND " +
                " NOT EXISTS ( " +
                "   SELECT 1 " +
                "   FROM " + TERAPIA + " " +
                "   WHERE " + TERAPIA + "." + PAZIENTE + " = " + TABELLA_TESTPSICOLOGICO + "." + PAZIENTE + " " +
                "     AND " + TERAPIA + "." + PSICOLOGO + " = " + TABELLA_TESTPSICOLOGICO + "." + PSICOLOGO + " " +
                "     AND " + TERAPIA + "." + DATATEST + " = " + TABELLA_TESTPSICOLOGICO + "." + DATA +
                ")";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePsicologo);
        stmt.setString(2, usernamePaziente);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            count = rs.getInt("Total");
        }

        System.out.println(count);
        rs.close();
        stmt.close();

        return count;

    }

    public List<TestPsicologico> ListaTestSvoltiSenzaPrescrizione(String usernamePaziente, String usernamePsicologo) throws SQLException {
        List<TestPsicologico> testSvolti= new ArrayList<>();

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql=  "SELECT " + DATA + ", " + RISULTATO + ", " + TEST + " " +
                "FROM " + TABELLA_TESTPSICOLOGICO + " " +
                "WHERE " + SVOLTO + " = 1 AND " + PSICOLOGO + " = ? AND " + PAZIENTE + " = ? " +
                "AND NOT EXISTS ( " +
                "SELECT 1 " +
                "FROM " + TERAPIA + " " +
                "WHERE " + TABELLA_TESTPSICOLOGICO + "." + PSICOLOGO + " = " + TERAPIA + "." + PSICOLOGO + " " +
                "AND " + TABELLA_TESTPSICOLOGICO + "." + PAZIENTE + " = " + TERAPIA + "." + PAZIENTE + " " +
                "AND " + TABELLA_TESTPSICOLOGICO + "." + DATA + " = " + TERAPIA + "." +  DATATEST +  " )";


        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePsicologo);
        stmt.setString(2, usernamePaziente);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            TestPsicologico testSvolto= new TestPsicologico(rs.getDate(1),
                    rs.getInt(2),
                    null,
                    null,
                    rs.getString(3),
                    null);

            testSvolti.add(testSvolto);
        }
        rs.close();
        stmt.close();

        return testSvolti;
    }
}
