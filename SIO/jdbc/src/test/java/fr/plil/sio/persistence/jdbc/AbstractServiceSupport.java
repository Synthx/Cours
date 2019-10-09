package fr.plil.sio.persistence.jdbc;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServiceSupport {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void createTables() {
        List<String> requests = new ArrayList<>();
        requests.add("CREATE TABLE GROUP_T (GROUP_ID INT NOT NULL AUTO_INCREMENT, NAME_C VARCHAR(50) UNIQUE NOT NULL, " +
                "PRIMARY KEY(GROUP_ID))");
        requests.add("CREATE TABLE RIGHT_T (RIGHT_ID INT NOT NULL AUTO_INCREMENT, PARENT_ID INT, " +
                "NAME_C VARCHAR(50) NOT NULL, PRIMARY KEY(RIGHT_ID), FOREIGN KEY(PARENT_ID) REFERENCES RIGHT_T(RIGHT_ID))");
        requests.add("CREATE TABLE GROUP_RIGHT_T (GROUP_ID INT NOT NULL, RIGHT_ID INT NOT NULL, PRIMARY KEY(GROUP_ID, RIGHT_ID)," +
                "FOREIGN KEY(GROUP_ID) REFERENCES GROUP_T(GROUP_ID), FOREIGN KEY(RIGHT_ID) REFERENCES RIGHT_T(RIGHT_ID))");
        requests.add("CREATE TABLE USER_T (USER_ID INT NOT NULL AUTO_INCREMENT, NAME_C VARCHAR(50) UNIQUE NOT NULL, " +
                "GROUP_ID INT, PRIMARY KEY(USER_ID), FOREIGN KEY(GROUP_ID) REFERENCES GROUP_T(GROUP_ID))");

        for (String request : requests) {
            this.jdbcTemplate.execute(request);
        }
    }

    @After
    public void cleanupDatabase() throws SQLException {
        List<String> requests = new ArrayList<>();
        requests.add("DROP TABLE GROUP_T");
        requests.add("DROP TABLE RIGHT_T");
        requests.add("DROP TABLE GROUP_RIGHT_T");
        requests.add("DROP TABLE USER_T");

        for (String request : requests) {
            this.jdbcTemplate.execute(request);
        }
    }
}
