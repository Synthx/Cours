package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import fr.plil.sio.persistence.api.User;
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
public class UserRepositoryJdbc implements UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String request = "INSERT INTO USER_T (NAME_C, GROUP_ID) VALUES (:name, :group)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("group", user.getGroup().getId());

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(request, params, holder);

        user.setId(holder.getKey().longValue());
    }

    @Override
    public User findByName(String name) {
        try {
            String request = "SELECT * FROM USER_T WHERE NAME_C = :name";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", name);

            return this.jdbcTemplate.queryForObject(request, params, new UserMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public int delete(String name) {
        String request = "DELETE FROM USER_T WHERE NAME_C = :name";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        return this.jdbcTemplate.update(request, params);
    }

    @Override
    public int deleteByGroupId(Long id) {
        String request = "DELETE FROM USER_T WHERE GROUP_ID = :group";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("group", id);

        return this.jdbcTemplate.update(request, params);
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();

            user.setId(resultSet.getLong("USER_ID"));
            user.setName(resultSet.getString("NAME_C"));

            Group group = new Group();
            group.setId(resultSet.getLong("GROUP_ID"));

            user.setGroup(group);

            return user;
        }
    }
}
