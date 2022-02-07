package com.epam.esm.service;

public interface NameableService<T> {
    T getByName(String name);
    boolean deleteByName(String name);
}
