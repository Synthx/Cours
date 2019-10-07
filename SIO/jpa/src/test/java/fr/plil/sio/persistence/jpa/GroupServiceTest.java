package fr.plil.sio.persistence.jpa;

import fr.plil.sio.persistence.api.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private RightService rightService;

    private Right parent;

    @Before
    public void before() {
        parent = rightService.create("parent");
        rightService.create("sibling", parent);
        groupService.create("group");
    }

    @Test
    public void testCreateGroupAndFindByName() {
        Group group = groupService.findByName("group");
        assertEquals("group", group.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroupFailsWhenNameNull() {
        groupService.create(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateFailsWhenSameGroupUserAlreadyPresent() {
        groupService.create("group");
    }

    @Test
    public void testDeleteGroup() {
        assertTrue(groupService.delete("group"));
        assertNull(groupService.findByName("group"));
        assertFalse(groupService.delete("group"));
    }

    @Test
    public void testDeleteNotExistingGroup() {
        assertFalse(groupService.delete("not-a-group"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testDeleteGroupFailsIfNameNull() {
        groupService.delete(null);
    }

    @Test
    public void deleteGroupDoesDeleteUsers() {
        userService.create("user1", "group");
        userService.create("user2", "group");
        assertNotNull(userService.findByName("user1"));
        assertNotNull(userService.findByName("user2"));
        groupService.delete("group");
        assertNull(userService.findByName("user1"));
        assertNull(userService.findByName("user2"));
    }

    @Test
    public void testFindByNameIfGroupNotFound() {
        assertNull(groupService.findByName("unknown"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByNameFailsIfNameNull() {
        groupService.findByName(null);
    }

    @Test
    public void testAddRight() {
        assertTrue(groupService.addRight("group", parent));
        Group group = groupService.findByName("group");
        assertEquals(1, group.getRights().size());
        assertEquals("parent", group.getRights().get(0).getName());
    }

    @Test
    public void testAddRightIfAlreadyPresent() {
        assertTrue(groupService.addRight("group", parent));
        assertFalse(groupService.addRight("group", parent));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRightFailsIfGroupNameNull() {
        groupService.addRight(null, parent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRightFailsIfRightNull() {
        groupService.addRight("group", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddRightFailsIfGroupNotInDatabase() {
        groupService.addRight("not-a-group", null);
    }

    @Test
    public void testRemoveRight() {
        assertTrue(groupService.addRight("group", parent));
        Group group = groupService.findByName("group");
        assertEquals(1, group.getRights().size());
        assertTrue(groupService.removeRight("group", parent));
        group = groupService.findByName("group");
        assertEquals(0, group.getRights().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveRightIfNotInDatabase() {
        Right right = new Right();
        right.setName("not-a-right");
        assertFalse(groupService.removeRight("group", right));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveRightFailsIfGroupNameNull() {
        groupService.removeRight(null, parent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveRightFailsIfRightNull() {
        groupService.removeRight("group", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveRightFailsIfGroupNotInDatabase() {
        groupService.removeRight("not-a-group", null);
    }
}
