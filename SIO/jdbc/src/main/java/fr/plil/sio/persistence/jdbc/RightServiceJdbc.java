package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightServiceJdbc implements RightService {

    @Autowired
    private RightRepository rightRepository;

    @Override
    public Right create(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        Right right = new Right();
        right.setName(name);

        this.rightRepository.save(right);

        return right;
    }

    @Override
    public Right create(String name, Right parent) {
        if (name == null || parent == null) {
            throw new IllegalArgumentException("name or parent cannot be null");
        }

        Right databaseParentObject = this.rightRepository.findOne(parent.getId());
        if (databaseParentObject == null) {
            throw new IllegalArgumentException("parent must exist in database");
        }

        Right right = new Right();
        right.setName(name);
        right.setParent(databaseParentObject);

        this.rightRepository.save(right);

        parent.getSiblings().add(right);

        return right;
    }

    @Override
    public boolean delete(Right right) {
        throw new IllegalStateException("not implemented !");
    }

    @Override
    public List<Right> findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        return this.rightRepository.findByName(name);
    }

    @Override
    public Right findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return this.rightRepository.findOne(id);
    }
}
