package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RightServiceJpa implements RightService {

    @Override
    @Transactional
    public Right create(String name) {
        return null;
    }

    @Override
    @Transactional
    public Right create(String name, Right parent) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Right right) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Right> findByName(String name) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Right findOne(Long id) {
        return null;
    }
}
