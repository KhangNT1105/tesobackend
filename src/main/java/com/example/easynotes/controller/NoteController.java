/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.controller;

import com.example.easynotes.constants.statusCode;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.util.responseUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.easynotes.util.responseUtil;
import java.util.Optional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;
    
    //method : GET
    //Get all notes from the database
    @GetMapping(value= "/notes",produces = "application/json" )
    public String getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        if (notes.isEmpty()) {
            return responseUtil.notFound();
        }
        else{
            return responseUtil.success(responseUtil.responseListNotes(notes));
        }
    }

    //method : POST
    //Create a new note
//    @PostMapping("/notes")
//    public Note createNote(@Valid @RequestBody Note note) {
//        return noteRepository.save(note);
//    }
    @PostMapping(value="/notes",produces = "application/json")
    public String createNote(@Valid @RequestBody Note note){
        if (StringUtils.isEmpty(note.getTitle())||StringUtils.isEmpty(note.getContent())) {
            return responseUtil.notFound();
        }else{
            noteRepository.save(note);
            return responseUtil.success(responseUtil.responseNote(note));
        }
    }

//    //method:GET
//    //Get a single note
//    @GetMapping("/notes/{id}")
//    public Note getNoteById(@PathVariable(value = "id") Long noteId) {
//        return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
//    }
    @GetMapping(value="/notes/{id}",produces = "application/json")
    public String getNoteById(@PathVariable(value = "id")Long noteId){
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent()) {
            return responseUtil.success(responseUtil.responseNote(note.get()));
        }else{
            return responseUtil.notFound();
        }
    }
    
    //method:PUT
    //Update a note
    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable("id") Long noteId,@Valid @RequestBody Note noteDetails){
        Note note =noteRepository.findById(noteId).orElseThrow(()->new ResourceNotFoundException("Note", "id", noteId));
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        
        Note updateNote = noteRepository.save(note);
        return updateNote;
    }
    @GetMapping(value="/notetag",produces = "application/json")
    public String getNoteByTitle(HttpServletRequest servlet ){
        String title = servlet.getParameter("title");
        List<Note> notes = noteRepository.findTitle(title);
        String response = responseUtil.responseListNotes(notes).toString();
        return response;
    }
    @DeleteMapping(value="/deletenote/{id}",produces = "application/json")
    public String deleteNoteById(@PathVariable(value = "id")Long noteId){
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent()) {
            noteRepository.delete(note.get()); 
            return responseUtil.success(responseUtil.responseNote(note.get()));
        }else{
            return responseUtil.notFound();
        }
    }
}
