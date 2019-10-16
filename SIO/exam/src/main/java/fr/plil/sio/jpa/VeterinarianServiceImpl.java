package fr.plil.sio.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("veterinarianService")
public class VeterinarianServiceImpl implements VeterinarianService {

    private final static Logger logger = LoggerFactory.getLogger(VeterinarianServiceImpl.class);

    @Resource
    private VeterinarianRepository veterinarianRepository;

    @Resource
    private AnimalService animalService;
    
    @Resource
    private NoteService noteService;

    @Override
    @Transactional
    public Veterinarian createVeterinarian(String name) {
        if (name == null) {
            throw new NullPointerException("name must be not null");
        }

        if (veterinarianRepository.findByName(name) != null) {
            throw new IllegalArgumentException("veterinarian already present");
        }

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setName(name);
        veterinarianRepository.save(veterinarian);

        return veterinarian;
    }

    @Override
    @Transactional
    public void removeVeterinarian(String name) {
        if (name == null) {
            throw new NullPointerException("name must be not null");
        }

        Veterinarian veterinarian = veterinarianRepository.findByName(name);
        if (veterinarian == null) {
            throw new IllegalArgumentException("veterinarian not present");
        }
        
        for (Note n : veterinarian.getNotes()) {
            n.getAnimal().getNotes().remove(n);
        }

        veterinarianRepository.delete(veterinarian);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Veterinarian> findAll() {
        return veterinarianRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Veterinarian findByName(String name) {
        if (name == null) {
            throw new NullPointerException("name must be not null");
        }

        return veterinarianRepository.findByName(name);
    }

    @Override
    @Transactional
    public boolean addAnimal(String animalName, String veterinarianName) {
        if (animalName == null) {
            throw new NullPointerException("animal name must be not null");
        }

        if (veterinarianName == null) {
            throw new NullPointerException("veterinarian name must be not null");
        }

        Veterinarian veterinarian = findByName(veterinarianName);
        if (veterinarian == null) {
            throw new IllegalArgumentException("veterinarian not present");
        }

        Animal animal = animalService.findByName(animalName);
        if (animal == null) {
            throw new IllegalArgumentException("animal not present");
        }

        if (animal.getVeterinarians().contains(veterinarian) || veterinarian.getAnimals().contains(animal)) {
            return false;
        }

        veterinarian.getAnimals().add(animal);
        animal.getVeterinarians().add(veterinarian);

        return true;
    }

    @Override
    @Transactional
    public boolean removeAnimal(String animalName, String veterinarianName) {
        Veterinarian veterinarian = findByName(veterinarianName);
        if (veterinarian == null) {
            throw new IllegalArgumentException("veterinarian not present");
        }

        Animal animal = animalService.findByName(animalName);
        if (animal == null) {
            throw new IllegalArgumentException("animal not present");
        }

        if (!animal.getVeterinarians().contains(veterinarian) || !veterinarian.getAnimals().contains(animal)) {
            return false;
        }

        veterinarian.getAnimals().remove(animal);
        animal.getVeterinarians().remove(veterinarian);

        return true;
    }
    
    @Override
    @Transactional
    public Note addNote(Veterinarian veterinarian, Animal animal, String comment) {
        if (!animal.getVeterinarians().contains(veterinarian) || !veterinarian.getAnimals().contains(animal)) {
            throw new IllegalStateException("animal non affected to veterinarian");
        }
        
        return this.noteService.save(comment, animal, veterinarian);
    }
}
