/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.controller;

import com.example.easynotes.model.Chapter;
import com.example.easynotes.repository.ChapterRepository;
import com.example.easynotes.util.responseUtil;
import java.io.IOException;
import javax.validation.Valid;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
public class ChapterController {
    
    @Autowired
    ChapterRepository chapterRepository;
    
    @GetMapping(value = "/getchapters", produces = "application/json")
    public String getChapter() {
        return responseUtil.success(responseUtil.responseListChapter(chapterRepository.findAll()));
    }
    
    @PostMapping(value = "/postchapter", produces = "application/json")
    public String postChapter(@Valid @RequestBody Chapter chapter) {
        if (StringUtils.isEmpty(chapter.getName()) || StringUtils.isEmpty(chapter.getLinkChapter()) || StringUtils.isEmpty(chapter.getStory())) {
            return responseUtil.inValid();
        } else {
            return responseUtil.success(responseUtil.responseChapter(chapter));
        }
    }
}
