package fr.plil.sio.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaSpringInitializerApplication.class})
@TransactionConfiguration
@Transactional
public class VeterinarianServiceTest {

    @Resource
    private VeterinarianService veterinarianService;

    @Resource
    private AnimalService animalService;

    @Resource
    private OwnerService ownerService;

    private Veterinarian veterinarian1, veterinarian2;

    private Animal animal1, animal2;

    private Owner owner;

    @Before
    public void before() {
        owner = ownerService.create("owner");
        animal1 = animalService.create("animal1", "owner");
        animal2 = animalService.create("animal2", "owner");
        veterinarian1 = veterinarianService.createVeterinarian("veterinarian1");
        veterinarian2 = veterinarianService.createVeterinarian("veterinarian2");
        veterinarianService.addAnimal("animal1", "veterinarian1");
        veterinarianService.addAnimal("animal1", "veterinarian2");
        veterinarianService.addAnimal("animal2", "veterinarian1");
    }

    @Test
    public void testCreateVeterinarian() {
        assertNotNull(veterinarianService.findByName("veterinarian1"));
        assertEquals("veterinarian1", veterinarianService.findByName("veterinarian1").getName());
    }


    @Test(expected = NullPointerException.class)
    public void testCreateVeterinarianFailsIfVeterinarianNull() {
        veterinarianService.createVeterinarian(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateVeterinarianFailsIfVeterinarianPresent() {
        veterinarianService.createVeterinarian("veterinarian1");
    }

    @Test
    public void testRemoveVeterinarian() {
        veterinarianService.removeVeterinarian("veterinarian1");
        assertEquals(1, veterinarianService.findAll().size());
        assertEquals(2, animalService.findAll().size());
        veterinarianService.removeVeterinarian("veterinarian2");
        assertEquals(0, veterinarianService.findAll().size());
        assertEquals(2, animalService.findAll().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveVeterinarianFailsIfVeterinarianNotPresent() {
        veterinarianService.removeVeterinarian("veterinarian-not-present");
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveVeterinarianFailsIfNameNull() {
        veterinarianService.removeVeterinarian(null);
    }

    @Test
    public void testfindAll() {
        assertEquals(2, veterinarianService.findAll().size());
    }

    @Test
    public void testFindByName() {
        assertNotNull(veterinarianService.findByName("veterinarian1"));
        assertNull(veterinarianService.findByName("dummy"));
    }

    @Test(expected = NullPointerException.class)
    public void testFindByNameFailsIfNameNull() {
        veterinarianService.findByName(null);
    }

    @Test
    public void testAddAnimalToVeterinarian() {
        assertEquals(2, veterinarian1.getAnimals().size());
        assertEquals(1, veterinarian2.getAnimals().size());
        assertEquals(2, animal1.getVeterinarians().size());
        assertEquals(1, animal2.getVeterinarians().size());
        assertFalse(veterinarianService.addAnimal("animal1", "veterinarian1"));
        Animal animal3 = animalService.create("animal3", "owner");
        assertTrue(veterinarianService.addAnimal("animal3", "veterinarian1"));
    }

    @Test(expected = NullPointerException.class)
    public void testAddAnimalToVeterinarianFailedIfAnimalNameNull() {
        veterinarianService.addAnimal(null, "veterinarian1");
    }

    @Test(expected = NullPointerException.class)
    public void testAddAnimalToVeterinarianFailedIfVeterinarianNameNull() {
        veterinarianService.addAnimal("animal1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAnimalToVeterinarianFailedIfAnimalNotPresent() {
        veterinarianService.addAnimal("animal-dummy", "veterinarian1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAnimalToVeterinarianFailedIfVeterinarianNotPresent() {
        veterinarianService.addAnimal("animal1", "veterinarian-dummy");
    }

    @Test
    public void testRemoveAnimalToVeterinarian() {
        assertTrue(veterinarianService.removeAnimal("animal1", "veterinarian1"));
        assertEquals(1, veterinarian1.getAnimals().size());
        assertEquals(1, veterinarian2.getAnimals().size());
        assertEquals(1, animal1.getVeterinarians().size());
        assertEquals(1, animal2.getVeterinarians().size());
        assertTrue(veterinarianService.removeAnimal("animal1", "veterinarian2"));
        assertEquals(1, veterinarian1.getAnimals().size());
        assertEquals(0, veterinarian2.getAnimals().size());
        assertEquals(0, animal1.getVeterinarians().size());
        assertEquals(1, animal2.getVeterinarians().size());
        assertFalse(veterinarianService.removeAnimal("animal1", "veterinarian2"));
        assertEquals(1, veterinarian1.getAnimals().size());
        assertEquals(0, veterinarian2.getAnimals().size());
        assertEquals(0, animal1.getVeterinarians().size());
        assertEquals(1, animal2.getVeterinarians().size());
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAnimalToVeterinarianFailedIfAnimalNameNull() {
        veterinarianService.removeAnimal(null, "veterinarian1");
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAnimalToVeterinarianFailedIfVeterinarianNameNull() {
        veterinarianService.removeAnimal("animal1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAnimalToVeterinarianFailedIfAnimalNotPresent() {
        veterinarianService.removeAnimal("animal-dummy", "veterinarian1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAnimalToVeterinarianFailedIfVeterinarianNotPresent() {
        veterinarianService.removeAnimal("animal1", "veterinarian-dummy");
    }
}