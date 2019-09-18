package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightServiceJdbc implements RightService {
    @Override
    public Right create(String name) {
        return null;
    }

    @Override
    public Right create(String name, Right parent) {
        return null;
    }

    @Override
    public boolean delete(Right right) {
        throw new IllegalStateException("not implemented !");
    }

    @Override
    public List<Right> findByName(String name) {
        throw new IllegalStateException("not implemented !");
    }

    @Override
    public Right findOne(Long id) {
        throw new IllegalStateException("not implemented !");
    }
}
