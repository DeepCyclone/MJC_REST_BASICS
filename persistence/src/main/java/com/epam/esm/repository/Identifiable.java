package com.epam.esm.repository;

/*
* An extension to @see GenericRepository in case of object have ID field
* */
public interface Identifiable<T> {
    T getByID(long ID);
    boolean deleteByID(long ID);
}
