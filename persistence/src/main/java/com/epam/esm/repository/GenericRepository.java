package com.epam.esm.repository;

import com.epam.esm.exception.RepositoryException;

import java.util.List;


/*
* Basic hierarchical interface,that describes avaliable operations on objects
* @params
* X - request dto's
* Y - response dto's
* */
public interface GenericRepository <X,Y>{
    /*
    * Takes dto(X) object,places it in datasource,returns response dto object with additional info
     */
    Y create(X object);
    List<Y> readAll();
    Y update(X object,long ID);
}
