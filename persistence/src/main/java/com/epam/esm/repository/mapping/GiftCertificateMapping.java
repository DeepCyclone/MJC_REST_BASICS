package com.epam.esm.repository.mapping;

import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class GiftCertificateMapping implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder().
                id(rs.getLong("gc_id")).
                name(rs.getString("gc_name")).
                description(rs.getString("gc_description")).
                price(rs.getBigDecimal("gc_price")).
                duration(rs.getInt("gc_duration")).
                createDate(rs.getTimestamp("gc_create_date",new GregorianCalendar())).
                lastUpdateDate(rs.getTimestamp("gc_last_update_date",new GregorianCalendar())).
                build();
    }
}
