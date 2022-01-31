package com.epam.esm.repository;

import com.epam.esm.repository.model.Tag;

public interface TagRepository extends GenericRepository<Tag>{
    @Override
    boolean update(Tag tag) throws UnsupportedOperationException;
}
