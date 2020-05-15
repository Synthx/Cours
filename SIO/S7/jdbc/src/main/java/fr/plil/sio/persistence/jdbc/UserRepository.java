package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.User;

public interface UserRepository {

    void save(User user);

    User findByName(String name);

    int delete(String name);

    int deleteByGroupId(Long id);
}
