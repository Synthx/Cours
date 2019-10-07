package fr.plil.sio.persistence.api;

import javax.persistence.*;

/**
 * An user MUST have a group in the database.
 * An user is unique by it name, i.e. database cannot contain two user with the same name or the same ID.
 */
@Entity
@Table(name = "USER_T")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "NAME_C", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
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
