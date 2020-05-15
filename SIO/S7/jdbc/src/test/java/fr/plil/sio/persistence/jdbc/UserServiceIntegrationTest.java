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
public class UserServiceIntegrationTest extends AbstractServiceSupport {

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
    public void testIsUserHasRightByParent() {
        Right right = rightService.findByName("sibling").get(0);
        assertFalse(userService.isUserHasRight("user",right));
    }

    @Test
    public void testIsUserHasNotRightMorComplex() {
        Right right1 = rightService.create("r1");
        Right right2 = rightService.create("r2");
        Right right3 = rightService.create("r3");
        Right right4 = rightService.create("r4");
        Right right5 = rightService.create("r5", right2);
        Right right6 = rightService.create("r6", right5);
        Right right7 = rightService.create("r7", right3);
        Right right8 = rightService.create("r8", right7);
        Right right9 = rightService.create("r9", right4);
        groupService.addRight("group", right5);
        groupService.addRight("group", right8);
        groupService.addRight("group", right4);
        assertFalse(userService.isUserHasRight("user", right1));
        assertTrue(userService.isUserHasRight("user", right2));
        assertTrue(userService.isUserHasRight("user", right3));
        assertTrue(userService.isUserHasRight("user", right4));
        assertTrue(userService.isUserHasRight("user", right5));
        assertFalse(userService.isUserHasRight("user", right6));
        assertTrue(userService.isUserHasRight("user", right7));
        assertTrue(userService.isUserHasRight("user", right8));
        assertFalse(userService.isUserHasRight("user", right9));
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
