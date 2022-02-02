package com.epam.esm.service.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.model.Tag;

public class TagDtoConverter implements ConverterTemplate<Tag, TagDto>{
    @Override
    public Tag convertFromDto(TagDto dto) {
        return null;
    }

    @Override
    public TagDto convertToDto(Tag object) {
        return null;
    }
}
