package com.epam.esm.controller;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.exception.ObjectNotFoundException;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/certificates",produces = {MediaType.APPLICATION_JSON_VALUE})
public class GiftCertificateController {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }


    @GetMapping
    public List<GiftCertificateResponseDto> getAll() {
        return service.getAll();
    }

//    @GetMapping
//    public List<GiftCertificateResponseDto> getAllByRequestParams(@RequestParam Map<String,String> params)
//    {
//        return service.handleParametrizedGetRequest(params);
//    }

    @GetMapping(value = "/{id:\\d+}")
    public GiftCertificateResponseDto getByID(@PathVariable long id) throws RepositoryException {

        GiftCertificateResponseDto giftCertificate = service.getByID(id);
        if(giftCertificate == null){throw new ObjectNotFoundException("Object with ID:"+id+" wasn't found",id);}
        return giftCertificate;
    }

    @GetMapping(value = "/{id:\\d+}/tags")
    public List<TagResponseDto> getAssociatedTags(@PathVariable long id) throws RepositoryException {
        return null;
    }


    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable long id){
        HttpStatus status = service.deleteByID(id)? HttpStatus.NO_CONTENT:HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<GiftCertificateResponseDto> updateCertificate(@PathVariable long id, @RequestBody GiftCertificateDto certificateDtoPatch) throws RepositoryException {
        return new ResponseEntity<GiftCertificateResponseDto>(service.update(certificateDtoPatch,id),HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificateResponseDto createCertificate(@RequestBody GiftCertificateDto certificateDto) throws RepositoryException {
        return service.addEntity(certificateDto);//TODO 201-CREATED,409-CONFLICT
    }

}
