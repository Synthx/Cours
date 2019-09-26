package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import fr.plil.sio.persistence.api.Right;

import java.util.List;

public interface RightRepository {

    List<Right> findByName(String name);

    List<Right> findByParentId(Long id);

    List<Right> findByGroupId(Long id);

    Right findOne(Long id);

    int delete(Long id);

    void save(Right right);

    void save(Right right, Right parent);

    void updateGroup(Right right, Group group);

    void deleteGroup(Right right);
}
