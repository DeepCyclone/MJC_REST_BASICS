package com.epam.esm.repository;

import com.epam.esm.config.IntegrationConfig;
import com.epam.esm.repository.model.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IntegrationConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(profiles = {"dev"})
public class GiftCertificateRepositoryTest {
    private final GiftCertificateRepository repository;

    private final GiftCertificate certificateToAdd = GiftCertificate.builder().name("Cert4").description("Cert4A").price(new BigDecimal("100.50000")).duration(30).build();

    private static List<GiftCertificate> certificates;
    @Autowired
    public GiftCertificateRepositoryTest(GiftCertificateRepository repository) {
        this.repository = repository;
    }


    @BeforeAll
    static void populateTestCertificates(){
        certificates = Arrays.asList(new GiftCertificate(1L,"Cert1","Cert1A",new BigDecimal("100.50000"),30,Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,10,0)),Timestamp.valueOf(LocalDateTime.of(2022, 1,10,10,10,12,0)),null),
                new GiftCertificate(2L,"Cert2","Cert2A",new BigDecimal("100.50000"),30,Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,12,0)),Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,14,0)),null),
                new GiftCertificate(3L,"Cert3","Cert3A",new BigDecimal("100.50000"),30,Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,14,0)),Timestamp.valueOf(LocalDateTime.of(2022,1,10,10,10,16,0)),null));

    }

    @Test
    @Order(1)
    void getAll(){
        List<GiftCertificate> certificatesDB = repository.handleParametrizedRequest(new HashMap<>());
        Assertions.assertEquals(certificates,certificatesDB);
    }

    @Test
    @Order(2)
    void getByID(){
        GiftCertificate actual = repository.getByID(1L);
        GiftCertificate expected = certificates.get(0);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @Order(3)
    void createEntry(){
        GiftCertificate expected = GiftCertificate.builder().
                                                    id(4L).
                                                    name("Cert4").
                                                    description("Cert4A").
                                                    price(new BigDecimal("100.50000")).
                                                    duration(30).
                                                    build();

        GiftCertificate actual = repository.create(certificateToAdd);
        Assertions.assertTrue(expected.getId() == actual.getId() &&
                                      expected.getName().equals(actual.getName()) &&
                                      expected.getDescription().equals(actual.getDescription()) &&
                                      expected.getPrice().equals(actual.getPrice()) &&
                                      expected.getDuration() ==  actual.getDuration());
    }

    @Test
    @Order(4)
    void updateEntry(){
        GiftCertificate certificateToUpdate = repository.getByID(3L);
        certificateToUpdate.setName("ABC");
        repository.update(certificateToUpdate,3);
        GiftCertificate updatedCertificate = repository.getByID(3L);
        Assertions.assertEquals("ABC", updatedCertificate.getName());
    }

    @Test
    @Order(5)
    void getInfoWithDateOrder(){
        Map<String,String> params = new HashMap<>();
        params.put("dateSortOrder","ASC");
        List<GiftCertificate> certs = repository.handleParametrizedRequest(params);
        Assertions.assertEquals(certs.get(0),certificates.get(0));
    }

    @Test
    @Order(6)
    void deleteEntry(){
        Assertions.assertTrue(repository.deleteByID(1L));
        Assertions.assertFalse(repository.deleteByID(5L));
    }
}
