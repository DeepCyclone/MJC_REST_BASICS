package com.epam.esm.service.converter;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateDtoConverter implements ConverterTemplate<GiftCertificate,GiftCertificateDto,GiftCertificateResponseDto> {


    @Override
    public GiftCertificate convertFromRequestDto(GiftCertificateDto dto) {
        return GiftCertificate.builder().
                id(dto.getId()).
                name(dto.getName()).
                description(dto.getDescription()).
                price(dto.getPrice()).
                duration(dto.getDuration()).
                build();
    }

    @Override
    public GiftCertificateResponseDto convertToResponseDto(GiftCertificate object) {
        return GiftCertificateResponseDto.builder().
                id(object.getId()).
                name(object.getName()).
                description(object.getDescription()).
                price(object.getPrice()).
                duration(object.getDuration()).
                createDate(new Date(object.getCreateDate().getTime())).
                lastUpdateDate(new Date(object.getLastUpdateDate().getTime())).
                build();
    }

    @Override
    public List<GiftCertificateResponseDto> convertToResponseDtos(List<GiftCertificate> objects) {
        List<GiftCertificateResponseDto> dtos = new ArrayList<>();
        for(GiftCertificate gc:objects){
            dtos.add(convertToResponseDto(gc));
        }
        return dtos;
    }
}
