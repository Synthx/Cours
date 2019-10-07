package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RightServiceJpa implements RightService {

    @Autowired
    private RightRepository rightRepository;

    @Override
    public Right create(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        Right right = new Right();
        right.setName(name);

        return this.rightRepository.save(right);
    }

    @Override
    public Right create(String name, Right parent) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (parent == null) {
            throw new IllegalArgumentException("parent cannot be null");
        }

        Right parentRightDatabaseObject = this.findOne(parent.getId());
        if (parentRightDatabaseObject == null) {
            throw new IllegalArgumentException("parent cannot be found");
        }

        Right right = new Right();
        right.setName(name);
        right.setParent(parent);

        parent.getSiblings().add(right);

        return this.rightRepository.save(right);
    }

    @Override
    public boolean delete(Right right) {
        if (right == null) {
            throw new IllegalArgumentException("right cannot be null");
        }
        if (right.getId() == null) {
            return false;
        }

        Right databaseRightObject = this.findOne(right.getId());
        if (databaseRightObject == null) {
            return false;
        }

        this.rightRepository.delete(right);

        return true;
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
