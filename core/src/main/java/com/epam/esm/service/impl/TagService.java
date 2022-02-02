package com.epam.esm.service.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    private final TagRepository repository;

    @Autowired
    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getAll(){
        return repository.readAll();
    }
    public Tag getByID(long ID){
        return repository.getByID(ID);
    }
    public void addEntity(Tag tag){
        repository.create(tag);
    }

    @Transactional
    public void deleteByID(long ID){
        repository.deleteByID(ID);
    }

    @Transactional
    public void update(Tag tag){
        repository.update(tag);
    }
}
