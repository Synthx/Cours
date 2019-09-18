package fr.plil.sio.persistence.api;

public interface GroupService {

    /**
     * Create a group with a specific name in the database.
     * There is no two groups with the same name or the same ID in the database.
     *
     * @param name the name of the group
     * @return an instance of the group
     * @throws IllegalArgumentException if name is null
     * @throws IllegalStateException if a group with the same name is already present
     */
    Group create(String name);

    /**
     * Delete a group in the database. Remove all users in the group.
     *
     * @param name the name of the group to remove
     * @return true if group has been deleted, false if group is not found in the database.
     * @throws IllegalArgumentException if name is null
     */
    boolean delete(String name);

    /**
     * Find a group in the database based on its name. Only references at one level are loaded (i.e. the users
     * who belong to the group).
     *
     * @param name the name of the group to search for.
     * @return an instance of the group if found, else null.
     * @throws IllegalArgumentException if name is null
     */
    Group findByName(String name);

    /**
     * Add a right in the group.
     *
     * @param groupName the name of the group.
     * @param right     the right to add
     * @return true if right is added in the group, false if right was already present.
     * @throws IllegalArgumentException if groupName or right is null, or if group or right cannot be found.
     */
    boolean addRight(String groupName, Right right);

    /**
     * Remove a right associated with a group.
     *
     * @param groupName the name of the group.
     * @param right     the right to remove
     * @return true if right is removed from the group, false if teh right was not present in the group.
     * @throws IllegalArgumentException if groupName or right is null, or if group or right cannot be found.
     */
    boolean removeRight(String groupName, Right right);
}
