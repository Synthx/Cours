package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Group;
import fr.plil.sio.persistence.api.GroupService;
import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceJdbc implements GroupService {

    @Autowired
    private RightService rightService;

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
        if (group == null) {
            return false;
        }

        int affectedRows = this.groupRepository.delete(group.getId());

        return affectedRows > 0;
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

        // Add right to group
        List<Right> groupRights = group.getRights();
        if (groupRights.stream().anyMatch(r -> r.getId().equals(rightDatabaseObject.getId()))) {
            return false;
        }

        groupRights.add(right);

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

        return true;
    }
}
