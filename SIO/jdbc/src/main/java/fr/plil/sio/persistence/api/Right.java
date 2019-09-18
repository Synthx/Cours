package fr.plil.sio.persistence.api;

import java.util.LinkedList;
import java.util.List;

/**
 * A right is unique by itd ID, i.e. it can exist two rights with the same name in the database.
 * A right may have a parent, null else.
 * A right can have zero, one or more siblings.
 */
public class Right {

    private Long id;

    private String name;

    /// the parent right
    private Right parent;

    /// the sibling right(s), eventually empty
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
