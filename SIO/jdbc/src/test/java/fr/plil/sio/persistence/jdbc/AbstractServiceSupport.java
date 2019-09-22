package fr.plil.sio.persistence.jdbc;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServiceSupport {

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    private Statement stmt;

    @Before
    public void createTables() throws SQLException {
        List<String> requests = new ArrayList<>();
        requests.add("CREATE TABLE GROUP_T (GROUP_ID INT NOT NULL AUTO_INCREMENT, NAME_C VARCHAR(50) UNIQUE NOT NULL, " +
                "PRIMARY KEY(GROUP_ID))");
        requests.add("CREATE TABLE RIGHT_T (RIGHT_ID INT NOT NULL AUTO_INCREMENT, PARENT_ID INT, " +
                "NAME_C VARCHAR(50) NOT NULL, PRIMARY KEY(RIGHT_ID), FOREIGN KEY(PARENT_ID) REFERENCES RIGHT_T(RIGHT_ID))");

        this.openConnection();

        for (String request : requests) {
            this.stmt.executeUpdate(request);
        }

        this.closeConnection();
    }

    @After
    public void cleanupDatabase() throws SQLException {
        List<String> requests = new ArrayList<>();
        requests.add("DROP TABLE GROUP_T");
        requests.add("DROP TABLE RIGHT_T");

        this.openConnection();

        for (String request : requests) {
            this.stmt.executeUpdate(request);
        }

        this.closeConnection();
    }

    private void closeConnection() throws SQLException {
        stmt.close();
        connection.close();
    }

    private void openConnection() throws SQLException {
        connection = dataSource.getConnection();
        stmt = connection.createStatement();
    }
}
