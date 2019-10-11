package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceJpa implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RightService rightService;

    @Override
    public User create(String name, String groupName) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (groupName == null) {
            throw new IllegalArgumentException("groupName cannot be null");
        }

        Group group = this.groupService.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group cannot be found");
        }

        User user = this.findByName(name);
        if (user != null) {
            throw new IllegalStateException("user with same name already exist");
        }

        user = new User();
        user.setName(name);
        user.setGroup(group);

        group.getUsers().add(user);

        return this.userRepository.save(user);
    }

    @Override
    public boolean delete(String name) {
        User user = this.findByName(name);
        if (user == null) {
            return false;
        }

        this.userRepository.delete(user);

        return true;
    }

    @Override
    public User findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        return this.userRepository.findByName(name);
    }

    @Override
    public boolean isUserHasRight(String userName, Right right) {
        if (userName == null) {
            throw new IllegalArgumentException("userName cannot be null");
        }
        if (right == null) {
            throw new IllegalArgumentException("right cannot be null");
        }

        User user = this.userRepository.findByName(userName);
        if (user == null) {
            throw new IllegalArgumentException("user cannot be found");
        }

        Right right1 = this.rightService.findOne(right.getId());
        if (right1 == null) {
            throw new IllegalArgumentException("right cannot be found");
        }

        Group group = this.groupService.findByName(user.getGroup().getName());
        if (group != null) {
            return group.getRights().stream().map(Right::getId).map(rightService::findOne).anyMatch(r -> isRightIsEqual(r, right));
        }

        return false;
    }

    private boolean isRightIsEqual(Right currentRight, Right right) {
        if (currentRight == null) {
            return false;
        }

        if (currentRight.getId().equals(right.getId())) {
            return true;
        }

        return this.isRightIsEqual(currentRight.getParent(), right);
    }
}
