package com.epam.esm.service.impl;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapping.CertificateResponseDtoMapper;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.converter.CertificateDtoConverter;
import com.epam.esm.service.converter.TagDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final TagDtoConverter tagConverter;
    private final CertificateDtoConverter certificateConverter;


    @Autowired
    public GiftCertificateService(GiftCertificateRepository certificateRepository,
                                  TagRepository tagRepository,
                                  TagDtoConverter tagConverter, CertificateDtoConverter certificateConverter) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
        this.certificateConverter = certificateConverter;
    }

    public List<GiftCertificate> getAll(){
        List<GiftCertificate> certificates = certificateRepository.readAll();
        for(GiftCertificate certificate:certificates){
            certificate.setAssociatedTags(tagRepository.fetchAssociatedTags(certificate.getId()));
        }
        return certificates;
    }
    public GiftCertificate getByID(long ID) {
        GiftCertificate certificate = certificateRepository.getByID(ID);
        certificate.setAssociatedTags(tagRepository.fetchAssociatedTags(ID));
        return certificate;
    }

    public GiftCertificate addEntity(GiftCertificate certificateDto) throws RepositoryException {
        GiftCertificate baseCert =  certificateRepository.create(certificateDto);
        List<Tag> savedTags = saveAssociatedTags(certificateDto.getAssociatedTags());
        baseCert.getAssociatedTags().addAll(savedTags);
        certificateRepository.linkAssociatedTags(baseCert.getId(),savedTags);
        return baseCert;
    }

    @Transactional
    public boolean deleteByID(long ID){
        return certificateRepository.deleteByID(ID);
    }

    @Transactional
    public GiftCertificate update(GiftCertificate certificatePatch, long certificateID) {
        GiftCertificate originalCertificate = getByID(certificateID);
        Optional.ofNullable(certificatePatch.getName()).ifPresent(originalCertificate::setName);
        Optional.ofNullable(certificatePatch.getDescription()).ifPresent(originalCertificate::setDescription);
        Optional.ofNullable(certificatePatch.getPrice()).ifPresent(originalCertificate::setPrice);
        Optional.ofNullable(certificatePatch.getDuration()).ifPresent(originalCertificate::setDuration);
        if (certificatePatch.getAssociatedTags() != null) {
            List<Tag> tags = saveAssociatedTags(certificatePatch.getAssociatedTags());
            certificateRepository.linkAssociatedTags(certificateID, tags);
        }
        certificateRepository.update(originalCertificate,certificateID);
        return getByID(certificateID);//TODO rethink
    }

    private List<Tag> saveAssociatedTags(List<Tag> tags) {
        List<Tag> tagsIdentifiable = new ArrayList<>();
        for(Tag tag:tags) {
            tagsIdentifiable.add(tagRepository.create(tag));
        }
        return tagsIdentifiable;
    }

    private void detachAssociatedTags(long certificateID){
        certificateRepository.detachAssociatedTags(certificateID);
    }


    public List<GiftCertificate> handleParametrizedGetRequest(Map<String,String> params){
        return certificateRepository.handleParametrizedRequest(params);
    }

    public GiftCertificate getByName(String name) {
        return null;
    }
}
