package com.epam.esm.repository.impl;

import com.epam.esm.exception.ErrorCodeHolder;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.field.GiftCertificateField;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.mapping.TagMapping;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.repository.query.ComplexParamMapProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.query.CertificateQueryHolder.DELETE_ENTRY;
import static com.epam.esm.repository.query.CertificateQueryHolder.DETACH_ASSOCIATED_TAGS;
import static com.epam.esm.repository.query.CertificateQueryHolder.FETCH_ASSOCIATED_TAGS;
import static com.epam.esm.repository.query.CertificateQueryHolder.INSERT_INTO_M2M;
import static com.epam.esm.repository.query.CertificateQueryHolder.READ_ALL;
import static com.epam.esm.repository.query.CertificateQueryHolder.READ_BY_ID;
import static com.epam.esm.repository.query.CertificateQueryHolder.READ_BY_NAME;
import static com.epam.esm.repository.query.CertificateQueryHolder.UPDATE_QUERY;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {


    public static final int MIN_AFFECTED_ROWS = 1;


    private final JdbcTemplate jdbcOperations;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public GiftCertificate create(GiftCertificate object) throws RepositoryException {
        SimpleJdbcInsertOperations simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcOperations);
        simpleJdbcInsert.withTableName("gift_certificate").usingGeneratedKeyColumns("gc_id").usingColumns("gc_name","gc_description","gc_price","gc_duration");
        Map<String,Object> params = new HashMap<>();
        params.put(GiftCertificateField.NAME,object.getName());
        params.put(GiftCertificateField.DESCRIPTION,object.getDescription());
        params.put(GiftCertificateField.PRICE,object.getPrice());
        params.put(GiftCertificateField.DURATION,object.getDuration());
        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return getByID(key.longValue());
    }

    @Override
    public List<GiftCertificate> readAll() {
        return jdbcOperations.query(READ_ALL,new GiftCertificateMapping());
    }

    @Override
    public boolean update(GiftCertificate object,long ID) {
       return jdbcOperations.update(UPDATE_QUERY,
                object.getName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                ID) == MIN_AFFECTED_ROWS;
    }

    @Override
    public GiftCertificate getByID(long ID){
        try {
            return jdbcOperations.queryForObject(READ_BY_ID, new GiftCertificateMapping(), ID);
        }
        catch (DataAccessException e){
            throw new RepositoryException(ErrorCodeHolder.CERTIFICATE_NOT_FOUND,"Cannot fetch certificate["+ID+"]");
        }
    }

    @Override
    public boolean deleteByID(long ID) {
        return jdbcOperations.update(DELETE_ENTRY,ID) >= MIN_AFFECTED_ROWS;
    }

    @Override
    public void linkAssociatedTags(long certificateID, List<Tag> tags) {
        for(Tag tag:tags) {
            jdbcOperations.update(con -> {
                PreparedStatement stmt = con.prepareStatement(INSERT_INTO_M2M, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, tag.getId());
                stmt.setLong(2, certificateID);
                return stmt;
            });
        }
    }

    @Override
    public boolean detachAssociatedTags(long certificateID) {
        return jdbcOperations.update(DETACH_ASSOCIATED_TAGS,certificateID) >= MIN_AFFECTED_ROWS;
    }

    @Override
    public List<Tag> fetchAssociatedTags(long certificateID) {
        return jdbcOperations.query(FETCH_ASSOCIATED_TAGS,new TagMapping(),certificateID);
    }

    @Override
    public List<GiftCertificate> handleParametrizedRequest(Map<String,String> params){
        String query = ComplexParamMapProcessor.buildQuery(params);
        prepareParamsToSearchStatement(params);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.jdbcOperations);
        return template.query(query, params,new GiftCertificateMapping());
    }

    private void prepareParamsToSearchStatement(Map<String,String> params){
        if(params.containsKey(ComplexParamMapProcessor.namePart)){
            params.replace(ComplexParamMapProcessor.namePart,ComplexParamMapProcessor.PERCENT+params.get(ComplexParamMapProcessor.namePart)+ComplexParamMapProcessor.PERCENT);
        }
        if(params.containsKey(ComplexParamMapProcessor.descriptionPart)){
            params.replace(ComplexParamMapProcessor.descriptionPart,ComplexParamMapProcessor.PERCENT+params.get(ComplexParamMapProcessor.descriptionPart)+ComplexParamMapProcessor.PERCENT);
        }
    }

    @Override
    public GiftCertificate getByName(String name) {
        return jdbcOperations.queryForObject(READ_BY_NAME,new GiftCertificateMapping(),name);
    }

}
