package com.epam.esm.service;

import java.util.List;

public interface BasicService <T>{
    T create(T object);
    List<T> readAll();
    boolean update(T object);
}
