package com.epam.esm.controller;

import com.epam.esm.dto.CreateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.service.impl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


import java.util.List;

@RestController
@RequestMapping(value = "certificates",produces = {MediaType.APPLICATION_JSON_VALUE})
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final CertificateConverter converter;

    @Autowired
    public GiftCertificateController(GiftCertificateService service, CertificateConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public List<GiftCertificateResponseDto> getAll() {

        List<GiftCertificate> certificates = service.getAll();
        List<GiftCertificateResponseDto> dtos = converter.convertToResponseDtos(certificates);
        return dtos;
    }

//    @GetMapping
//    public List<GiftCertificateResponseDto> getAllByRequestParams(@RequestParam Map<String,String> params)
//    {
//        return converter.convertToResponseDtos(service.handleParametrizedGetRequest(params));
//    }

    @GetMapping(value = "/{id:\\d+}")
    public GiftCertificateResponseDto getByID(@PathVariable long id) {

        GiftCertificate giftCertificate = service.getByID(id);//NOT FOUND EXCEPTION
        GiftCertificateResponseDto dto = converter.convertToResponseDto(giftCertificate);
        return dto;
    }

    @GetMapping(value = "/{id:\\d+}/tags")
    public List<TagResponseDto> getAssociatedTags(@PathVariable long id) {
        return null;
    }


    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable long id){
        HttpStatus status = service.deleteByID(id)? HttpStatus.NO_CONTENT:HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<?> updateCertificate(@PathVariable long id, @RequestBody @Validated({PatchDTO.class}) GiftCertificateDto certificateDtoPatch){
        GiftCertificate certificate = converter.convertFromRequestDto(certificateDtoPatch);
        GiftCertificateResponseDto a = converter.convertToResponseDto(service.update(certificate,id));//TODO CONFLICT
        return new ResponseEntity<>(a,HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateResponseDto createCertificate(@RequestBody @Validated({CreateDTO.class}) GiftCertificateDto certificateDto) {
        GiftCertificate certificate = converter.convertFromRequestDto(certificateDto);
        return converter.convertToResponseDto(service.addEntity(certificate));//TODO 201-CREATED,409-CONFLICT
    }

}
