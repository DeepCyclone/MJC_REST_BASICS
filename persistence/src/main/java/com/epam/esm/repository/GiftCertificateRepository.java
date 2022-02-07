package com.epam.esm.repository;

import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface GiftCertificateRepository extends GenericRepository<GiftCertificate>,Identifiable<GiftCertificate> {
    void linkAssociatedTags(long certificateID, List<Tag> tags);
    List<GiftCertificateResponseDto> handleParametrizedRequest(String tagName,
                                                                      String namePart,
                                                                      String descriptionPart,
                                                                      String nameSortOrder,
                                                                      String dateSortOrder);
}
