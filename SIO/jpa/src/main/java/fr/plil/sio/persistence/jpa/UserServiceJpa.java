package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.User;
import fr.plil.sio.persistence.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceJpa implements UserService {

    @Override
    @Transactional
    public User create(String name, String groupName) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(String name) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String name) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserHasRight(String userName, Right right) {
        return false;
    }
}
