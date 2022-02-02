package com.epam.esm.repository.mapping;

import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapping implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
                certificate.setId(rs.getLong("gc_id"));//TODO builder
                certificate.setName(rs.getString("gc_name"));
                certificate.setDescription(rs.getString("gc_description"));
                certificate.setPrice(rs.getBigDecimal("gc_price"));
                certificate.setDuration(rs.getInt("gc_duration"));
                return certificate;
    }
}
