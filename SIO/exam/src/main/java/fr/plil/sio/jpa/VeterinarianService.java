package fr.plil.sio.jpa;

import java.util.List;

public interface VeterinarianService {

    /**
     * Create an veterinarian.
     *
     * @param name name of the veterinarian
     * @return the new veterinarian instance
     * @throws NullPointerException  if name is null
     * @throws IllegalStateException if an veterinarian with the same name exist in the database
     */
    Veterinarian createVeterinarian(String name);

    /**
     * Delete an veterinarian from the database.
     *
     * @param name name of the veterinarian to delete
     * @throws NullPointerException  if name is null
     * @throws IllegalStateException if veterinarian does not exist in the database.
     */
    void removeVeterinarian(String name);

    /**
     * Return all veterinarians in the database.
     *
     * @return The list of veterinarians.
     */
    List<Veterinarian> findAll();

    /**
     * Return a veterinarian instance with specified name.
     *
     * @param name the name of the veterinarian to return.
     * @return the veterinarian instance or null if not found
     * @throws NullPointerException if parameter is null
     */
    Veterinarian findByName(String name);

    /**
     * Add an animal to a veterinarian in the database.
     *
     * @param animalName       the animal name to add
     * @param veterinarianName the veterinarian name added to the animal
     * @return false if animal was already associated to veterinarian, false else
     * @throws NullPointerException if animal name or veterinarian name is null
     * @throws IllegalStateException if animal or veterinarian is not found in the database.
     */
    boolean addAnimal(String animalName, String veterinarianName);


    /**
     * Remove an animal to a veterinarian in the database.
     *
     * @param animalName       the animal name to remove
     * @param veterinarianName the veterinarian name removed from the animal
     * @return false if animal was not associated to veterinarian, false else
     * @throws NullPointerException  if animal name or veterinarian name is null
     * @throws IllegalStateException if animal or veterinarian is not found in the database
     */
    boolean removeAnimal(String animalName, String veterinarianName);
    
    /**
    * Add a comment to an animal.
    *
    * @param veterinarian the veterinarian who write the comment
    * @param animal the "commented" animal 
    * @param comment the text of the comment
    * @throws NullPointerException  if comment is null
    * @throws IllegalStateException if animal is not attached to the veterinarian
    */
    public Note addNote(Veterinarian veterinarian, Animal animal, String comment);
}
