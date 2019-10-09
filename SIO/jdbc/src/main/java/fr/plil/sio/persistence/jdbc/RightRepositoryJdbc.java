package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RightRepositoryJdbc implements RightRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Right> findByName(String name) {
        String request = "SELECT * FROM RIGHT_T WHERE NAME_C = :name";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        return this.jdbcTemplate.query(request, params, new RightMapper());
    }

    @Override
    public Right findOne(Long id) {
        try {
            String request = "SELECT * FROM RIGHT_T WHERE RIGHT_ID = :id";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", id);

            return this.jdbcTemplate.queryForObject(request, params, new RightMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public int delete(Long id) {
        String request = "DELETE FROM RIGHT_T WHERE RIGHT_ID = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return this.jdbcTemplate.update(request, params);
    }

    @Override
    public void save(Right right) {
        String request = "INSERT INTO RIGHT_T (NAME_C) VALUES (:name)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", right.getName());

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(request, params, holder);

        right.setId(holder.getKey().longValue());
    }

    @Override
    public void save(Right right, Right parent) {
        String request = "INSERT INTO RIGHT_T (NAME_C, PARENT_ID) VALUES (:name, :parent)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", right.getName());
        params.addValue("parent", parent.getId());

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(request, params, holder);

        right.setId(holder.getKey().longValue());
    }

    @Override
    public void saveGroupRight(Long id, Right right) {
        String request = "INSERT INTO GROUP_RIGHT_T VALUES (:right, :parent)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("right", id);
        params.addValue("parent", right.getId());

        this.jdbcTemplate.update(request, params);
    }

    @Override
    public void deleteGroupRightByGroupId(Long id) {
        String request = "DELETE FROM GROUP_RIGHT_T WHERE GROUP_ID = :group";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("group", id);

        this.jdbcTemplate.update(request, params);
    }

    @Override
    public List<Right> findByParentId(Long id) {
        String request = "SELECT * FROM RIGHT_T WHERE PARENT_ID = :parent";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("parent", id);

        return this.jdbcTemplate.query(request, params, new RightMapper());
    }

    @Override
    public List<Right> findByGroupId(Long id) {
        String request = "SELECT r.RIGHT_ID, r.NAME_C FROM GROUP_RIGHT_T g JOIN RIGHT_T r ON r.RIGHT_ID = g.RIGHT_ID " +
                "WHERE g.GROUP_ID = :group";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("group", id);

        return this.jdbcTemplate.query(request, params, new RightMapper());
    }

    private static final class RightMapper implements RowMapper<Right> {

        @Override
        public Right mapRow(ResultSet resultSet, int i) throws SQLException {
            Right right = new Right();

            right.setId(resultSet.getLong("RIGHT_ID"));
            right.setName(resultSet.getString("NAME_C"));

            return right;
        }
    }
}
