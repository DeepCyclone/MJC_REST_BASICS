package com.epam.esm.controller;

import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {


    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id}")
    public Tag getByID(@PathVariable("id") Long id){
        Tag tag = tagService.getByID(id);
        if(tag == null){throw new ObjectNotFoundException("Object with ID:"+id+"wasn't found",id);}
        return tag;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTag(@PathVariable("id") Long id){
        tagService.deleteByID(id);
    }

    @PatchMapping
    public void updateTag(){

    }

    @PostMapping
    public void createTag(){

    }
}
