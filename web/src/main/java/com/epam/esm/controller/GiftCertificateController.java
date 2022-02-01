package com.epam.esm.controller;

import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates",produces = {MediaType.APPLICATION_JSON_VALUE})
public class GiftCertificateController {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<GiftCertificate> getAll(){
        return null;
    }

    @GetMapping(value = "/{id}")
    public GiftCertificate getByID(@PathVariable("id") Long id){
        GiftCertificate giftCertificate = service.getByID(id);
        if(giftCertificate == null){throw new ObjectNotFoundException("Object with ID:"+id+"wasn't found",id);}
        return giftCertificate;
    }


    @DeleteMapping(value = "/{id}")
    public void deleteCertificate(@PathVariable("id") Long id){
        service.deleteByID(id);
    }

    @PatchMapping
    public void updateCertificate(){

    }

    @PostMapping
    public void createCertificate(){//dto here->convert to entity->service

    }

}
