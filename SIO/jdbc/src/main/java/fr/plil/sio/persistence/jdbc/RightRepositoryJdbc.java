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
import java.util.List;

@Repository
public class RightRepositoryJdbc implements RightRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Right> findByName(String name) {
        List<Right> rights = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT * FROM RIGHT_T WHERE NAME_C = ?";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setString(1, name);

            rs = stmt.executeQuery();

            while (rs.next()) {
                rights.add(this.buildObject(rs));
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
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT * FROM RIGHT_T WHERE RIGHT_ID = ?";
            stmt = this.dataSource.getConnection().prepareStatement(request);
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
    public int delete(Long id) {
        PreparedStatement stmt = null;

        try {
            String request = "DELETE FROM RIGHT_T WHERE RIGHT_ID = ?";
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

    @Override
    public void save(Right right) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "INSERT INTO RIGHT_T (NAME_C) VALUES (?)";
            stmt = this.dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, right.getName());

            int affectedRows = stmt.executeUpdate();

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

    @Override
    public void save(Right right, Right parent) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "INSERT INTO RIGHT_T (NAME_C, PARENT_ID) VALUES (?, ?)";
            stmt = this.dataSource.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, right.getName());
            stmt.setLong(2, parent.getId());

            int affectedRows = stmt.executeUpdate();

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

    @Override
    public void saveGroupRight(Long id, Right right) {
        PreparedStatement stmt = null;

        try {
            String request = "INSERT INTO GROUP_RIGHT_T VALUES (?, ?)";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setLong(1, id);
            stmt.setLong(2, right.getId());

            stmt.executeUpdate();
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
    public void deleteGroupRightByGroupId(Long id) {
        PreparedStatement stmt = null;

        try {
            String request = "DELETE FROM GROUP_RIGHT_T WHERE GROUP_ID = ?";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setLong(1, id);

            stmt.executeUpdate();
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
    public List<Right> findByParentId(Long id) {
        List<Right> rights = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT * FROM RIGHT_T WHERE PARENT_ID = ?";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                rights.add(this.buildObject(rs));
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
    public List<Right> findByGroupId(Long id) {
        List<Right> rights = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String request = "SELECT r.RIGHT_ID, r.NAME_C FROM GROUP_RIGHT_T g JOIN RIGHT_T r ON r.RIGHT_ID = g.RIGHT_ID " +
                    "WHERE g.GROUP_ID = ? ";
            stmt = this.dataSource.getConnection().prepareStatement(request);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                rights.add(this.buildObject(rs));
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

    private Right buildObject(ResultSet rs) throws SQLException {
        Right right = new Right();
        right.setId(rs.getLong("RIGHT_ID"));
        right.setName(rs.getString("NAME_C"));

        return right;
    }
}
