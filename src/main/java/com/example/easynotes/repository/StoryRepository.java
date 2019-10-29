/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.repository;

import com.example.easynotes.model.Story;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ASUS
 */
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(value = "SELECT * FROM story s WHERE s.name LIKE %?%1 ", nativeQuery = true)
    List<Story> findStoryByName(String name);

    @Query(value = "SELECT * FROM story s WHERE s.id=?1", nativeQuery = true)
    public Story findStoryById(Long id);
}
