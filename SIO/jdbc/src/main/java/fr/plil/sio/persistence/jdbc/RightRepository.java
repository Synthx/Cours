package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;

import java.util.List;

public interface RightRepository {

    List<Right> findByName(String name);

    Right findOne(Long id);

    void delete(Long name);

    void save(Right right);
}
