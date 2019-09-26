package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import fr.plil.sio.persistence.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserRepositoryJdbc implements UserRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void save(User user) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "INSERT INTO USER_T (NAME_C, GROUP_ID) VALUES (?, ?)";
            stmt = dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setLong(2, user.getGroup().getId());

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
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
    public User findByName(String name) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT * FROM USER_T WHERE NAME_C = ?";
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
    public int delete(String name) {
        PreparedStatement stmt = null;

        try {
            String request = "DELETE FROM USER_T WHERE NAME_C = ?";
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

    @Override
    public int deleteByGroupId(Long id) {
        PreparedStatement stmt = null;

        try {
            String request = "DELETE FROM USER_T WHERE GROUP_ID = ?";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setLong(1, id);

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

    private User buildObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("USER_ID"));
        user.setName(rs.getString("NAME_C"));

        Group group = new Group();
        group.setId(rs.getLong("GROUP_ID"));

        user.setGroup(group);

        return user;
    }
}
