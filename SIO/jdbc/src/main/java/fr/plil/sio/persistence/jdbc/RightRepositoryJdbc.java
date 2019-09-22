package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Repository
public class RightRepositoryJdbc implements RightRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Right> findByName(String name) {
        List<Right> rights = new LinkedList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "SELECT * FROM RIGHT_T WHERE NAME_C = ?";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request);

            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Right right = new Right();
                right.setId(rs.getLong(1));
                right.setName(rs.getString(3));

                rights.add(right);
            }

            return rights;
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
    public Right findOne(Long id) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "SELECT * FROM RIGHT_T WHERE RIGHT_ID = ?";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request);

            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Right right = new Right();
                right.setId(rs.getLong(1));
                right.setName(rs.getString(3));
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

        return null;
    }

    @Override
    public void delete(Long name) {

    }

    @Override
    public void save(Right right) {
        Statement stmt = null;
        ResultSet rs = null;
        Integer affectedRows = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "INSERT INTO RIGHT_T (NAME_C) VALUES (?)";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, right.getName());
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    right.setId(rs.getLong(1));
                }
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
}
