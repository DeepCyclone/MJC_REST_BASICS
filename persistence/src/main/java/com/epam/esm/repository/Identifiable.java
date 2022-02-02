package com.epam.esm.repository;

public interface Identifiable<T> {
    public T getByID(long ID);
    public T deleteByID(long ID);
}
