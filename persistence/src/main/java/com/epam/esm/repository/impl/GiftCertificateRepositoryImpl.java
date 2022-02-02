package com.epam.esm.repository.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    public static final String READ_BY_ID = "SELECT * FROM gift_certificate WHERE gc_id = ?";
    public static final String READ_ALL = "SELECT * FROM gift_certificate";
    public static final String ADD_NEW_ENTRY = "INSERT INTO gift_certificate (gc_name,gc_description,gc_price,gc_duration,gc_create_date,gc_last_update_date) " +
                                               "VALUES (?,?,?,?,?,?)";



    private final JdbcOperations jdbcOperations;
    private final SimpleJdbcInsert simpleJdbcInsert = null;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public long create(GiftCertificateDto object) {
        simpleJdbcInsert.withTableName("gift_certificate");
        Map<String,Object> params = new HashMap<>();
        params.put("gc_name",object.getName());
        params.put("gc_description",object.getDescription());
        params.put("gc_price",object.getPrice());
        params.put("gc_duration",object.getDuration());
        params.put("gc_create_date",object.getCreateDate());
        params.put("gc_last_update_date",object.getLastUpdateDate());
        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return key.longValue();
    }

    @Override
    public long create(GiftCertificate object) {
        return 0;
    }

    @Override
    public List<GiftCertificate> readAll() {
        return jdbcOperations.query(READ_ALL,new GiftCertificateMapping());
    }

    @Override
    public boolean update(GiftCertificate object) {
        return true;
    }

    @Override
    public boolean delete(GiftCertificate object) {
        return false;
    }

    @Override
    public GiftCertificate getByID(long ID) {
        return jdbcOperations.query(READ_BY_ID, new GiftCertificateMapping(),ID).stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate deleteByID(long ID) {
        return null;
    }
}
