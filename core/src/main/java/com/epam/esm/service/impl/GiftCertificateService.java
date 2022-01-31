package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.readByID(ID);
    }
    public void deleteByID(long ID){

    }
}
