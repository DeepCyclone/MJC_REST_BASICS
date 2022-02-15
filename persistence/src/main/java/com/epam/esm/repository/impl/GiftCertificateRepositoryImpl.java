package com.epam.esm.repository.impl;

import com.epam.esm.exception.ErrorCodeHolder;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.field.GiftCertificateField;
import com.epam.esm.repository.mapping.ComplexCertificateMapping;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import static com.epam.esm.repository.query.CertificateQueryHolder.*;

import com.epam.esm.repository.query.CertificateQueryHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {


    public static final int MIN_AFFECTED_ROWS = 1;
    public static final String tagName = "tagName";
    public static final String namePart = "namePart";
    public static final String descriptionPart = "descriptionPart";
    public static final String nameSortOrder = "nameSortOrder";
    public static final String dateSortOrder = "dateSortOrder";

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
        catch (Exception e){
            throw new RepositoryException(ErrorCodeHolder.CERTIFICATE_NOT_FOUND,"Cannot fetch certificate["+ID+"]");
        }
    }

    @Override
    public boolean deleteByID(long ID) {
        return jdbcOperations.update(DELETE_ENTRY,ID) == MIN_AFFECTED_ROWS;
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

    @Override
    public boolean detachAssociatedTags(long certificateID) {
        return false;
    }

//    @Override
//    public boolean detachAssociatedTags(long certificateID) {
//        return jdbcOperations.update(DETACH_ASSOCIATED_TAGS,certificateID) == MIN_AFFECTED_ROWS;
//    }

    @Override
    public List<GiftCertificate> handleParametrizedRequest(Map<String,String> params){

        String query = mapParamsToQuery(new ArrayList<>(params.keySet()));
        PreparedStatementSetter preparedStatementSetter = injectArgsToQuery(query,params);
        return jdbcOperations.query(query,preparedStatementSetter, new ComplexCertificateMapping());
    }

    @Override
    public GiftCertificate getByName(String name) {
        return null;
    }

    private String mapParamsToQuery(List<String> params){
        Map<List<String>,String> mapping = new HashMap<>();
        mapping.put(Arrays.asList(),REAL_ALL_CERTS_WITH_TAGS);
        mapping.put(Arrays.asList(tagName),SEARCH_BY_TAG_NAME);
        mapping.put(Arrays.asList(namePart),SEARCH_BY_GC_NAME_PART);
        mapping.put(Arrays.asList(descriptionPart),SEARCH_BY_GC_DESCRIPTION_PART);
        mapping.put(Arrays.asList(tagName,namePart),SEARCH_BY_TAG_AND_NAME);
        mapping.put(Arrays.asList(tagName,descriptionPart),SEARCH_BY_TAG_AND_DESCRIPTION);
        mapping.put(Arrays.asList(tagName,descriptionPart,nameSortOrder,dateSortOrder),SEARCH_BY_TAG_AND_DESCRIPTION_SORTED);
        mapping.put(Arrays.asList(tagName,namePart,nameSortOrder,dateSortOrder),SEARCH_BY_TAG_AND_NAME_SORTED);
        mapping.put(Arrays.asList(namePart,nameSortOrder,dateSortOrder),SEARCH_BY_NAME_SORTED);
        mapping.put(Arrays.asList(descriptionPart,nameSortOrder,dateSortOrder),SEARCH_BY_DESCRIPTION_SORTED);
        return mapping.get(params);
    }

    private PreparedStatementSetter injectArgsToQuery(String query,Map<String,String> params){
        Map<String, PreparedStatementSetter> preparedStatementMapping = new HashMap<>();
        preparedStatementMapping.put(REAL_ALL_CERTS_WITH_TAGS,(ps)->{});
        preparedStatementMapping.put(SEARCH_BY_TAG_NAME,ps->ps.setObject(1,params.get(tagName)));
        preparedStatementMapping.put(SEARCH_BY_GC_NAME_PART,ps->ps.setObject(1,"%"+params.get(namePart)+"%"));
        preparedStatementMapping.put(SEARCH_BY_GC_DESCRIPTION_PART,ps->ps.setObject(1,"%"+params.get(descriptionPart)+"%"));
        preparedStatementMapping.put(SEARCH_BY_TAG_AND_NAME,ps->{ps.setObject(1,params.get(tagName));ps.setObject(2,"%"+params.get(namePart)+"%");});
        preparedStatementMapping.put(SEARCH_BY_TAG_AND_DESCRIPTION,ps->{ps.setObject(1,params.get(tagName));ps.setObject(2,"%"+params.get(descriptionPart)+"%");});;
        preparedStatementMapping.put(SEARCH_BY_TAG_AND_DESCRIPTION_SORTED,ps->{ps.setObject(1,params.get(tagName));ps.setObject(2,"%"+params.get(descriptionPart)+"%");ps.setObject(3,params.get(nameSortOrder));ps.setObject(4,params.get(dateSortOrder));});
        preparedStatementMapping.put(SEARCH_BY_TAG_AND_NAME_SORTED,ps->{ps.setObject(1,tagName);ps.setObject(2,"%"+namePart+"%");ps.setObject(3,nameSortOrder);ps.setObject(4,dateSortOrder);});
        preparedStatementMapping.put(SEARCH_BY_NAME_SORTED,ps->{ps.setObject(1,"%"+namePart+"%");ps.setObject(2,nameSortOrder);ps.setObject(3,dateSortOrder);});
        preparedStatementMapping.put(SEARCH_BY_DESCRIPTION_SORTED,ps->{ps.setObject(1,"%"+params.get(descriptionPart)+"%");ps.setObject(2,params.get(nameSortOrder));ps.setObject(3,params.get(dateSortOrder));});
        return preparedStatementMapping.get(query);
    }
}
