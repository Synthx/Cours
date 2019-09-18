package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest extends AbstractServiceSupport {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private RightService rightService;

    @Before
    public void before() {
        groupService.create("group");
        userService.create("user", "group");
        Right right = rightService.create("parent");
        rightService.create("sibling", right);
        groupService.addRight("group", right);
        rightService.create("not-for-me");
    }

    @Test
    public void testCreateAndFindByName() {
        User user = userService.findByName("user");
        assertNotNull(user);
        assertEquals("user", user.getName());
        Group group = user.getGroup();
        assertNotNull(group);
        assertEquals("group", group.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailsWhenNameNull() {
        userService.create(null, "group");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailsWhenGroupNameNull() {
        userService.create("user", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailsWhenGroupDoesNotExist() {
        userService.create("user", "notGroup");
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateFailsWhenSameNameUserAlreadyPresent() {
        userService.create("user", "group");
    }

    @Test
    public void testDeleteUser() {
        assertTrue(userService.delete("user"));
    }

    @Test
    public void testDeleteUserIfNotFound() {
        userService.delete("user");
        assertFalse(userService.delete("user"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserFailsIfNameNull() {
        userService.delete(null);
    }

    @Test
    public void testFindUserByNameIfUserNotFound() {
        assertNull(userService.findByName("blabla"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindUserByNameFailsIfNameNull() {
        assertNull(userService.findByName(null));
    }

    @Test
    public void testIsUserHasExactRight() {
        Right right = rightService.findByName("parent").get(0);
        assertTrue(userService.isUserHasRight("user", right));
    }

    @Test
    public void testIsUserHasRightByParents() {
        Right right = rightService.findByName("sibling").get(0);
        assertTrue(userService.isUserHasRight("user", right));
    }

    @Test
    public void testIsUserHasNotTheExactRight() {
        Right right = rightService.findByName("not-for-me").get(0);
        assertFalse(userService.isUserHasRight("user", right));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsUserHasRightFailsWhenUsernameNull() {
        Right right = rightService.findByName("parent").get(0);
        userService.isUserHasRight(null, right);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsUserHasRightFailsWhenRightNull() {
        userService.isUserHasRight("user", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsUserHasRightFailsWhenRightNotInDatabase() {
        Right right = new Right();
        right.setName("dummy");
        userService.isUserHasRight("user", right);
    }
}
