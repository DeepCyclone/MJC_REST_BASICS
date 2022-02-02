package com.epam.esm.controller;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tags",produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTag(@RequestBody TagDto tagDto){
        System.out.println(tagDto);
    }
}
