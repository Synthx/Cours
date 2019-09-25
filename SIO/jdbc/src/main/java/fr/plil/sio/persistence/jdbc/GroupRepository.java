package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;

public interface GroupRepository {

    Group findByName(String name);

    int delete(Long id);

    void save(Group group);
}
