package fr.plil.sio.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaSpringInitializerApplication.class})
@TransactionConfiguration
@Transactional
public class AnimalServiceTest {

    @Resource
    private AnimalService animalService;

    @Resource
    private OwnerService ownerService;

    @Resource
    private VeterinarianService veterinarianService;

    private Owner owner;

    private Animal animal1, animal2;

    private Veterinarian veterinarian1, veterinarian2;

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
    public void testCreateAnimal() {
        assertEquals("animal1", animal1.getName());
        assertEquals(owner, animal1.getOwner());
        assertEquals(2, owner.getAnimals().size());
        assertEquals(animal1, owner.getAnimals().get(0));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateAnimalFailsIfNameNull() {
        ownerService.findByName("owner");
        animalService.create(null, "owner");
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateAnimalFailsIfOwnerNotFound() {
        animalService.create("animal1", "not-an-owner");
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateAnimalFailsIfAnimalAlreadyPresent() {
        animalService.create("animal1", "owner");
    }

    @Test
    public void testRemoveAnimal() {
        animalService.remove("animal1");
        assertEquals(1, animalService.findAll().size());
        assertNull(animalService.findByName("animal1"));
        assertEquals(1, owner.getAnimals().size());
        assertEquals(1, ownerService.findByName("owner").getAnimals().size());
        assertEquals(1, veterinarianService.findByName("veterinarian1").getAnimals().size());
        assertEquals(0, veterinarianService.findByName("veterinarian2").getAnimals().size());
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAnimalFailsIfNameNull() {
        animalService.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveAnimalFailsIfAnimalNotFound() {
        animalService.remove("not-an-animal1");
    }

    @Test
    public void testFindAll() {
        List<Animal> animals = animalService.findAll();
        assertEquals(2, animals.size());
    }

    @Test
    public void testFindAnimalByName() {
        animal1 = animalService.findByName("animal1");
        assertNotNull(animal1);
        assertEquals(owner, animal1.getOwner());
        assertEquals(2, animal1.getVeterinarians().size());
        assertEquals(animal1, veterinarianService.findByName("veterinarian1").getAnimals().get(0));
        assertEquals(animal1, veterinarianService.findByName("veterinarian2").getAnimals().get(0));
        animal2 = animalService.findByName("animal2");
        assertNotNull(animal2);
        assertEquals(owner, animal2.getOwner());
        assertEquals(1, animal2.getVeterinarians().size());
        assertEquals(animal2, veterinarianService.findByName("veterinarian1").getAnimals().get(1));
    }


    @Test(expected = NullPointerException.class)
    public void testFindAnimalByNameFailedWhenNameNull() {
        animalService.findByName(null);
    }
}