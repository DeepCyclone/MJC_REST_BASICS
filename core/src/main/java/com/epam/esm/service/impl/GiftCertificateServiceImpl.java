package com.epam.esm.service.impl;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.exception.ErrorCode.CERTIFICATE_BAD_REQUEST_PARAMS;

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

    @Override
    public GiftCertificate getByID(long ID) {
        GiftCertificate certificate = certificateRepository.getByID(ID);
        certificate.setAssociatedTags(certificateRepository.fetchAssociatedTags(ID));
        return certificate;
    }

    @Override
    public GiftCertificate addEntity(GiftCertificate certificateDto) {
        GiftCertificate baseCert = certificateRepository.create(certificateDto);
        List<Tag> savedTags = saveAssociatedTags(certificateDto.getAssociatedTags());
        certificateRepository.linkAssociatedTags(baseCert.getId(),savedTags);
        return getByID(baseCert.getId());
    }

    @Override
    @Transactional
    public void deleteByID(long ID){
        boolean result = certificateRepository.deleteByID(ID);
        if(!result){
            throw new ServiceException(ErrorCode.CERTIFICATE_DELETION_ERROR,"Cannot delete cert with ID = "+ID);
        }
    }

    @Override
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

    @Override
    public List<Tag> saveAssociatedTags(List<Tag> tags) {
        List<Tag> tagsIdentifiable = new ArrayList<>();
        if(tags!=null) {
            for (Tag tag : tags) {
                tagsIdentifiable.add(tagRepository.create(tag));
            }
        }
        return tagsIdentifiable;
    }

    @Override
    public List<GiftCertificate> handleParametrizedGetRequest(Map<String,String> params){
        checkInvalidValuedParams(params);
        List<GiftCertificate> certificates = certificateRepository.handleParametrizedRequest(params);
        for(GiftCertificate certificate:certificates){
            certificate.setAssociatedTags(certificateRepository.fetchAssociatedTags(certificate.getId()));
        }
        return certificates;
    }

    private void checkInvalidValuedParams(Map<String,String> params){
        if((params.containsKey(nameSortOrder) && !isAllowedOrderDirection(params.get(nameSortOrder))) ||
                (params.containsKey(dateSortOrder) && !isAllowedOrderDirection(params.get(dateSortOrder)))){
            throw new ServiceException(CERTIFICATE_BAD_REQUEST_PARAMS,"allowed values for sorting params are ASC and DESC");
        }
    }

    private static boolean isAllowedOrderDirection(String order){
        return order.equalsIgnoreCase(ASCENDING_SORT) || order.equalsIgnoreCase(DESCENDING_SORT);
    }

    private void detachAssociatedTags(long certificateID){
        certificateRepository.detachAssociatedTags(certificateID);
    }

}
