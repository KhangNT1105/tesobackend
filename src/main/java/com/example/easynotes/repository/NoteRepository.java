/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.repository;

import com.example.easynotes.model.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author ASUS
 */
@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    @Query(value="select * from notes n where n.title = ?1 ",nativeQuery = true)
    List<Note> findTitle(String title);

    @Query(value="delete from notes n where n.id=?1",nativeQuery = true)
    public void deleteNoteById(Long id);
    
    
}
