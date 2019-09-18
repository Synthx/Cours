package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class GroupRepositoryJdbc implements GroupRepository {

    private static final Logger logger = LoggerFactory.getLogger(GroupRepository.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public Group findByName(String name) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dataSource.getConnection().createStatement();
            rs = stmt.executeQuery("SELECT * FROM GROUP_T WHERE NAME_C = \'" + name + "\'");
            if (rs.next()) {
                logger.debug("found group " + name);
                Group group = new Group();
                group.setId(rs.getLong("GROUP_ID"));
                group.setName(rs.getString("NAME_C"));
                return group;
            } else {
                logger.debug("not found " + name);
                return null;
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("sql exception", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new UnsupportedOperationException("sql exception during close", e);

            }
        }
    }

    @Override
    public void save(Group group) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dataSource.getConnection().createStatement();
            stmt.executeUpdate("INSERT INTO GROUP_T (NAME_C) VALUES (\'" + group.getName() + "\')",
                    Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                group.setId(rs.getLong(1));
            } else {
                throw new UnsupportedOperationException("default in key access");
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("sql exception", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new UnsupportedOperationException("sql exception during close", e);
            }
        }

    }

    @Override
    public void delete(Long id) {
        throw new IllegalStateException("not implemented !");
    }
}
