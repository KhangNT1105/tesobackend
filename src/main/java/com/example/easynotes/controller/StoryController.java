/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.controller;

import com.example.easynotes.model.Chapter;
import com.example.easynotes.model.Story;
import com.example.easynotes.repository.ChapterRepository;
import com.example.easynotes.repository.StoryRepository;
import com.example.easynotes.util.responseUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class StoryController {

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @PostMapping(value = "/getstoryupdated", produces = "application/json")
    public String getStoryUpdated() throws IOException {
        Document doc = Jsoup.connect("https://truyenfull.vn").get();
        Elements elements = doc.select("div.list-new>div.row>div.col-title>h3>a");
        List<Story> stories = new ArrayList<>();
        for (Element element : elements) {
            Story story = new Story();
            List<Chapter> chapters = new ArrayList<>();
            System.out.println("Title : " + element.text());
            story.setName(element.text());
            System.out.println("link : " + element.attr("href"));
            story.setLink(element.attr("href"));
            story.setChapters(chapters);
            storyRepository.save(story);
            getChapter(element.attr("href"), story);
            stories.add(story);
        }
        return responseUtil.success(responseUtil.responseListStory(stories));
    }

    @GetMapping(value = "/getstory", produces = "application/json")
    public String getStory() {
        return responseUtil.success(responseUtil.responseListStory(storyRepository.findAll()));
    }

    public void getChapter(String link, Story story) throws IOException {
        Document doc1;
        Element gly;
        for (int i = 1; i < 100; i++) {
            doc1 = Jsoup.connect(link + "trang-" + i + "/#list-chapter").get();
            Elements cols = doc1.select("div.row>div.col-md-6>ul>li>a");
            for (Element col : cols) {
                Chapter chapter = new Chapter();
                chapter.setStory(story);
                System.out.println(col.text());
                chapter.setName(col.text());
                System.out.println("link : " + col.attr("href"));
                chapter.setLinkChapter(col.attr("href"));
                chapterRepository.save(chapter);
            }
            gly = doc1.selectFirst("span.glyphicon-menu-right");
            if (gly == null) {
                System.out.println("Ket thuc //");
                break;
            }
        }
    }

    @GetMapping(value = "/getstory/{name}", produces = "application/json")
    public String getStoryByName(@PathVariable("name") String name) throws IOException {
        List<Story> stories = storyRepository.findStoryByName(name);
        if (stories.isEmpty()) {
            return responseUtil.notFound();
        } else {
            return responseUtil.success(responseUtil.responseListStory(stories));
        }
    }

    @GetMapping(value = "/getstorybyid/{id}", produces = "application/json")
    public String getStoryById(@PathVariable("id") Long id) {
        Optional<Story> story = storyRepository.findById(id);
        if (story.isPresent()) {
            return responseUtil.success(responseUtil.responseStory(story.get()));
        } else {
            return responseUtil.notFound();
        }
    }

    @PostMapping(value = "/poststory", produces = "application/json")
    public String postStory(@Valid @RequestBody Story story) {
        if (StringUtils.isEmpty(story.getLink()) || StringUtils.isEmpty(story.getName())) {
            return responseUtil.inValid();
        } else {
            return responseUtil.success(responseUtil.responseStory(story));
        }
    }
}
