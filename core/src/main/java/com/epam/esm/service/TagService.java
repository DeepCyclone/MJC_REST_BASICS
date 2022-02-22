package com.epam.esm.service;

import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAll();
    Tag getByID(long ID);
    Tag addEntity(Tag tag);
    void deleteByID(long ID);
}
