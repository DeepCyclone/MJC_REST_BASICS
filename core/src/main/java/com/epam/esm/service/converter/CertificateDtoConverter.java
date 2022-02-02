package com.epam.esm.service.converter;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.stereotype.Service;

@Service
public class CertificateDtoConverter implements ConverterTemplate<GiftCertificate,GiftCertificateDto> {
    @Override
    public GiftCertificate convertFromDto(GiftCertificateDto dto) {
        return null;
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate object) {
        return null;
    }
}
