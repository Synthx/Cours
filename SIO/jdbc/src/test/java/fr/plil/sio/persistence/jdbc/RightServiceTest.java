package fr.plil.sio.persistence.jdbc;

import fr.plil.sio.persistence.api.Right;
import fr.plil.sio.persistence.api.RightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RightServiceTest extends AbstractServiceSupport {

    @Autowired
    private RightService rightService;

    @Test
    public void testCreateParentRightAndFindOne() {
        rightService.create("right");
        assertEquals(1, rightService.findByName("right").size());
    }

    @Test
    public void testCreateTwoParentRightsWithSameNameAndFindByName() {
        rightService.create("right");
        rightService.create("right");
        assertEquals(2, rightService.findByName("right").size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailsIfNameNull() {
        rightService.create(null);
    }

    @Test
    public void testCreateSiblingRightAndFindOne() {
        Right parent = rightService.create("parent");
        rightService.create("sibling", parent);
        assertEquals(1, rightService.findByName("sibling").size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSiblingFailsIfNameNull() {
        Right parent = rightService.create("parent");
        rightService.create(null, parent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSiblingFailsIfParentNull() {
        rightService.create("parent");
        rightService.create("sibling", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSiblingFailsIfParentNotInDatabase() {
        Right right = new Right();
        right.setName("not-a-right");
        rightService.create("sibling", right);
    }

    @Test
    public void testDeleteParentRight() {
        Right right = rightService.create("right");
        assertEquals(1, rightService.findByName("right").size());
        rightService.delete(right);
        assertEquals(0, rightService.findByName("right").size());
    }

    @Test
    public void testDeleteSiblingRight() {
        Right parent = rightService.create("parent");
        Right sibling = rightService.create("sibling", parent);
        rightService.delete(sibling);
        assertEquals(0, rightService.findByName("sibling").size());
    }

    @Test
    public void testDeleteParentAndSiblingRights() {
        Right parent = rightService.create("parent");
        rightService.create("sibling", parent);
        rightService.delete(parent);
        assertEquals(0, rightService.findByName("sibling").size());
        assertEquals(0, rightService.findByName("parent").size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteRightIfNotInDatabase() {
        Right right = new Right();
        right.setName("not-a-right");
        rightService.delete(right);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteRightFailsIfRightNull() {
        rightService.delete(null);
    }

    @Test
    public void testFindByNameIfNameNotFound() {
        assertEquals(0, rightService.findByName("no").size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByNameFailsIfNameNull() {
        rightService.findByName(null);
    }

    @Test
    public void testFindOne() {
        Right right = rightService.create("right");
        assertNotNull(rightService.findOne(right.getId()));
    }

    @Test
    public void testFindOneIfIdNotFound() {
        assertNull(rightService.findOne(153463167809232L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindOneFailsIfIdNull() {
        rightService.findOne(null);
    }
}
