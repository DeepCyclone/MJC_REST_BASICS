package com.epam.esm.service.impl;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private final GiftCertificateRepository repository;

    @Autowired
    public GiftCertificateService(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    public List<GiftCertificate> getAll(){
        return repository.readAll();
    }
    public GiftCertificate getByID(long ID){
        return repository.getByID(ID);
    }

    public GiftCertificate addEntity(GiftCertificate certificate){
        return repository.create(certificate);
    }

    @Transactional
    public void deleteByID(long ID){
        repository.deleteByID(ID);
    }

    @Transactional
    public void update(GiftCertificateDto certificateDtoPatch,long id){
        GiftCertificate originalCertificate = getByID(id);
        Optional.ofNullable(certificateDtoPatch.getName()).ifPresent(originalCertificate::setName);
        Optional.ofNullable(certificateDtoPatch.getDescription()).ifPresent(originalCertificate::setDescription);
        Optional.ofNullable(certificateDtoPatch.getPrice()).ifPresent(originalCertificate::setPrice);
        Optional.ofNullable(certificateDtoPatch.getDuration()).ifPresent(originalCertificate::setDuration);
        repository.update(originalCertificate);
    }


}
