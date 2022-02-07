package com.epam.esm.service;

public interface IdentifiableService <T>{
    T getByID(String name);
    boolean deleteByID(String name);
}
