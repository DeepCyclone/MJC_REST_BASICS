package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    public static final String READ_BY_ID = "SELECT * FROM gift_certificate WHERE gc_id = ?";
    public static final String READ_ALL = "SELECT * FROM gift_certificate";
    public static final String DELETE_ENTRY = "DELETE FROM gift_certificate WHERE gc_id = ?";

    private final JdbcOperations jdbcOperations;
    private final SimpleJdbcInsertOperations simpleJdbcInsert;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcOperations jdbcOperations, SimpleJdbcInsertOperations simpleJdbcInsert) {
        this.jdbcOperations = jdbcOperations;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Override
    public GiftCertificate create(GiftCertificate object) {
        simpleJdbcInsert.withTableName("gift_certificate").usingGeneratedKeyColumns("gc_id");;
        Map<String,Object> params = new HashMap<>();
        params.put("gc_name",object.getName());
        params.put("gc_description",object.getDescription());
        params.put("gc_price",object.getPrice());
        params.put("gc_duration",object.getDuration());
        params.put("gc_create_date",object.getCreateDate());
        params.put("gc_last_update_date",object.getLastUpdateDate());
        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return getByID(key.longValue());//TODO or create new object;copy fields on it and set ID
    }

    @Override
    public List<GiftCertificate> readAll() {
        return jdbcOperations.query(READ_ALL,new GiftCertificateMapping());
    }

    @Override
    public void update(GiftCertificate object) {
    }

    @Override
    public GiftCertificate getByID(long ID) {
        return jdbcOperations.query(READ_BY_ID,new GiftCertificateMapping(),ID).stream().findAny().orElse(null);
//        return jdbcOperations.queryForObject(READ_BY_ID,new GiftCertificateMapping(),ID);
    }

    @Override
    public void deleteByID(long ID) {
        jdbcOperations.update(DELETE_ENTRY,ID);
    }

}
