package com.epam.esm.controller;

import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.dto.CreateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/certificates",produces = {MediaType.APPLICATION_JSON_VALUE})
public class GiftCertificateController {

    private final GiftCertificateServiceImpl service;
    private final CertificateConverter converter;

    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl service, CertificateConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public List<GiftCertificateResponseDto> getAllByRequestParams(@RequestParam Map<String,String> params) {
        return converter.convertToResponseDtos(service.handleParametrizedGetRequest(params));
    }

    @GetMapping(value = "/{id:\\d+}")
    public GiftCertificateResponseDto getByID(@PathVariable long id) {

        GiftCertificate giftCertificate = service.getByID(id);
        return converter.convertToResponseDto(giftCertificate);
    }

    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id){
        service.deleteByID(id);
    }

    @PutMapping
    public ResponseEntity<?> updateCertificate(@RequestBody @Validated(PatchDTO.class) GiftCertificateDto certificateDtoPatch){
        GiftCertificate certificate = converter.convertFromRequestDto(certificateDtoPatch);
        GiftCertificateResponseDto a = converter.convertToResponseDto(service.update(certificate));
        return new ResponseEntity<>(a,HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateResponseDto createCertificate(@RequestBody @Validated(CreateDTO.class) GiftCertificateDto certificateDto) {
        GiftCertificate certificate = converter.convertFromRequestDto(certificateDto);
        return converter.convertToResponseDto(service.addEntity(certificate));
    }

}
