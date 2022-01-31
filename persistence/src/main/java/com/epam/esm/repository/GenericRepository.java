package com.epam.esm.repository;

import java.util.List;

public interface GenericRepository <T>{
    boolean create(T object);
    List<T> readAll();
    T readByID(long ID);
    boolean update(T object);
    boolean delete(T object);
}
