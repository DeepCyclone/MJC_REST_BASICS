package com.epam.esm.controller;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags",produces = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {


    private final TagService tagService;
    private final TagConverter tagConverter;

    @Autowired
    public TagController(TagService tagService, TagConverter tagConverter) {
        this.tagService = tagService;
        this.tagConverter = tagConverter;
    }

    @GetMapping(value = "{id:\\d+}")
    public TagResponseDto getByID(@PathVariable("id") Long id){
        return tagConverter.convertToResponseDto(tagService.getByID(id));
    }

    @GetMapping
    public List<TagResponseDto> getTags(){
        return tagConverter.convertToResponseDtos(tagService.getAll());
    }

    @DeleteMapping(value = "{id:\\d+}")
    public void deleteTag(@PathVariable("id") Long id){
        tagService.deleteByID(id);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public TagResponseDto createTag(@RequestBody TagDto tagDto){
//        return tagService.addEntity(tagDto);
        return null;
    }

}
