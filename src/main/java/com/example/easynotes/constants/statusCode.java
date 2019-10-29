/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.constants;

import org.springframework.http.HttpStatus;

/**
 *
 * @author ASUS
 */
public enum statusCode {
    NONE(0),
    SUCCESS(HttpStatus.OK.value()),
    SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value()),
    NOT_FOUND(HttpStatus.NOT_FOUND.value()),
    PARAMETER_INVALID(1000);
    private int value;

     statusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
     

    
    
}
