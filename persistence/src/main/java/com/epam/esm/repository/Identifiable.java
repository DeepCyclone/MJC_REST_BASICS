package com.epam.esm.repository;

/*
* An extension to @see GenericRepository in case of object have ID field
* */
public interface Identifiable<T> {
    public T getByID(long ID);
    public void deleteByID(long ID);
}
