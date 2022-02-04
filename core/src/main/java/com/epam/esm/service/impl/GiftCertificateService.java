package com.epam.esm.service.impl;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.converter.TagDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final TagDtoConverter converter;

    @Autowired
    public GiftCertificateService(GiftCertificateRepository certificateRepository, TagRepository tagRepository, TagDtoConverter converter) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.converter = converter;
    }

    public List<GiftCertificate> getAll(){
        return certificateRepository.readAll();
    }
    public GiftCertificate getByID(long ID){
        return certificateRepository.getByID(ID);
    }

    public GiftCertificate addEntity(GiftCertificate certificate){
        return certificateRepository.create(certificate);
    }

    @Transactional
    public void deleteByID(long ID){
        certificateRepository.deleteByID(ID);
    }

    @Transactional
    public void update(GiftCertificateDto certificateDtoPatch,long certificateID){
        GiftCertificate originalCertificate = getByID(certificateID);
        Optional.ofNullable(certificateDtoPatch.getName()).ifPresent(originalCertificate::setName);
        Optional.ofNullable(certificateDtoPatch.getDescription()).ifPresent(originalCertificate::setDescription);
        Optional.ofNullable(certificateDtoPatch.getPrice()).ifPresent(originalCertificate::setPrice);
        Optional.ofNullable(certificateDtoPatch.getDuration()).ifPresent(originalCertificate::setDuration);
        List<Tag> tags = new ArrayList<>();
        for(TagDto dto:certificateDtoPatch.getAssociatedTags()) {
           tags.add(tagRepository.create(converter.convertFromDto(dto)));
        }
        linkAssociatedTags(certificateID,tags);
        certificateRepository.update(originalCertificate);

    }

    private void linkAssociatedTags(long certificateID,List<Tag> tags){
        certificateRepository.linkAssociatedTags(certificateID,tags);
    }

}
