/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Category;
import com.example.easynotes.repository.CategoryRepository;
import com.example.easynotes.util.responseUtil;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    /**
     *
     * @return
     */
    @GetMapping(value="/categories",produces = "application/json")
    public String getAllCategories(){
        List<Category> categories= categoryRepository.findAll();
        if (categories.isEmpty()) {
            return responseUtil.notFound();
        }else{
            return responseUtil.success(responseUtil.responseListCategory(categories));
        }
    }

    /**
     *
     * @param id
     * @return
     */
//    @GetMapping(value="/category/{id}",produces = "application/json")
//    public String getCategory(@PathVariable(value = "id")Long id){
//        return respoUtil.responseCategory(categoryRepository.findById(id)).toString();
//    }
    
    @PostMapping(value= "/category",produces = "application/json")
    public String createCategory(@Valid @RequestBody Category cate){
         if (StringUtils.isEmpty(cate.getContent())||StringUtils.isEmpty(cate.getTitle())) {
            return responseUtil.notFound();
        }else{
             categoryRepository.save(cate);
             return responseUtil.success(responseUtil.responseCategory(cate));
         }
    }
    
    @PutMapping("/category/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long id
            ,@Valid @RequestBody Category cate){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",id));
        category.setTitle(cate.getTitle());
        category.setContent(cate.getContent());
        Category categoryUpdate = categoryRepository.save(category);
        return categoryUpdate;
    }
    
    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id")Long id){
        Category category =categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }
}
