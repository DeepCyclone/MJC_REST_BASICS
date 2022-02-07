package com.epam.esm.service.impl;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.converter.CertificateDtoConverter;
import com.epam.esm.service.converter.TagDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        return certificateRepository.readAll();
    }
    public GiftCertificate getByID(long ID){
        return certificateRepository.getByID(ID);
    }

    public GiftCertificate addEntity(GiftCertificateDto certificateDto){
        GiftCertificate certificate = certificateConverter.convertFromDto(certificateDto);
        GiftCertificate baseCert =  certificateRepository.create(certificate);
        List<Tag> tags = saveAssociatedTags(certificateDto.getAssociatedTags());
        linkAssociatedTags(baseCert.getId(),tags);
        return baseCert;
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
        //TODO boolean or exception
        List<Tag> tags = saveAssociatedTags(certificateDtoPatch.getAssociatedTags());
        linkAssociatedTags(certificateID,tags);
        certificateRepository.update(originalCertificate);

    }

    private List<Tag> saveAssociatedTags(List<TagDto> tagsDtos){
        List<Tag> tags = new ArrayList<>();
        for(TagDto dto:tagsDtos) {
            tags.add(tagRepository.create(tagConverter.convertFromDto(dto)));
        }
        return tags;
    }

    private void linkAssociatedTags(long certificateID,List<Tag> tags){
        certificateRepository.linkAssociatedTags(certificateID,tags);
    }


    public List<GiftCertificateResponseDto> handleParametrizedGetRequest(String tagName, String namePart, String descriptionPart, String dateSortOrder, String nameSortOrder){
        return certificateRepository.handleParametrizedRequest(tagName, namePart, descriptionPart, dateSortOrder, nameSortOrder);
    }
}
