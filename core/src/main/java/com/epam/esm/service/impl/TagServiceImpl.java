package com.epam.esm.service.impl;


import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAll(){
        return tagRepository.readAll();
    }
    @Override
    public Tag getByID(long ID){
        return Optional.ofNullable(tagRepository.getByID(ID)).orElseThrow(
                ()->new ServiceException(ErrorCode.TAG_NOT_FOUND,"Couldn't fetch tag with ID = "+ID));
    }
    @Override
    public Tag addEntity(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    @Transactional
    public void deleteByID(long ID){
        boolean flushingResult = tagRepository.deleteByID(ID);
        if(!flushingResult){
            throw new ServiceException(ErrorCode.TAG_DELETION_ERROR,"Couldn't delete tag with ID = "+ID);
        }
    }


}
