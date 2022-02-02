package com.epam.esm.repository;

import java.util.List;

public interface GenericRepository <T>{
    long create(T object);
    List<T> readAll();
    boolean update(T object);
    boolean delete(T object);
}
