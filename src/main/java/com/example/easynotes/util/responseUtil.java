/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.util;

import com.example.easynotes.constants.statusCode;
import com.example.easynotes.model.Category;
import com.example.easynotes.model.Chapter;
import com.example.easynotes.model.Note;
import com.example.easynotes.model.Story;
import com.example.easynotes.model.Tag;
import com.example.easynotes.model.TagNote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class responseUtil {

    private static ObjectMapper oM = new ObjectMapper();

    public static ObjectNode responseNote(Note note) {
        ObjectNode node = oM.createObjectNode();
        node.put("id", note.getId());
        node.put("content", note.getContent());
        node.put("title", note.getTitle());
        return node;
    }

    public static ArrayNode responseListNotes(List<Note> notes) {
        ArrayNode arrayNode = oM.createArrayNode();
        for (Note note : notes) {
            arrayNode.add(responseNote(note));
        }
        return arrayNode;
    }

    public static ObjectNode responseCategory(Category category) {
        ObjectNode node = oM.createObjectNode();
        node.put("id", category.getId());
        node.put("content", category.getContent());
        node.put("title", category.getTitle());
        node.set("notes", responseListNotes(category.getNotes()));
        return node;
    }

    public static ArrayNode responseListCategory(List<Category> categories) {
        ArrayNode arrayNode = oM.createArrayNode();
        for (Category category : categories) {
            arrayNode.add(responseCategory(category));
        }
        return arrayNode;
    }

    public static ObjectNode responseTag(Tag tag) {
        ObjectNode objectNode = oM.createObjectNode();
        objectNode.put("id", tag.getId());
        objectNode.put("name", tag.getName());
        return objectNode;
    }

    public static ArrayNode responseListTag(List<Tag> tags) {
        ArrayNode arrayNode = oM.createArrayNode();
        tags.forEach((tag) -> {
            arrayNode.add(responseTag(tag));
        });
        return arrayNode;
    }

    public static String success(JsonNode body) {
        ObjectNode objectNode = oM.createObjectNode();
        objectNode.put(statusCode.class.getSimpleName(), statusCode.SUCCESS.getValue());
        objectNode.put("message", statusCode.SUCCESS.name());
        objectNode.set("response", body);
        return objectNode.toString();
    }

    public static String notFound() {
        ObjectNode objectNode = oM.createObjectNode();
        objectNode.put(statusCode.class.getSimpleName(), statusCode.NOT_FOUND.getValue());
        objectNode.put("message", statusCode.NOT_FOUND.name());
        objectNode.put("response", "");
        return objectNode.toString();
    }

    public static String inValid() {
        ObjectNode objectNode = oM.createObjectNode();
        objectNode.put(statusCode.class.getSimpleName(), statusCode.PARAMETER_INVALID.getValue());
        objectNode.put("message", statusCode.PARAMETER_INVALID.name());
        objectNode.put("response", "");
        return objectNode.toString();
    }

    public static ObjectNode responseChapter(Chapter chapter) {
        ObjectNode objectNode = oM.createObjectNode();
        objectNode.put("id", chapter.getId());
        objectNode.put("name", chapter.getName());
        objectNode.put("link", chapter.getLinkChapter());
        return objectNode;
    }

    public static ArrayNode responseListChapter(List<Chapter> chapters) {
        ArrayNode arrayNode = oM.createArrayNode();
        for (Chapter chapter : chapters) {
            arrayNode.add(responseChapter(chapter));
        }
        return arrayNode;
    }

    public static ObjectNode responseStory(Story story) {
        ObjectNode objectNode = oM.createObjectNode();
        objectNode.put("id", story.getId());
        objectNode.put("name", story.getName());
        objectNode.put("link", story.getLink());
        objectNode.set("chapter", responseListChapter(story.getChapters()));
        return objectNode;

    }

    public static ArrayNode responseListStory(List<Story> stories) {
        ArrayNode arrayNode = oM.createArrayNode();
        for (Story story : stories) {
            arrayNode.add(responseStory(story));
        }
        return arrayNode;
    }
}
