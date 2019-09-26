package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class GroupRepositoryJdbc implements GroupRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public Group findByName(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT * FROM GROUP_T WHERE NAME_C = ?";
            stmt = dataSource.getConnection().prepareStatement(request);
            stmt.setString(1, name);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return this.buildObject(rs);
            } else {
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
    public Group findById(Long id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT * FROM GROUP_T WHERE GROUP_ID = ?";
            stmt = dataSource.getConnection().prepareStatement(request);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return this.buildObject(rs);
            } else {
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
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "INSERT INTO GROUP_T (NAME_C) VALUES (?)";
            stmt = dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, group.getName());

            stmt.executeUpdate();
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
    public int delete(String name) {
        PreparedStatement stmt = null;

        try {
            String request = "DELETE FROM GROUP_T WHERE NAME_C = ?";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setString(1, name);

            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new UnsupportedOperationException("sql exception", e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new UnsupportedOperationException("sql exception during close", e);
            }
        }
    }

    private Group buildObject(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getLong("GROUP_ID"));
        group.setName(rs.getString("NAME_C"));

        return group;
    }
}
