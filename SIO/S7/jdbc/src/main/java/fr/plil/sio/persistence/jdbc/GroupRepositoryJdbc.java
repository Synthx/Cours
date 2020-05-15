package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GroupRepositoryJdbc implements GroupRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Group findByName(String name) {
        try {
            String request = "SELECT * FROM GROUP_T WHERE NAME_C = :name";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", name);

            return this.jdbcTemplate.queryForObject(request, params, new GroupMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Group findById(Long id) {
        try {
            String request = "SELECT * FROM GROUP_T WHERE GROUP_ID = :id";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", id);

            return this.jdbcTemplate.queryForObject(request, params, new GroupMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void save(Group group) {
        String request = "INSERT INTO GROUP_T (NAME_C) VALUES (:name)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", group.getName());

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(request, params, holder);

        group.setId(holder.getKey().longValue());
    }

    @Override
    public int delete(String name) {
        String request = "DELETE FROM GROUP_T WHERE NAME_C = :name";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        return this.jdbcTemplate.update(request, params);
    }

    private static final class GroupMapper implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            Group group = new Group();

            group.setId(resultSet.getLong("GROUP_ID"));
            group.setName(resultSet.getString("NAME_C"));

            return group;
        }
    }
}
