package fr.plil.sio.persistence.jdbc;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractServiceSupport {

    private static final String create_table_group = "CREATE TABLE GROUP_T (GROUP_ID INT NOT NULL AUTO_INCREMENT, " +
            "NAME_C VARCHAR(50) UNIQUE NOT NULL, PRIMARY KEY(GROUP_ID))";

    private static final String drop_table_group = "DROP TABLE GROUP_T";

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    private Statement stmt;

    @Before
    public void createTables() throws SQLException {
        openConnection();
        stmt.executeUpdate(create_table_group);
        closeConnection();
    }

    @After
    public void cleanupDatabase() throws SQLException {
        openConnection();
        stmt.executeUpdate(drop_table_group);
        closeConnection();
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
