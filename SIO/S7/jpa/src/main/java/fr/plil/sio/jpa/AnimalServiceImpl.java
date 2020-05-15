package fr.plil.sio.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("animalService")
public class AnimalServiceImpl implements AnimalService {

    @Resource
    private AnimalRepository animalRepository;

    @Resource
    private OwnerService ownerService;

    @Override
    @Transactional
    public Animal create(String nameAnimal, String nameOwner) {
        if (nameAnimal == null || nameOwner == null) {
            throw new NullPointerException("animal or owner name must be not null");
        }

        Owner owner = ownerService.findByName(nameOwner);

        if (owner == null) {
            throw new IllegalStateException("cannot found owner");
        }

        Animal animal = animalRepository.findByName(nameAnimal);

        if (animal != null) {
            throw new IllegalStateException("animal with the same name already present the database.");
        }

        animal = new Animal();
        animal.setName(nameAnimal);
        animal.setOwner(owner);
        owner.getAnimals().add(animal);

        return animal;
    }

    @Override
    @Transactional
    public void remove(String name) {
        if (name == null) {
            throw new NullPointerException("name must be not null");
        }

        Animal animal = animalRepository.findByName(name);

        if (animal == null) {
            throw new IllegalArgumentException("animal not present");
        }

        animal.getOwner().getAnimals().remove(animal);

        for (Veterinarian v : animal.getVeterinarians()) {
            v.getAnimals().remove(animal);
        }

        animalRepository.delete(animal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Animal findByName(String name) {
        if (name == null) {
            throw new NullPointerException("name must be not null");
        }

        return animalRepository.findByName(name);
    }
}