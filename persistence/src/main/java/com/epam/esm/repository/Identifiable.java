package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

/*
* An extension to @see GenericRepository in case of object have ID field
* */
public interface Identifiable<T> {
    T getByID(long ID) throws RepositoryException;
    boolean deleteByID(long ID);
}
