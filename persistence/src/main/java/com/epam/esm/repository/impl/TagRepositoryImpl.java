package com.epam.esm.repository.impl;

import com.epam.esm.dto.request.TagDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapping.TagMapping;
import com.epam.esm.repository.mapping.TagResponseMapping;
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
public class TagRepositoryImpl implements TagRepository {

    public static final String READ_BY_ID = "SELECT * FROM tag WHERE t_id = ?";
    public static final String READ_ALL = "SELECT * FROM tag";
    public static final String DELETE_BY_ID = "DELETE FROM tag WHERE t_id = ?";
    public static final String FETCH_ASSOCIATED_TAGS = "SELECT t_id,t_name FROM tag WHERE t_id IN (SELECT tmgc_t_id FROM tag_m2m_gift_certificate WHERE tmgc_gc_id = ?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TagResponseDto create(TagDto object) {
        SimpleJdbcInsertOperations simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("tag").usingGeneratedKeyColumns("t_id");
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("t_name",object.getName());
        Number key = simpleJdbcInsert.executeAndReturnKey(paramsMap);
        return getByID(key.longValue());//TODO
    }

    @Override
    public List<TagResponseDto> readAll() {
        return jdbcTemplate.query(READ_ALL,new TagResponseMapping());
    }

    @Override
    public TagResponseDto update(TagDto object,long ID) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TagResponseDto getByID(long ID) {
        return jdbcTemplate.queryForObject(READ_BY_ID, new TagResponseMapping(),ID);
    }

    @Override
    public boolean deleteByID(long ID) {
        return jdbcTemplate.update(DELETE_BY_ID,ID) == 1;
    }

    @Override
    public List<TagResponseDto> fetchAssociatedTags(long certificateID) {
        return jdbcTemplate.query(FETCH_ASSOCIATED_TAGS,new TagResponseMapping(),certificateID);
    }

    @Override
    public TagResponseDto getByName(String name) {
        return null;
    }

}
