package fr.plil.sio.jpa;

import java.util.List;

public interface OwnerService {

    /**
     * Create an owner.
     *
     * @param name name of the owner
     * @return the new owner instance
     * @throws NullPointerException  if name is null
     * @throws IllegalStateException if an owner with the same name exist in the database
     */
    Owner create(String name);

    /**
     * Delete an owner from the database and remove all attached animals.
     *
     * @param name name of the owner to delete
     * @throws NullPointerException  if name is null
     * @throws IllegalStateException if owner does not exist in the database.
     */
    void remove(String name);

    /**
     * Return all owners in the database. Only one level dependencies are loaded (animals).
     *
     * @return The list of animals.
     */
    List<Owner> findAll();

    /**
     * Return an owner instance with specified name. Only one level dependencies are loaded (animals).
     *
     * @param name the name of the owner to return.
     * @return the owner instance or null if not found
     * @throws NullPointerException if parameter is null
     */
    Owner findByName(String name);
}
