package com.epam.esm.repository;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface TagRepository extends GenericRepository<TagDto, TagResponseDto>,Identifiable<TagResponseDto>,Nameable<TagResponseDto>{
    List<TagResponseDto> fetchAssociatedTags(long certificateID);
}
