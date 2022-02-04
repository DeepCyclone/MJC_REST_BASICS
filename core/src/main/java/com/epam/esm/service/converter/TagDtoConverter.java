package com.epam.esm.service.converter;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.repository.model.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagDtoConverter implements ConverterTemplate<Tag, TagDto>{
    @Override
    public Tag convertFromDto(TagDto dto) {
        return Tag.builder().
                name(dto.getName()).
                build();
    }

    @Override
    public TagDto convertToDto(Tag object) {
        return null;
    }
}
