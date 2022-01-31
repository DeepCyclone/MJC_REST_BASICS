package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapping.GiftCertificateMapping;
import com.epam.esm.repository.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    public static final String READ_BY_ID = "SELECT * FROM gift_certificate WHERE gc_id = ?";
    public static final String READ_ALL = "SELECT * FROM gift_certificate";

    private final JdbcTemplate template;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate template, TransactionTemplate transactionTemplate) {
        this.template = template;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean create(GiftCertificate object) {
        return false;
    }

    @Override
    public List<GiftCertificate> readAll() {
        return template.query(READ_ALL,new GiftCertificateMapping());
    }

    @Override
    public GiftCertificate readByID(long ID) {
        return template.query(READ_BY_ID, new GiftCertificateMapping(),ID).stream().findAny().orElse(null);
    }

    @Override
    public boolean update(GiftCertificate object) {
        //TODO transaction???HOWTO
        //transactionTemplate.getTransactionManager();
        return false;
    }

    @Override
    public boolean delete(GiftCertificate object) {
        return false;
    }
}
