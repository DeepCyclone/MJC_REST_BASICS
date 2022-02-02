package com.epam.esm.repository;

import java.util.List;

public interface GenericRepository <T>{
    T create(T object);
    List<T> readAll();
    void update(T object);
}
