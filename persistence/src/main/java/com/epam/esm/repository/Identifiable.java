package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

/*
* An extension to {@link com.epam.esm.repository.GenericRepository} in case of object have ID field
* @author Flexus
* */
public interface Identifiable<T> {
    T getByID(long ID) throws RepositoryException;
    boolean deleteByID(long ID);
}
