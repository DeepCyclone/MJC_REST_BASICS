package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

import java.util.List;


/*
* Basic hierarchical interface,that describes available operations on objects
* @params

* */
public interface GenericRepository <X>{
    /*
    * Takes dto(X) object,places it in datasource,returns response dto object with additional info
     */
    X create(X object);
    List<X> readAll();
    boolean update(X object,long ID);
}
