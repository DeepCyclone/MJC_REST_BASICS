package com.epam.esm.service.converter;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.stereotype.Service;

@Service
public class CertificateDtoConverter implements ConverterTemplate<GiftCertificate,GiftCertificateDto> {
    @Override
    public GiftCertificate convertFromDto(GiftCertificateDto dto) {
        return GiftCertificate.builder().
                name(dto.getName()).
                description(dto.getDescription()).
                price(dto.getPrice()).
                duration(dto.getDuration()).
                createDate(dto.getCreateDate()).
                lastUpdateDate(dto.getLastUpdateDate()).
                build();
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate object) {
        return null;
    }
}
