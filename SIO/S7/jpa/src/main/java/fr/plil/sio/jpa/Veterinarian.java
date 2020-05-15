package fr.plil.sio.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * An veterinarian is unique by name. An veterinarian may be connected to zero, one or several animals.
 */

@Entity
@Table(name = "VETERINARIAN_T")
public class Veterinarian implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(Veterinarian.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VETERINARIAN_ID")
    private Long id;

    @Column(name = "NAME_C", unique = true, nullable = false)
    private String name;

    @ManyToMany
    private List<Animal> animals = new LinkedList<Animal>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Veterinarian veterinarian = (Veterinarian) o;

        return name.equals(veterinarian.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
