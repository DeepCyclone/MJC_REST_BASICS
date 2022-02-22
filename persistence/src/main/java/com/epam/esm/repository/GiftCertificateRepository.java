package com.epam.esm.repository;

import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;

import java.util.List;
import java.util.Map;

/*
 * A specification how to interact with datasource which contains gift certificates
 * @author Flexus
 * */
public interface GiftCertificateRepository extends GenericRepository<GiftCertificate>,Identifiable<GiftCertificate>,Nameable<GiftCertificate> {
    void linkAssociatedTags(long certificateID, List<Tag> tags);
    boolean detachAssociatedTags(long certificateID);
    List<Tag> fetchAssociatedTags(long certificateID);
    List<GiftCertificate> handleParametrizedRequest(Map<String,String> map);
}
