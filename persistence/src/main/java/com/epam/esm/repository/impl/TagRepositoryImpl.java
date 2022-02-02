package com.epam.esm.repository.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapping.TagMapping;
import com.epam.esm.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
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
    public static final String CREATE_ENTRY = "INSERT INTO tag(t_name) VALUES (?)";

    private final JdbcOperations jdbcOperations;
    private final SimpleJdbcInsertOperations simpleJdbcInsert;

    @Autowired
    public TagRepositoryImpl(JdbcOperations jdbcOperations, SimpleJdbcInsertOperations simpleJdbcInsert) {
        this.jdbcOperations = jdbcOperations;
        this.simpleJdbcInsert = simpleJdbcInsert;
    }

    @Override
    public Tag create(Tag object) {
        simpleJdbcInsert.withTableName("tag").usingGeneratedKeyColumns("t_id");
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("t_name",object.getName());
        Number key = simpleJdbcInsert.executeAndReturnKey(paramsMap);
        return getByID(key.longValue());//TODO
    }

    @Override
    public List<Tag> readAll() {
        return jdbcOperations.query(READ_ALL,new TagMapping());
    }

    @Override
    public void update(Tag object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tag getByID(long ID) {
        return jdbcOperations.queryForObject(READ_BY_ID, new TagMapping(),ID);
    }

    @Override
    public void deleteByID(long ID) {
        jdbcOperations.update(DELETE_BY_ID,ID);
    }
}
