package com.epam.esm.repository;

public interface Nameable<T> {
    T getByName(String name);
    void deleteByName(String name);
}
