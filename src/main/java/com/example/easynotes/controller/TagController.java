package com.example.easynotes.controller;

import com.example.easynotes.model.Tag;
import com.example.easynotes.repository.TagRepository;
import com.example.easynotes.util.responseUtil;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
@RestController
public class TagController {

    @Autowired
    TagRepository tagRepo;

    @GetMapping(value = "/tags", produces = "application/json")
    public String getAllTag() {
        return responseUtil.responseListTag(tagRepo.findAll()).toString();
    }
    @PostMapping(value="/tag",produces ="application/json")
    public String addTag(@Valid @RequestBody Tag tag){
        if (StringUtils.isEmpty(tag.getName())) {
            return responseUtil.notFound();
        }else{
            tagRepo.save(tag);
            return responseUtil.success(responseUtil.responseTag(tag));
        }
       
    }
}
