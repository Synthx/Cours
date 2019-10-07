package fr.plil.sio.persistence.api;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * A right is unique by itd ID, i.e. it can exist two rights with the same name in the database.
 * A right may have a parent, null else.
 * A right can have zero, one or more siblings.
 */
@Entity
@Table(name = "RIGHT_T")
public class Right {

    @Id
    @GeneratedValue
    @Column(name = "RIGHT_ID")
    private Long id;

    @Column(name = "NAME_C", nullable = false)
    private String name;

    /// the parent right
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Right parent;

    /// the sibling right(s), eventually empty
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Right> siblings = new LinkedList<>();

    public List<Right> getSiblings() {
        return siblings;
    }

    public void setSiblings(List<Right> siblings) {
        this.siblings = siblings;
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

    public Right getParent() {
        return parent;
    }

    public void setParent(Right parent) {
        this.parent = parent;
    }
}
