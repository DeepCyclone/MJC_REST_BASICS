package com.epam.esm.repository.mapping;

import com.epam.esm.repository.field.GiftCertificateField;
import com.epam.esm.repository.field.TagField;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;



public class ComplexCertificateMapping implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> certificates = new LinkedList<>();
        List<Tag> associatedTags = null;
        GiftCertificate certificate = null;
        while(rs.next()){
            long certID = rs.getLong(GiftCertificateField.ID);
            if(certificate == null || certificate.getId() != certID){
                associatedTags = new ArrayList<>();
                certificate = GiftCertificate.builder().
                                              id(certID).
                                              name(rs.getString(GiftCertificateField.NAME)).
                                              description(rs.getString(GiftCertificateField.DESCRIPTION)).
                                              duration(rs.getInt(GiftCertificateField.DURATION)).
                                              price(rs.getBigDecimal(GiftCertificateField.PRICE)).
                                              createDate(rs.getTimestamp(GiftCertificateField.CREATE_DATE,new GregorianCalendar())).
                                              lastUpdateDate(rs.getTimestamp(GiftCertificateField.LAST_UPDATE_DATE,new GregorianCalendar())).
                                              associatedTags(associatedTags).
                                              build();
                certificates.add(certificate);
            }
            associatedTags.add(Tag.builder().
                               id(rs.getLong(TagField.ID)).
                               name(rs.getString(TagField.NAME)).
                               build());
        }
        return certificates;
    }
}
