/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.plil.sio.jpa;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
    
    @Resource
    private NoteRepository noteRepository;

    @Override
    public Note save(String comment, Animal animal, Veterinarian veterinarian) {
        if (comment == null) {
            throw new NullPointerException("comment is null");
        }
        
        Note note = new Note();
        note.setComment(comment);
        note.setAnimal(animal);
        animal.getNotes().add(note);
        note.setVeterinarian(veterinarian);
        veterinarian.getNotes().add(note);
        
        return this.noteRepository.save(note);
    }
}
