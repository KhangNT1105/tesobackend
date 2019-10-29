/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ASUS
 */
@Embeddable
public class TagNoteKey implements Serializable{
    
    @Column(name="note_id")
    Long noteId;
    
    @Column(name="tag_id")
    Long tagId;
}
