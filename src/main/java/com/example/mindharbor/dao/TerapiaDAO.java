package com.example.mindharbor.dao;

import com.example.mindharbor.model.*;
import com.example.mindharbor.session.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TerapiaDAO {
    protected static final String PSICOLOGO= "Psicologo";
    protected static final String PAZIENTE="Paziente";
    protected static final String TERAPIA= "Terapia";
    protected static final String DATATERAPIA="DataOdierna";
    protected static final String DATATEST="DataTest";
    protected static final String TABELLA_TERAPIA="terapia";
    protected static final String NOTIFICAPAZIENTE="NotificaPaziente";

    public void insertTerapia(Terapia terapia) throws SQLException {

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql= "INSERT INTO " + TABELLA_TERAPIA + " ( " +
                PSICOLOGO + " , " +
                PAZIENTE + " , " +
                TERAPIA + " , " +
                DATATERAPIA + " , " +
                DATATEST + "," +
                NOTIFICAPAZIENTE + " ) " +
                "VALUES ( ? , ? , ? , ? , ?, DEFAULT ) " ;
        // TYPE_SCROLL_INSENSITIVE: ResultSet can be slided but is sensible to db data variations
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, terapia.getTestPsicologico().getPsicologo().getUsername());
        stmt.setString(2,terapia.getTestPsicologico().getPaziente().getUsername());
        stmt.setString(3,terapia.getTerapia());
        stmt.setDate(4, new java.sql.Date(terapia.getDataTerapia().getTime()));
        stmt.setDate(5, new java.sql.Date(terapia.getTestPsicologico().getData().getTime()));

        stmt.executeUpdate();
        stmt.close();

    }

    public List<Terapia> getTerapie(String usernamePaziente) throws  SQLException{
        List<Terapia> terapie= new ArrayList<>();

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql="SELECT " +  PSICOLOGO + "," + TERAPIA + "," + DATATERAPIA + " " +
                "FROM " + TABELLA_TERAPIA + " " +
                "WHERE " + PAZIENTE + " = ? ";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1,usernamePaziente);

        ResultSet rs= stmt.executeQuery();

        while (rs.next()) {
            Terapia terapia= new Terapia(new TestPsicologico(null,null, new Psicologo(rs.getString(1)), null, null, null),
                    rs.getString(2),
                    rs.getDate(3));

            terapie.add(terapia);
        }
        rs.close();
        stmt.close();

        aggiornaStatoNotificaPaziente(usernamePaziente);

        return terapie;

    }

    private void aggiornaStatoNotificaPaziente(String usernamePaziente) throws SQLException {
        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String updateQuery= "UPDATE " + TABELLA_TERAPIA + " " +
                "SET " + NOTIFICAPAZIENTE +  " = 0 " + " " +
                "WHERE " + PAZIENTE + " = ? ";
        stmt = conn.prepareStatement(updateQuery);
        stmt.setString(1,usernamePaziente);

        stmt.executeUpdate();

        stmt.close();
    }

    public Integer getNuoveTerapie(String usernamePaziente) throws SQLException{
        int count=0;

        PreparedStatement stmt;
        Connection conn;

        conn = ConnectionFactory.getConnection();

        String sql= "SELECT COUNT(*) AS Total " +
                "FROM " + TABELLA_TERAPIA + " " +
                "WHERE " + PAZIENTE + " = ?  AND " + NOTIFICAPAZIENTE + " = 1 ";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, usernamePaziente);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            count = rs.getInt("Total");
        }
        rs.close();
        stmt.close();

        return count;
    }
}
