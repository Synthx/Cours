package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Group;
import fr.plil.sio.persistence.api.GroupService;
import fr.plil.sio.persistence.api.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceJpa implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional
    public Group create(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        Group group = groupRepository.findByName(name);
        if (group != null) {
            throw new IllegalStateException("a group with the same name already exists");
        }
        group = new Group();
        group.setName(name);
        groupRepository.save(group);
        return group;
    }

    @Override
    @Transactional
    public boolean delete(String name) {
        throw new IllegalStateException("not implemented !");
    }

    @Override
    @Transactional(readOnly = true)
    public Group findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        return groupRepository.findByName(name);
    }

    @Override
    @Transactional
    public boolean addRight(String groupName, Right right) {
        return false;
    }

    @Override
    @Transactional
    public boolean removeRight(String groupName, Right right) {
        return false;
    }
}
