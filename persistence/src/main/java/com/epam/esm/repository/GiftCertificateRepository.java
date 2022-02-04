package com.epam.esm.repository;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;

import java.util.List;

public interface GiftCertificateRepository extends GenericRepository<GiftCertificate>,Identifiable<GiftCertificate> {
    public void linkAssociatedTags(long certificateID, List<Tag> tags);
}
