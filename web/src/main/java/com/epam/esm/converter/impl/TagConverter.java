package com.epam.esm.converter.impl;

import com.epam.esm.converter.ConverterTemplate;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.repository.model.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagConverter implements ConverterTemplate<Tag, TagDto, TagResponseDto> {

    @Override
    public Tag convertFromRequestDto(TagDto dto) {
        return Tag.builder().
                id(dto.getId()).
                name(dto.getName()).
                build();
    }

    @Override
    public List<Tag> convertFromRequestDtos(List<TagDto> dtos) {
        return dtos.stream().map(this::convertFromRequestDto).collect(Collectors.toList());
    }

    @Override
    public TagResponseDto convertToResponseDto(Tag object) {
        return TagResponseDto.builder().
                id(object.getId()).
                name(object.getName()).
                build();
    }

    @Override
    public List<TagResponseDto> convertToResponseDtos(List<Tag> objects) {
        List<TagResponseDto> tags = new ArrayList<>();
        for(Tag object:objects){
            tags.add(convertToResponseDto(object));
        }
        return tags;
    }
}
