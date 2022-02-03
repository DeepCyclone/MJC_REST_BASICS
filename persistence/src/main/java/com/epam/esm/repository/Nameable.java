package com.epam.esm.repository;


/*
 * An extension to @see GenericRepository in case of object have name field
 * */
public interface Nameable<T> {
    T getByName(String name);
    void deleteByName(String name);
}
