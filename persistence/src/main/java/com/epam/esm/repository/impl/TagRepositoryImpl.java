package com.epam.esm.repository.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.mapping.TagMapping;
import com.epam.esm.repository.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {

    public static final String READ_BY_ID = "SELECT * FROM tag WHERE t_id = ?";
    public static final String READ_ALL = "SELECT * FROM tag";

    private final JdbcOperations template;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcOperations template, TransactionTemplate transactionTemplate) {
        this.template = template;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public long create(Tag object) {
        return 0;
    }

    @Override
    public List<Tag> readAll() {
        return template.query(READ_ALL,new TagMapping());
    }

    @Override
    public boolean update(Tag object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Tag object) {
        return false;
    }

    @Override
    public Tag getByID(long ID) {
        return template.query(READ_BY_ID, new TagMapping(),ID).stream().findAny().orElse(null);
    }

    @Override
    public Tag deleteByID(long ID) {
        return null;
    }
}
