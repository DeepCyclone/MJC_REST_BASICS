package com.epam.esm.repository;

import com.epam.esm.config.IntegrationConfig;
import com.epam.esm.repository.model.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IntegrationConfig.class})
@ActiveProfiles(profiles = {"dev"})
public class GiftCertificateRepositoryTest {
    private final GiftCertificateRepository repository;

    private final List<GiftCertificate> certificates = Arrays.asList(new GiftCertificate(1L,"Cert1","Cert1A",new BigDecimal("100.5"),30,Timestamp.valueOf(LocalDateTime.of(2022,01,10,10,10,10,10)),Timestamp.valueOf(LocalDateTime.of(2022, 1,10,10,10,10,12)),null),
                                                                     new GiftCertificate(2L,"Cert2","Cert2A",new BigDecimal("100.5"),30,Timestamp.valueOf(LocalDateTime.of(2022,01,10,10,10,10,12)),Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,10,14)),null),
                                                                     new GiftCertificate(3L,"Cert3","Cert3A",new BigDecimal("100.5"),30,Timestamp.valueOf(LocalDateTime.of(2022,01,10,10,10,10,14)),Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,10,16)),null));

    @Autowired
    public GiftCertificateRepositoryTest(GiftCertificateRepository repository) {
        this.repository = repository;
    }

//    @Test
//    void getAll(){
//        List<GiftCertificate> certificatesDB = repository.readAll();
//        Assertions.assertEquals(certificates,certificatesDB);
//    }

//    @Test
//    void getByID(){
//        GiftCertificate certificate = repository.getByID(1L);
//        Assertions.assertEquals(certificate,certificates.get(0));
//    }
}
