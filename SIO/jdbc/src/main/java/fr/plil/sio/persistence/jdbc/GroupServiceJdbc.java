package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceJdbc implements GroupService {

    @Autowired
    private RightService rightService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

    @Override
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
    public boolean delete(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        // Group exist ?
        Group group = this.groupRepository.findByName(name);

        // Delete group users and rights if group exist
        if (group != null) {
            this.userService.deleteByGroupId(group.getId());
            this.rightService.deleteGroupRight(group.getId());
        }

        return this.groupRepository.delete(name) > 0;
    }

    @Override
    public Group findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        Group group = this.groupRepository.findByName(name);

        if (group != null) {
            // Add rights to group
            group.setRights(this.rightService.findByGroupId(group.getId()));
        }

        return group;
    }

    @Override
    public boolean addRight(String groupName, Right right) {
        if (groupName == null) {
            throw new IllegalArgumentException("groupName cannot be null");
        }
        if (right == null) {
            throw new IllegalArgumentException("right cannot be null");
        }
        if (right.getId() == null) {
            throw new IllegalArgumentException("right id cannot be null");
        }

        // Group exist ?
        Group group = this.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group cannot be found");
        }

        // Right exist ?
        Right rightDatabaseObject = this.rightService.findOne(right.getId());
        if (rightDatabaseObject == null) {
            throw new IllegalArgumentException("right cannot be found");
        }

        // Add right to group
        List<Right> groupRights = group.getRights();
        if (groupRights.stream().anyMatch(r -> r.getId().equals(rightDatabaseObject.getId()))) {
            return false;
        }

        groupRights.add(right);

        // Save right to database
        this.rightService.saveGroupRight(group);

        return true;
    }

    @Override
    public boolean removeRight(String groupName, Right right) {
        if (groupName == null || right == null || right.getId() == null) {
            throw new IllegalArgumentException("groupName or right cannot be null");
        }

        // Group exist ?
        Group group = this.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group cannot be found");
        }

        // Right exist ?
        Right rightDatabaseObject = this.rightService.findOne(right.getId());
        if (rightDatabaseObject == null) {
            throw new IllegalArgumentException("right cannot be found");
        }

        // Delete right from group
        List<Right> groupRights = group.getRights();
        if (groupRights.stream().noneMatch(r -> r.getId().equals(right.getId()))) {
            return false;
        }

        group.setRights(groupRights.stream().filter(r -> !r.getId().equals(right.getId())).collect(Collectors.toList()));

        // Save right to database
        this.rightService.saveGroupRight(group);

        return true;
    }

    @Override
    public Group findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return this.groupRepository.findById(id);
    }
}
