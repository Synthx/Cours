package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceJdbc implements UserService {

    @Autowired
    private GroupService groupService;

    @Autowired
    private RightService rightService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(String name, String groupName) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (groupName == null) {
            throw new IllegalArgumentException("groupName cannot be null");
        }

        // Group exist ?
        Group group = this.groupService.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group not found");
        }

        // User already exist ?
        User userDatabaseObject = this.userRepository.findByName(name);
        if (userDatabaseObject != null) {
            throw new IllegalStateException("user with same name already exist");
        }

        // Insert into database
        User user = new User();
        user.setName(name);
        user.setGroup(group);

        this.userRepository.save(user);

        return user;
    }

    @Override
    public boolean delete(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        return this.userRepository.delete(name) > 0;
    }

    @Override
    public boolean deleteByGroupId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        return this.userRepository.deleteByGroupId(id) > 0;
    }

    @Override
    public User findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        User user = this.userRepository.findByName(name);

        // Associate group
        if (user != null) {
            Group group = user.getGroup();
            if (group != null) {
                user.setGroup(this.groupService.findById(group.getId()));
            }
        }

        return user;
    }

    @Override
    public boolean isUserHasRight(String userName, Right right) {
        if (userName == null) {
            throw new IllegalArgumentException("userName cannot be null");
        }
        if (right == null) {
            throw new IllegalArgumentException("right cannot be null");
        }

        // User exist ?
        User user = this.findByName(userName);
        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }

        // Right exist ?
        Right right1 = this.rightService.findOne(right.getId());
        if (right1 == null) {
            throw new IllegalArgumentException("right not found");
        }

        // Get group rights
        List<Right> groupRights = this.rightService.findByGroupId(user.getGroup().getId());

        return groupRights.stream().map(Right::getId).map(rightService::findOne).anyMatch(r -> {
            return r.getId().equals(right1.getId()) || r.getSiblings().stream().map(Right::getId).anyMatch(right1.getId()::equals);
        });
    }
}
