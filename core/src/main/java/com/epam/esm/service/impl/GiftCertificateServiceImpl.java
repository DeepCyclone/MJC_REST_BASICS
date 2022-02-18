package com.epam.esm.service.impl;

import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String nameSortOrder = "nameSortOrder";
    private static final String dateSortOrder = "dateSortOrder";
    private static final String ASCENDING_SORT = "ASC";
    private static final String DESCENDING_SORT = "DESC";

    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
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
        GiftCertificate baseCert = certificateRepository.create(certificateDto);
        List<Tag> savedTags = saveAssociatedTags(certificateDto.getAssociatedTags());
        if(baseCert.getAssociatedTags()!=null){
            baseCert.getAssociatedTags().addAll(savedTags);
        }
        else{
            baseCert.setAssociatedTags(savedTags);
        }
        certificateRepository.linkAssociatedTags(baseCert.getId(),savedTags);
        return baseCert;
    }

    @Transactional
    public boolean deleteByID(long ID){
        return certificateRepository.deleteByID(ID);
    }

    @Transactional
    public GiftCertificate update(GiftCertificate certificatePatch) {
        GiftCertificate originalCertificate = getByID(certificatePatch.getId());
        Optional.ofNullable(certificatePatch.getName()).ifPresent(originalCertificate::setName);
        Optional.ofNullable(certificatePatch.getDescription()).ifPresent(originalCertificate::setDescription);
        Optional.ofNullable(certificatePatch.getPrice()).ifPresent(originalCertificate::setPrice);
        originalCertificate.setDuration(certificatePatch.getDuration());
        detachAssociatedTags(certificatePatch.getId());
        if (certificatePatch.getAssociatedTags() != null) {
            List<Tag> tags = saveAssociatedTags(certificatePatch.getAssociatedTags());
            if(!tags.isEmpty()) {
                certificateRepository.linkAssociatedTags(certificatePatch.getId(), tags);
            }
        }
        certificateRepository.update(originalCertificate,certificatePatch.getId());
        return getByID(certificatePatch.getId());
    }

    private List<Tag> saveAssociatedTags(List<Tag> tags) {
        List<Tag> tagsIdentifiable = new ArrayList<>();
        for(Tag tag:tags) {
            Optional.ofNullable(tagRepository.create(tag)).ifPresent(tagsIdentifiable::add);
        }
        return tagsIdentifiable;
    }

    private void detachAssociatedTags(long certificateID){
        certificateRepository.detachAssociatedTags(certificateID);
    }


    public List<GiftCertificate> handleParametrizedGetRequest(Map<String,String> params){
        deleteInvalidValuedParams(params);
        return certificateRepository.handleParametrizedRequest(params);
    }

    private void deleteInvalidValuedParams(Map<String,String> params){
        if(params.containsKey(nameSortOrder) && !isAllowedOrderDirection(params.get(nameSortOrder))){
            params.remove(nameSortOrder);
        }
        if(params.containsKey(dateSortOrder) && !isAllowedOrderDirection(params.get(dateSortOrder))){
            params.remove(dateSortOrder);
        }
    }

    private static boolean isAllowedOrderDirection(String order){
        return order.equalsIgnoreCase(ASCENDING_SORT) || order.equalsIgnoreCase(DESCENDING_SORT);
    }

}
