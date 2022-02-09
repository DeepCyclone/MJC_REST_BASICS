package com.epam.esm.service.impl;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.TagRepository;
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

    public List<TagResponseDto> getAll(){
        return repository.readAll();
    }
    public TagResponseDto getByID(long ID){
        return repository.getByID(ID);
    }
    public TagResponseDto addEntity(TagDto tag) {
        return repository.create(tag);
    }

    @Transactional
    public void deleteByID(long ID){
        repository.deleteByID(ID);
    }

    public List<GiftCertificateResponseDto> getAssociatedCertificates(long ID){
        return null;
    }

}
