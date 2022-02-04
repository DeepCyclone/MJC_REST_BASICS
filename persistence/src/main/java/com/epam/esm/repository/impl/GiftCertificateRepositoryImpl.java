package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public static final String UPDATE_QUERY = "UPDATE gift_certificate SET gc_name=?, gc_description=?, gc_price=?, gc_duration=? WHERE gc_id=?";
    public static final String LINK_TAGS = "INSERT INTO tag_m2m_gift_certificate(tmgc_gc_id,tmgc_t_id) VALUES(?,?)";

    private final JdbcTemplate jdbcOperations;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public GiftCertificate create(GiftCertificate object) {
        SimpleJdbcInsertOperations simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcOperations);
        simpleJdbcInsert.withTableName("gift_certificate").usingGeneratedKeyColumns("gc_id");
        Map<String,Object> params = new HashMap<>();
        params.put("gc_name",object.getName());
        params.put("gc_description",object.getDescription());
        params.put("gc_price",object.getPrice());
        params.put("gc_duration",object.getDuration());
        //TODO set createdate and lastupdatedate
        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return getByID(key.longValue());//TODO or create new object;copy fields on it and set ID
    }

    @Override
    public List<GiftCertificate> readAll() {
        return jdbcOperations.query(READ_ALL,new GiftCertificateMapping());
    }

    @Override
    public void update(GiftCertificate object) {
        jdbcOperations.update(UPDATE_QUERY,object.getName(),object.getDescription(),object.getPrice(),object.getDuration(),object.getId());
    }

    @Override
    public GiftCertificate getByID(long ID) {
        return jdbcOperations.queryForObject(READ_BY_ID, new GiftCertificateMapping(), ID);//TODO build exception hierarchy
    }

    @Override
    public void deleteByID(long ID) {
        jdbcOperations.update(DELETE_ENTRY,ID);
    }

    @Override
    public void linkAssociatedTags(long certificateID, List<Tag> tags) {
        SimpleJdbcInsertOperations simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcOperations);
        simpleJdbcInsert.withTableName("tag_m2m_gift_certificate");
        Map<String,Object> params = new HashMap<>();
        params.put("tmgc_gc_id",certificateID);
        for(Tag tag:tags){
            params.put("tmgc_t_id",tag.getId());
            simpleJdbcInsert.execute(params);
        }
    }
}
