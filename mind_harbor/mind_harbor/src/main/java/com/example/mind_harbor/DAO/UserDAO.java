package com.example.mind_harbor.DAO;

import com.example.mind_harbor.Model.Paziente;
import com.example.mind_harbor.Model.Utente;
import com.example.mind_harbor.Model.Psicologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    public UserDAO() {
        try (InputStream input = UserDAO.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                logger.error("Impossibile trovare le proprietà in config.properties");
                return;
            }
            // caricamento delle proprietà
            prop.load(input);

            // get delle proprietà
            jdbcURL = prop.getProperty("jdbcURL");
            jdbcUsername = prop.getProperty("jdbcUsername");
            jdbcPassword = prop.getProperty("jdbcPassword");

            /*
             * inizio debug
             */
            System.out.println(jdbcURL);
            /*
             * fine debug
             */

        } catch (Exception ex) {
            logger.error("Errore durante il caricamento del file di configurazione", ex);
        }
    }

    public Utente validate(String username, String password) throws SQLException {
        String LOGIN_QUERY = "SELECT u.Username, u.Ruolo FROM Utente u WHERE u.Username = ? AND u.Password = ?";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {
             preparedStatement.setString(1, username);
             preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String ruolo = resultSet.getString("Ruolo");
                // a seconda del ruolo si andrà ad eseguire un query con Join tra Utente e Paziente oppure tra Utente e Psicologo
                switch (ruolo){
                    case "Paziente":
                        return getPazienteDetails(username, connection);
                    case "Psicologo":
                        return getPsicologoDetails(username,connection);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw e;
        }
        return null;
    }
    private Paziente getPazienteDetails(String username, Connection connection) throws SQLException {
        String query = "SELECT u.*, p.Età, p.CodiceFiscale FROM Utente u JOIN Paziente p ON u.username = p.username WHERE u.username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Paziente paziente = new Paziente();
                // Imposta qui i dettagli specifici del Paziente
                paziente.setUsername(resultSet.getString("Username"));
                paziente.setRuolo(resultSet.getString("Ruolo"));
                paziente.setNome(resultSet.getString("Nome"));
                paziente.setCognome(resultSet.getString("Cognome"));
                paziente.setEta(resultSet.getInt("Età"));
                paziente.setCodiceFiscale(resultSet.getString("CodiceFiscale"));
                return paziente;
            }
        }
        return null;
    }

    private Psicologo getPsicologoDetails(String username, Connection connection) throws SQLException {
        String query = "SELECT u.*, s.CostoOrario, s.NomeStudio FROM Utente u JOIN Psicologo s ON u.username = s.username WHERE u.username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Psicologo psicologo = new Psicologo();
                // Imposta qui i dettagli specifici dello Psicologo
                psicologo.setUsername(resultSet.getString("Username"));
                psicologo.setRuolo(resultSet.getString("Ruolo"));
                psicologo.setNome(resultSet.getString("Nome"));
                psicologo.setCognome(resultSet.getString("Cognome"));
                psicologo.setCostoOrario(resultSet.getDouble("CostoOrario"));
                psicologo.setNomeStudio(resultSet.getString("NomeStudio"));
                return psicologo;
            }
        }
        return null;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
