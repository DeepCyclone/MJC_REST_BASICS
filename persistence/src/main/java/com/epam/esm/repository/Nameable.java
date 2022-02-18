package com.epam.esm.repository;


/*
 * An extension to {@link com.epam.esm.repository.GenericRepository} in case of object have name field
 * @author Flexus
 * */
public interface Nameable<T> {
    T getByName(String name);
}
