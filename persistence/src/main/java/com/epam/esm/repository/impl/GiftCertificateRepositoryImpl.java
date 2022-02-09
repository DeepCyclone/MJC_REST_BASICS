package com.epam.esm.repository.impl;

import com.epam.esm.dto.request.GiftCertificateDto;
import com.epam.esm.dto.response.GiftCertificateResponseDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.exception.ErrorCodeHolder;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapping.CertificateResponseDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    public static final String READ_ALL = "SELECT gc_id,gc_name,gc_description,gc_price,gc_duration,gc_create_date,gc_last_update_date,t_id,t_name FROM gift_certificate" +
            " JOIN (SELECT t_name,tmgc_gc_id,t_id FROM tag JOIN `tag_m2m_gift_certificate` ON t_id = tmgc_t_id) AS ix ON gc_id = ix.tmgc_gc_id";
    public static final String ID_FILTER = " WHERE gc_id = ?";
    public static final String READ_BY_ID = READ_ALL + ID_FILTER;
    public static final String DELETE_ENTRY = "DELETE FROM gift_certificate WHERE gc_id = ?";
    public static final String DETACH_ASSOCIATED_TAGS = "DELETE * FROM tag_m2m_gift_certificate WHERE tmgc_gc_id = ?";
    public static final String UPDATE_QUERY = "UPDATE gift_certificate SET gc_name=?, gc_description=?, gc_price=?, gc_duration=? WHERE gc_id=?";
    public static final String LINK_TAGS = "INSERT INTO tag_m2m_gift_certificate(tmgc_gc_id,tmgc_t_id) VALUES(?,?)";
    public static final String JOIN_PARAMS = "SELECT gc_id,gc_name,gc_description,gc_price,gc_duration,gc_create_date,gc_last_update_date,t_id,t_name FROM gift_certificate JOIN" +
            " (SELECT t_name,tmgc_gc_id,t_id FROM tag JOIN" +
            " `tag_m2m_gift_certificate` ON t_id = tmgc_t_id";
    public static final String TAG_NAME_FILTER = " WHERE t_name=?";
    public static final String JOIN_ON_CONDITION = ") AS ix ON gc_id = ix.tmgc_gc_id";
    public static final String PART_NAME_FILTER = " WHERE gc_name like ?";
    public static final String PART_DESCRIPTION_FILTER = " WHERE gc_description like ?";
    public static final String COMBINED_SORT = " ORDER BY gc_name ?,gc_last_update_date ?";
    public static final String SEARCH_BY_TAG_NAME = JOIN_PARAMS+TAG_NAME_FILTER+JOIN_ON_CONDITION;
    public static final String SEARCH_BY_GC_NAME_PART = JOIN_PARAMS+JOIN_ON_CONDITION + PART_NAME_FILTER;
    public static final String SEARCH_BY_GC_DESCRIPTION_PART = JOIN_PARAMS+JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER;
    public static final String SEARCH_BY_TAG_AND_NAME = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_NAME_FILTER;
    public static final String SEARCH_BY_TAG_AND_DESCRIPTION = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER;
    public static final String SEARCH_BY_TAG_AND_DESCRIPTION_SORTED = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER + COMBINED_SORT;
    public static final String SEARCH_BY_TAG_AND_NAME_SORTED = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_NAME_FILTER + COMBINED_SORT;
    public static final String SEARCH_BY_NAME_SORTED = JOIN_PARAMS + JOIN_ON_CONDITION + PART_NAME_FILTER + COMBINED_SORT;
    public static final String SEARCH_BY_DESCRIPTION_SORTED = JOIN_PARAMS + JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER + COMBINED_SORT;
    public static final int MIN_AFFECTED_ROWS = 1;


    private final JdbcTemplate jdbcOperations;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public GiftCertificateResponseDto create(GiftCertificateDto object) throws RepositoryException {
        SimpleJdbcInsertOperations simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcOperations);
        simpleJdbcInsert.withTableName("gift_certificate").usingGeneratedKeyColumns("gc_id");
        Map<String,Object> params = new HashMap<>();
        params.put("gc_name",object.getName());
        params.put("gc_description",object.getDescription());
        params.put("gc_price",object.getPrice());
        params.put("gc_duration",object.getDuration());
        Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return getByID(key.longValue());
    }

    @Override
    public List<GiftCertificateResponseDto> readAll() {
        return jdbcOperations.query(READ_ALL,new CertificateResponseDtoMapper());
    }

    @Override
    public GiftCertificateResponseDto update(GiftCertificateDto object,long ID) {
       if(!(jdbcOperations.update(UPDATE_QUERY,
                object.getName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                ID) == MIN_AFFECTED_ROWS)){
           throw new RepositoryException(ErrorCodeHolder.CERTIFICATE_UPDATE_OP_ERROR,"cannot update object with ID = "+ID);
       }
       return getByID(ID);
    }

    @Override
    public GiftCertificateResponseDto getByID(long ID){
        try {
            return jdbcOperations.query(READ_BY_ID, new CertificateResponseDtoMapper(), ID).stream().findFirst().orElse(null);
        }
        catch (DataAccessException e){
            throw new RepositoryException(ErrorCodeHolder.CERTIFICATE_NOT_FOUND,"Cannot fetch object with ID = " + ID);
        }
    }

    @Override
    public boolean deleteByID(long ID) {
        return jdbcOperations.update(DELETE_ENTRY,ID) == MIN_AFFECTED_ROWS;
    }

    @Override
    public void linkAssociatedTags(long certificateID, List<TagResponseDto> tags) {
        SimpleJdbcInsertOperations simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcOperations);
        simpleJdbcInsert.withTableName("tag_m2m_gift_certificate");
        Map<String,Object> params = new HashMap<>();
        params.put("tmgc_gc_id",certificateID);
        for(TagResponseDto tag:tags){
            params.put("tmgc_t_id",tag.getId());
            simpleJdbcInsert.execute(params);
        }
    }

    @Override
    public boolean detachAssociatedTags(long certificateID) {
        return jdbcOperations.update(DETACH_ASSOCIATED_TAGS,certificateID) == MIN_AFFECTED_ROWS;
    }

    @Override
    public List<GiftCertificateResponseDto> handleParametrizedRequest(Map<String, String> map) {
        return null;
    }

//    @Override
//    public List<GiftCertificateResponseDto> handleParametrizedRequest(Map<String,String> params){
//        final String tagName = "tagName";
//        final String namePart = "namePart";
//        final String descriptionPart = "descriptionPart";
//        final String nameSortOrder = "nameSortOrder";
//        final String dateSortOrder = "dateSortOrder";
//        CertificateResponseDtoMapper mapper = new CertificateResponseDtoMapper();
//        List<String> args = new ArrayList<>();
//        Map<String,PreparedStatementSetter> mapping = new HashMap<>();
//        params.put(Collections.unmodifiableSet(),READ_ALL);
//        params.put(Arrays.asList(tagName),SEARCH_BY_TAG_NAME);
//        params.put(Arrays.asList(namePart),SEARCH_BY_GC_NAME_PART);
//        params.put(Arrays.asList(descriptionPart),SEARCH_BY_GC_DESCRIPTION_PART);
//        params.put(Arrays.asList(tagName,namePart),SEARCH_BY_TAG_AND_NAME);
//        params.put(Arrays.asList(tagName,descriptionPart),SEARCH_BY_TAG_AND_DESCRIPTION);
//        params.put(Arrays.asList(tagName,descriptionPart,nameSortOrder,dateSortOrder),SEARCH_BY_TAG_AND_DESCRIPTION_SORTED);
//        params.put(Arrays.asList(tagName,namePart,nameSortOrder,dateSortOrder),SEARCH_BY_TAG_AND_NAME_SORTED);
//        params.put(Arrays.asList(namePart,nameSortOrder,dateSortOrder),SEARCH_BY_NAME_SORTED);
//        params.put(Arrays.asList(descriptionPart,nameSortOrder,dateSortOrder),SEARCH_BY_DESCRIPTION_SORTED);
//        mapping.put(READ_ALL,(ps)->{});
//        mapping.put(SEARCH_BY_TAG_NAME,ps->ps.setObject(1,tagName));
//        mapping.put(SEARCH_BY_GC_NAME_PART,ps->ps.setObject(1,"%"+namePart+"%"));
//        mapping.put(SEARCH_BY_GC_DESCRIPTION_PART,ps->ps.setObject(1,"%"+descriptionPart+"%"));
//        mapping.put(SEARCH_BY_TAG_AND_NAME,ps->{ps.setObject(1,tagName);ps.setObject(2,"%"+namePart+"%");});
//        mapping.put(SEARCH_BY_TAG_AND_DESCRIPTION,ps->{ps.setObject(1,tagName);ps.setObject(2,"%"+descriptionPart+"%");});;
//        mapping.put(SEARCH_BY_TAG_AND_DESCRIPTION_SORTED,ps->{ps.setObject(1,tagName);ps.setObject(2,"%"+descriptionPart+"%");ps.setObject(3,nameSortOrder);ps.setObject(4,dateSortOrder);});
//        mapping.put(SEARCH_BY_TAG_AND_NAME_SORTED,ps->{ps.setObject(1,tagName);ps.setObject(2,"%"+namePart+"%");ps.setObject(3,nameSortOrder);ps.setObject(4,dateSortOrder);});
//        mapping.put(SEARCH_BY_NAME_SORTED,ps->{ps.setObject(1,"%"+namePart+"%");ps.setObject(2,nameSortOrder);ps.setObject(3,dateSortOrder);});
//        mapping.put(SEARCH_BY_DESCRIPTION_SORTED,ps->{ps.setObject(1,"%"+descriptionPart+"%");ps.setObject(2,nameSortOrder);ps.setObject(3,dateSortOrder);});
//        String query = params.get(args);
//        return jdbcOperations.query(query,mapping.get(query), mapper);
//    }

    @Override
    public GiftCertificateResponseDto getByName(String name) {
        return null;
    }
}
