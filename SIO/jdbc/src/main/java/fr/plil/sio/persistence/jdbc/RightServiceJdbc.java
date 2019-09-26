package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
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
        if (name == null || parent == null || parent.getId() == null) {
            throw new IllegalArgumentException("name or parent cannot be null");
        }

        Right databaseParentObject = this.rightRepository.findOne(parent.getId());
        if (databaseParentObject == null) {
            throw new IllegalArgumentException("parent must exist in database");
        }

        Right right = new Right();
        right.setName(name);
        right.setParent(databaseParentObject);

        this.rightRepository.save(right, databaseParentObject);

        parent.getSiblings().add(right);

        return right;
    }

    @Override
    public boolean delete(Right right) {
        if (right == null || right.getId() == null) {
            throw new IllegalArgumentException("right cannot be null");
        }

        Right databaseObject = this.rightRepository.findOne(right.getId());

        // Delete siblings right
        for (Right r : databaseObject.getSiblings()) {
            this.rightRepository.delete(r.getId());
        }

        // Then delete parent right
        int affectedRows = this.rightRepository.delete(right.getId());

        return affectedRows > 0;
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

        Right right = this.rightRepository.findOne(id);

        if (right != null) {
            right.setSiblings(this.rightRepository.findByParentId(right.getId()));
        }

        return right;
    }

    @Override
    public List<Right> findByGroupId(Long id) {
        return this.rightRepository.findByGroupId(id);
    }

    @Override
    public void updateGroup(Right right, Group group) {
        this.rightRepository.updateGroup(right, group);
    }

    @Override
    public void deleteGroup(Right right) {
        this.rightRepository.deleteGroup(right);
    }
}
