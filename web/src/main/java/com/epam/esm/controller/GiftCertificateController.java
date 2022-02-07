package com.epam.esm.controller;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
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
        System.out.println(dtos);
        return dtos;
    }

    @GetMapping(value = "/{id:\\d+}")
    public GiftCertificate getByID(@PathVariable long id){

        GiftCertificate giftCertificate = service.getByID(id);
        if(giftCertificate == null){throw new ObjectNotFoundException("Object with ID:"+id+" wasn't found",id);}
        return giftCertificate;
    }


    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteCertificate(@PathVariable long id){
        service.deleteByID(id);
    }

    @PatchMapping(value = "/{id:\\d+}")
    public void updateCertificate(@PathVariable long id,@RequestBody GiftCertificateDto certificateDtoPatch){
        service.update(certificateDtoPatch,id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GiftCertificate createCertificate(@RequestBody GiftCertificateDto certificateDto){
//        for(TagDto dto:certificateDtoPatch.getAssociatedTags()) {
//            tags.add(tagRepository.create(converter.convertFromDto(dto)));
//        }
//        linkAssociatedTags(certificateID,tags);
//        GiftCertificate certificate = converter.convertFromDto(certificateDto);
        return service.addEntity(certificateDto);//responseDtoMapping
    }

}
