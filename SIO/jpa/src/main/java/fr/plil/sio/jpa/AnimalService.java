package fr.plil.sio.jpa;

import java.util.List;

public interface AnimalService {

    /**
     * Create an animal and add it to the owner.
     *
     * @param nameAnimal name of the new animal
     * @param nameOwner  name of the owner
     * @return the new animal instance with all dependencies resolved
     * @throws NullPointerException  if nameAnimal or nameOwner is null
     * @throws IllegalStateException if owner does not exist in the database or if an animal with the same name exist
     *                               in the database
     */
    Animal create(String nameAnimal, String nameOwner);

    /**
     * Delete an animal from the database and remove it from the owner and the veterinarians.
     *
     * @param name name of the animal to delete
     * @throws NullPointerException  if name is null
     * @throws IllegalStateException if animal does not exist in the database.
     */
    void remove(String name);

    /**
     * Return all animals in the database with all dependencies resolved.
     *
     * @return The list of animals.
     */
    List<Animal> findAll();

    /**
     * Return an animal instance with specified name with all dependencies resolved.
     *
     * @param name the name of the animal to return.
     * @return the animal instance or null if not found
     * @throws NullPointerException if name is null
     */
    Animal findByName(String name);
}
