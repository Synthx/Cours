package fr.plil.sio.persistence.api;

import java.util.List;

public interface RightService {

    /**
     * Create a parent right with a specific name in the database.
     * It is possible that two rights has the same name.
     *
     * @param name the name of the right
     * @throws IllegalArgumentException if name is null
     */
    Right create(String name);

    /**
     * Create a sibling right attached to a parent right with a specific name in the database.
     * It is possible that two rights has the same name.
     *
     * @param name   the name of the right
     * @param parent the parent right
     * @throws IllegalArgumentException if name or parent is null, or if parent does not exist in the database.
     */
    Right create(String name, Right parent);

    /**
     * Delete a right in the database. Delete sibling rights if present.
     *
     * @param right the right to delete
     * @return true if right has been deleted, false else.
     * @throws IllegalArgumentException if right is null.
     */
    boolean delete(Right right);

    /**
     * Find a list of rights in the database based on their name.
     *
     * @param name the name of the rights to search for.
     * @return A list of rights, eventually empty.
     * @throws IllegalArgumentException if name is null
     */
    List<Right> findByName(String name);

    /**
     * Find a right in the database based on its id.
     *
     * @param id the name of the right to search for.
     * @return an instance of the right if found, else null.
     * @throws IllegalArgumentException if id is null
     */
    Right findOne(Long id);
}
