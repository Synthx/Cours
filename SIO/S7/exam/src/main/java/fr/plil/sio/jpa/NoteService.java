/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.plil.sio.jpa;

/**
 *
 * @author rtaniel
 */
public interface NoteService {
    
    /**
     * Add a new note in database
     * 
     * @param comment
     * @return 
     */
    Note save(String comment, Animal animal, Veterinarian veterinarian);
}
