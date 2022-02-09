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

    public List<GiftCertificateResponseDto> getAll(){
        return certificateRepository.readAll();
    }
    public GiftCertificateResponseDto getByID(long ID) {
        return certificateRepository.getByID(ID);
    }

    public GiftCertificateResponseDto addEntity(GiftCertificateDto certificateDto) throws RepositoryException {
        GiftCertificateResponseDto baseCert =  certificateRepository.create(certificateDto);
        List<TagResponseDto> savedTags = saveAssociatedTags(certificateDto.getAssociatedTags());
        baseCert.getAssociatedTags().addAll(savedTags);
        certificateRepository.linkAssociatedTags(baseCert.getId(),savedTags);
        return baseCert;
    }

    @Transactional
    public boolean deleteByID(long ID){
        return certificateRepository.deleteByID(ID);
    }

    @Transactional
    public GiftCertificateResponseDto update(GiftCertificateDto certificateDtoPatch, long certificateID) {
        GiftCertificateResponseDto originalCertificate = getByID(certificateID);
        Optional.ofNullable(certificateDtoPatch.getName()).ifPresent(originalCertificate::setName);
        Optional.ofNullable(certificateDtoPatch.getDescription()).ifPresent(originalCertificate::setDescription);
        Optional.ofNullable(certificateDtoPatch.getPrice()).ifPresent(originalCertificate::setPrice);
        Optional.ofNullable(certificateDtoPatch.getDuration()).ifPresent(originalCertificate::setDuration);
        if (certificateDtoPatch.getAssociatedTags() != null) {
            List<TagResponseDto> tags = saveAssociatedTags(certificateDtoPatch.getAssociatedTags());
            certificateRepository.linkAssociatedTags(certificateID, tags);
        }
        return certificateRepository.update(certificateConverter.fromResponseToRequestDto(originalCertificate),certificateID);
    }

    private List<TagResponseDto> saveAssociatedTags(List<TagDto> tagsDtos) throws RepositoryException {
        List<TagResponseDto> tags = new ArrayList<>();
        for(TagDto dto:tagsDtos) {
            tags.add(tagRepository.create(dto));
        }
        return tags;
    }

    private void detachAssociatedTags(long certificateID){
        certificateRepository.detachAssociatedTags(certificateID);
    }


    public List<GiftCertificateResponseDto> handleParametrizedGetRequest(Map<String,String> params){
        return certificateRepository.handleParametrizedRequest(params);
    }

    public GiftCertificateResponseDto getByName(String name) {
        return null;
    }
}
