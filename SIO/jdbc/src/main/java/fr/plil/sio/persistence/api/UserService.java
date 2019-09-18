package fr.plil.sio.persistence.api;

public interface UserService {

    /**
     * Create an user with a specific name in the database and affected to an existing group.
     * There is no two users with the same name in the database.
     * Only references at one level are loaded (i.e. the group where the user belong).
     *
     * @param name the name of the user
     * @param groupName the name of the group
     * @return an instance of the user
     * @throws IllegalArgumentException if name or groupName is null, or if group does not exist.
     * @throws IllegalStateException    if an user with the same name is already present
     */
    User create(String name, String groupName);

    /**
     * Delete an user in the database.
     *
     * @param name the name of the user to remove
     * @return true if user has been deleted, false if user is not found in the database.
     * @throws IllegalArgumentException if name is null
     */
    boolean delete(String name);

    /**
     * Find an user in the database based on its name.
     *
     * @param name the name of the user to search for.
     * @return an instance of the user if found, else null.
     * @throws IllegalArgumentException if name is null
     */
    User findByName(String name);

    /**
     * Check method to control if an user has a specific right. The following rule is used: an user has a specific
     * right if the group where the user is contains the specific right or one of its parents.
     *
     * @param userName the name of the user
     * @param right    the specific right
     * @return true if the group where the user is contains the specific right or one of its parents, false else.
     * @throws IllegalArgumentException if userName or right is null, or if the user or right does not exist in the db.
     */
    boolean isUserHasRight(String userName, Right right);
}
