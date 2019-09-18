package fr.plil.sio.persistence.api;

/**
 * An user MUST have a group in the database.
 * An user is unique by it name, i.e. database cannot contain two user with the same name or the same ID.
 */
public class User {

    private Long id;

    private String name;

    private Group group;

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
