/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.plil.sio.jpa;

import javax.persistence.*;

/**
 *
 * @author rtaniel
 */
@Entity
@Table(name = "NOTE_T")
public class Note {
    
    @Id
    @GeneratedValue
    @Column(name = "NOTE_ID")
    private Long id;
    
    @Column(name = "NAME_C")
    private String comment;
    
    @ManyToOne
    @JoinColumn(name = "ANIMAL_ID")
    private Animal animal;
    
    @ManyToOne
    @JoinColumn(name = "VETERINARIAN_ID")
    private Veterinarian veterinarian;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    
    public Veterinarian getVeterinarian() {
        return veterinarian;
    }
    
    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }
}
