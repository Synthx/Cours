package fr.plil.sio.persistence.api;

import java.util.LinkedList;
import java.util.List;

/**
 * A group is unique by its name (no two groups with the same name or the same ID can exist in the database).
 * A group contains a list of rights unique by their ID (no two groups with the same ID can exist in the database).
 */
public class Group {

    private Long id;

    private String name;

    /**
     * Users in the group.
     */
    private List<User> users = new LinkedList<>();

    /**
     * List of rights. The list CANNOT contains duplicate rights.
     */
    private List<Right> rights = new LinkedList<>();

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
