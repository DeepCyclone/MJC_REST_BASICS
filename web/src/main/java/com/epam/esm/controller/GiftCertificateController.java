package com.epam.esm.controller;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.exception.ObjectNotFoundException;
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
    public List<GiftCertificateResponseDto> getAll(@RequestParam(required = false) String tagName,
                                                   @RequestParam(required = false) String namePart,
                                                   @RequestParam(required = false) String descriptionPart,
                                                   @RequestParam(required = false,defaultValue = "ASC") String dateSortOrder,
                                                   @RequestParam(required = false,defaultValue = "ASC") String nameSortOrder
                                        ){

        List<GiftCertificateResponseDto> dtos = service.handleParametrizedGetRequest(tagName,
                namePart,
                descriptionPart,
                dateSortOrder,
                nameSortOrder);
        return dtos;
    }

//    @GetMapping
//    public List<GiftCertificateResponseDto> getAllByTagName(@RequestParam String tagName)
//    {
//        return null;//TODO transfer it to tagController
//    }
//
//
//    @GetMapping
//    public List<GiftCertificateResponseDto> getAllByNamePart(@RequestParam String namePart)
//    {
//        return null;
//    }
//
//    @GetMapping
//    public List<GiftCertificateResponseDto> getAllByRequestParams(@RequestParam Map<String,String> params)
//    {
//        return null;
//    }

    @GetMapping(value = "/{id:\\d+}")
    public GiftCertificate getByID(@PathVariable long id){

        GiftCertificate giftCertificate = service.getByID(id);
        if(giftCertificate == null){throw new ObjectNotFoundException("Object with ID:"+id+" wasn't found",id);}
        return giftCertificate;
    }


    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteCertificate(@PathVariable long id){
        service.deleteByID(id);//TODO NOT FOUND OR NO CONTENT
    }

    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<GiftCertificateResponseDto> updateCertificate(@PathVariable long id, @RequestBody GiftCertificateDto certificateDtoPatch){
        HttpStatus status = service.update(certificateDtoPatch,id)?HttpStatus.OK:HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(status);//TODO update in terms of put logic
        //TODO 201-CREATED,409-CONFLICT
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate createCertificate(@RequestBody GiftCertificateDto certificateDto){
        return service.addEntity(certificateDto);//TODO 201-CREATED,409-CONFLICT
    }

}
