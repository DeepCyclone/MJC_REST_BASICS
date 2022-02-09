package com.epam.esm.repository;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.dto.response.TagResponseDto;

import java.util.List;
import java.util.Map;

public interface GiftCertificateRepository extends GenericRepository<GiftCertificateDto,GiftCertificateResponseDto>,Identifiable<GiftCertificateResponseDto>,Nameable<GiftCertificateResponseDto> {
    void linkAssociatedTags(long certificateID, List<TagResponseDto> tags);
    boolean detachAssociatedTags(long certificateID);
    List<GiftCertificateResponseDto> handleParametrizedRequest(Map<String,String> map);
}
