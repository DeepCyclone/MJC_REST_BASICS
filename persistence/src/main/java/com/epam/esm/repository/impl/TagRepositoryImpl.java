package com.epam.esm.repository.impl;

import com.epam.esm.exception.ErrorCodeHolder;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.mapping.TagMapping;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.repository.query.TagQueryHolder.DELETE_BY_ID;
import static com.epam.esm.repository.query.TagQueryHolder.FETCH_ASSOCIATED_CERTIFICATES;
import static com.epam.esm.repository.query.TagQueryHolder.GET_BY_NAME;
import static com.epam.esm.repository.query.TagQueryHolder.INSERT_INTO_BY_NAME;
import static com.epam.esm.repository.query.TagQueryHolder.INSERT_INTO_WITH_ID;
import static com.epam.esm.repository.query.TagQueryHolder.READ_ALL;
import static com.epam.esm.repository.query.TagQueryHolder.READ_BY_ID;

@Repository
public class TagRepositoryImpl implements TagRepository {


    public static final int MIN_AFFECTED_ROWS = 1;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag object) {
        KeyHolder holder = new GeneratedKeyHolder();
        if(object.getId() > 0) {
            jdbcTemplate.update(con -> {
                PreparedStatement stmt = con.prepareStatement(INSERT_INTO_WITH_ID, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, object.getId());
                stmt.setString(2, object.getName());
                return stmt;
            }, holder);
        }
        else if(object.getId() == 0){
            jdbcTemplate.update(con -> {
                PreparedStatement stmt = con.prepareStatement(INSERT_INTO_BY_NAME, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, object.getName());
                return stmt;
            }, holder);
        }
        if(holder.getKey()!=null) {
            return getByID(holder.getKey().longValue());
        }
        else {
            if(object.getName()!=null){
                return getByName(object.getName());
            }
            else {
                return getByID(object.getId());
            }
        }
    }

    @Override
    public List<Tag> readAll() {
        return jdbcTemplate.query(READ_ALL,new TagMapping());
    }

    @Override
    public boolean update(Tag object,long ID) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tag getByID(long ID) {
        try {
            return jdbcTemplate.queryForObject(READ_BY_ID, new TagMapping(), ID);
        }
        catch (DataAccessException e){
            return null;
//            throw new RepositoryException(ErrorCodeHolder.TAG_NOT_FOUND,"Cannot fetch tag["+ID+"]");
        }
    }

    @Override
    public boolean deleteByID(long ID) {
        return fetchAssociatedCertificates(ID).isEmpty() &&
                jdbcTemplate.update(DELETE_BY_ID,ID) >= MIN_AFFECTED_ROWS;
    }


    @Override
    public Tag getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_NAME, new TagMapping(), name);
        }
        catch (DataAccessException e){
            return null;
//            throw new RepositoryException(ErrorCodeHolder.TAG_NOT_FOUND,"Cannot fetch tag["+name+"]");
        }
    }

    @Override
    public List<GiftCertificate> fetchAssociatedCertificates(long tagID) {
        return jdbcTemplate.query(FETCH_ASSOCIATED_CERTIFICATES,new GiftCertificateMapping(),tagID);
    }
}
