package com.epam.esm.repository;

import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface TagRepository extends GenericRepository<Tag>,Identifiable<Tag>,Nameable<Tag>{
    List<Tag> fetchAssociatedTags(long certificateID);
}
