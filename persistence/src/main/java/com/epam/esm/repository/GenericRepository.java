package com.epam.esm.repository;

import java.util.List;


/*
* Basic hierarchical interface,that describes avaliable operations on objects
* */
public interface GenericRepository <T>{
    T create(T object);
    List<T> readAll();
    void update(T object);
}
