package com.epam.esm.service.converter;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class CertificateDtoConverter implements ConverterTemplate<GiftCertificate,GiftCertificateDto> {
    @Override
    public GiftCertificate convertFromDto(GiftCertificateDto dto) {
        return GiftCertificate.builder().
                name(dto.getName()).
                description(dto.getDescription()).
                price(dto.getPrice()).
                duration(dto.getDuration()).
                build();
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate object) {
        return null;
    }

    public GiftCertificateResponseDto convertToResponseDto(GiftCertificate object){
        return GiftCertificateResponseDto.builder().
                id(object.getId()).
                name(object.getName()).
                description(object.getDescription()).
                duration(object.getDuration()).
                price(object.getPrice()).
                createDate(new Date(object.getCreateDate().getNanos())).
                lastUpdateDate(new Date(object.getLastUpdateDate().getNanos())).
                build();
    }

    public GiftCertificate convertFromResponseDto(GiftCertificateResponseDto dto) {
        return GiftCertificate.builder().
                id(dto.getId()).
                name(dto.getName()).
                description(dto.getDescription()).
                duration(dto.getDuration()).
                price(dto.getPrice()).
                createDate(new Timestamp(dto.getCreateDate().getTime())).
                lastUpdateDate(new Timestamp(dto.getLastUpdateDate().getTime())).
                        build();
    }

    public GiftCertificateDto fromResponseToRequestDto(GiftCertificateResponseDto dto){
        return GiftCertificateDto.builder().
                name(dto.getName()).
                description(dto.getDescription()).
                duration(dto.getDuration()).
                price(dto.getPrice()).
                build();
    }
}
