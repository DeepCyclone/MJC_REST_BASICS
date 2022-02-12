package com.epam.esm.service.impl;


import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll(){
        return tagRepository.readAll();
    }
    public Tag getByID(long ID){
        return tagRepository.getByID(ID);
    }
    public Tag addEntity(Tag tag) {
        return tagRepository.create(tag);
    }

    @Transactional
    public void deleteByID(long ID){
        if(!tagRepository.deleteByID(ID)){
            throw new ServiceException(ErrorCode.TAG_DELETION_ERROR,"Couldn't delete tag with ID = "+ID);
        }
    }

    public List<GiftCertificate> getAssociatedCertificates(long ID){
        return null;
    }

}
