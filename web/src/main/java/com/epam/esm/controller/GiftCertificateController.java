package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.converter.CertificateDtoConverter;
import com.epam.esm.service.impl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates",produces = {MediaType.APPLICATION_JSON_VALUE})
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final CertificateDtoConverter converter;

    @Autowired
    public GiftCertificateController(GiftCertificateService service, CertificateDtoConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public List<GiftCertificate> getAll(){
        return null;
    }

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public GiftCertificate getByID(@PathVariable long id){
        GiftCertificate giftCertificate = service.getByID(id);
        if(giftCertificate == null){throw new ObjectNotFoundException("Object with ID:"+id+"wasn't found",id);}
        return giftCertificate;
    }


    @DeleteMapping(value = "/{id}")
    public void deleteCertificate(@PathVariable("id") Long id){
        service.deleteByID(id);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCertificate(@RequestBody GiftCertificateDto certificateDto){
        GiftCertificate certificate = converter.convertFromDto(certificateDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCertificate(@RequestBody GiftCertificateDto certificateDto){//dto here->convert to entity->service
        GiftCertificate certificate = converter.convertFromDto(certificateDto);
    }

}
