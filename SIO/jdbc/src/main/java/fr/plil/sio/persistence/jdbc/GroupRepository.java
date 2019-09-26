package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;

public interface GroupRepository {

    Group findByName(String name);

    Group findById(Long id);

    int delete(String name);

    void save(Group group);
}
