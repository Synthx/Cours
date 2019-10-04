package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Group;
import fr.plil.sio.persistence.api.GroupService;
import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupServiceJpa implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RightService rightService;

    @Override
    public Group create(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        Group group = this.groupRepository.findByName(name);
        if (group != null) {
            throw new IllegalStateException("a group with the same name already exists");
        }

        group = new Group();
        group.setName(name);

        return this.groupRepository.save(group);
    }

    @Override
    public boolean delete(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        Group group = this.groupRepository.findByName(name);
        if (group == null) {
            return false;
        }

        this.groupRepository.delete(group);

        return true;
    }

    @Override
    public Group findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        return this.groupRepository.findByName(name);
    }

    @Override
    public boolean addRight(String groupName, Right right) {
        if (groupName == null) {
            throw new IllegalArgumentException("groupName cannot be null");
        }
        if (right == null) {
            throw new IllegalArgumentException("right cannot be null");
        }

        Group group = this.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group cannot be found");
        }

        Right o = this.rightService.findOne(right.getId());
        if (o == null) {
            throw new IllegalArgumentException("right cannot be found");
        }

        if (group.getRights().stream().anyMatch(r -> r.getId().equals(o.getId()))) {
            return false;
        }

        group.getRights().add(o);
        this.groupRepository.save(group);

        return true;
    }

    @Override
    public boolean removeRight(String groupName, Right right) {
        if (groupName == null) {
            throw new IllegalArgumentException("groupName cannot be null");
        }
        if (right == null) {
            throw new IllegalArgumentException("right cannot be null");
        }

        Group group = this.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group cannot be found");
        }

        Right o = this.rightService.findOne(right.getId());
        if (o == null) {
            throw new IllegalArgumentException("right cannot be found");
        }

        if (group.getRights().stream().noneMatch(r -> r.getId().equals(o.getId()))) {
            return false;
        }

        group.getRights().remove(right);
        this.groupRepository.save(group);

        return true;
    }
}
