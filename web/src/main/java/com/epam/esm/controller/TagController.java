package com.epam.esm.controller;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags",produces = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {


    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id:\\d+}")
    public TagResponseDto getByID(@PathVariable("id") Long id){
        TagResponseDto tag = tagService.getByID(id);
        if(tag == null){throw new ObjectNotFoundException("Object with ID:"+id+" wasn't found",id);}//TODO optional or else throw and catch it in controller advice
        return tag;
    }

    @GetMapping
    public List<TagResponseDto> getTags(){
        return tagService.getAll();
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteTag(@PathVariable("id") Long id){
        tagService.deleteByID(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TagResponseDto createTag(@RequestBody TagDto tagDto){
//        return tagService.addEntity(tagDto);
        return null;
    }

}
