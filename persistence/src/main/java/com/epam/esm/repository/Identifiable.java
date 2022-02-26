package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

import java.util.Optional;

/*
* An extension to {@link com.epam.esm.repository.GenericRepository} in case of object have ID field
* @author Flexus
* */
public interface Identifiable<T> {
    Optional<T> getByID(long ID) throws RepositoryException;
    boolean deleteByID(long ID);
}
