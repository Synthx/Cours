package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class RightRepositoryJdbc implements RightRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Right> findByName(String name) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "SELECT * FROM RIGHT_T WHERE NAME_C = ?";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request);

            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

            return this.getList(rs);
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
                right.setId(rs.getLong("RIGHT_ID"));
                right.setName(rs.getString("NAME_C"));

                return right;
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
    public int delete(Long id) {
        Statement stmt = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "DELETE FROM RIGHT_T WHERE RIGHT_ID = ?";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();
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
    public void save(Right right) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "INSERT INTO RIGHT_T (NAME_C) VALUES (?)";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, right.getName());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                rs = preparedStatement.getGeneratedKeys();
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

    @Override
    public void save(Right right, Right parent) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "INSERT INTO RIGHT_T (NAME_C, PARENT_ID) VALUES (?, ?)";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, right.getName());
            preparedStatement.setLong(2, parent.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                rs = preparedStatement.getGeneratedKeys();
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

    @Override
    public List<Right> findByParentId(Long id) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "SELECT * FROM RIGHT_T WHERE PARENT_ID = ?";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request);

            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();

            return this.getList(rs);
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
    public List<Right> findByGroupId(Long id) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.dataSource.getConnection().createStatement();

            String request = "SELECT * FROM RIGHT_T WHERE GROUP_ID = ?";
            PreparedStatement preparedStatement = this.dataSource.getConnection().prepareStatement(request);

            preparedStatement.setLong(1, id);
            rs = preparedStatement.executeQuery();

            return this.getList(rs);
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

    private List<Right> getList(ResultSet rs) {
        List<Right> rights = new ArrayList<>();

        try {
            while (rs.next()) {
                Right right = new Right();
                right.setId(rs.getLong("RIGHT_ID"));
                right.setName(rs.getString("NAME_C"));

                rights.add(right);
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("sql exception", e);
        }

        return rights;
    }
}
