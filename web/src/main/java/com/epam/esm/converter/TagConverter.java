package com.epam.esm.converter;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.repository.model.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverter implements ConverterTemplate<Tag, TagDto, TagResponseDto>{

    @Override
    public Tag convertFromRequestDto(TagDto dto) {
        return Tag.builder().
                id(dto.getId()).
                name(dto.getName()).
                build();
    }

    @Override
    public List<Tag> convertFromRequestDtos(List<TagDto> dtos) {
        List<Tag> tags = new ArrayList<>();
        if(dtos!=null) {
            for (TagDto dto : dtos) {
                tags.add(convertFromRequestDto(dto));
            }
        }
        return tags;
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
