package com.epam.esm.service;

import com.epam.esm.exception.RepositoryException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.GiftCertificate;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {

    @Mock
    private static GiftCertificateRepository giftCertificateRepository;
    @Mock
    private static TagRepository tagRepository;

    private static GiftCertificateServiceImpl service = new GiftCertificateServiceImpl(giftCertificateRepository,tagRepository);

    private static final GiftCertificate certificate = GiftCertificate.builder().id(1).build();
    private static final GiftCertificate newCert = GiftCertificate.builder().build();

    private static final List<GiftCertificate> certificates = Arrays.asList(new GiftCertificate());
    private static final List<GiftCertificate> parametrizedCertificates = Arrays.asList(GiftCertificate.builder().name("Aaaa").build());

    private static final List<Tag> tags = Collections.emptyList();
    private static final Tag emptyTag = Tag.builder().build();
    private static final Tag IDTag = Tag.builder().id(1L).build();


    @BeforeEach
    void init(){
        service = new GiftCertificateServiceImpl(giftCertificateRepository,tagRepository);
    }

    @Test
    void getByID(){
        Mockito.when(giftCertificateRepository.getByID(1L)).thenReturn(certificate);
        Assertions.assertEquals(service.getByID(1L),certificate);

    }

    @Test
    void getAllWithoutParams(){
        Mockito.when(giftCertificateRepository.handleParametrizedRequest(new HashMap<>())).thenReturn(certificates);
        Assertions.assertEquals(certificates,service.handleParametrizedGetRequest(new HashMap<>()));
    }

    @Test
    void getWithNamePart(){
        Map<String,String> params = new HashMap<>();
        params.put("namePart","A");
        Mockito.when(giftCertificateRepository.handleParametrizedRequest(params)).thenReturn(parametrizedCertificates);
        Assertions.assertEquals(parametrizedCertificates,service.handleParametrizedGetRequest(params));
    }

    @Test
    void deleteNonExistingEntry(){
        Mockito.when(giftCertificateRepository.deleteByID(25L)).thenReturn(false);
        Assertions.assertThrows(ServiceException.class,()->service.deleteByID(25L));
    }

    @Test
    void deleteEntry(){
        Mockito.when(giftCertificateRepository.deleteByID(1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(()->service.deleteByID(1L));
    }

    @Test
    void addEntity(){
        Mockito.when(giftCertificateRepository.create(newCert)).thenReturn(certificate);
        Mockito.when(giftCertificateRepository.getByID(Mockito.eq(1L))).thenReturn(certificate);
        GiftCertificate entity = service.addEntity(newCert);
        Assertions.assertEquals(certificate,entity);
    }

    @Test
    void updateEntity(){
        Mockito.when(giftCertificateRepository.update(certificate,1L)).thenReturn(true);
        Mockito.when(giftCertificateRepository.getByID(Mockito.eq(1L))).thenReturn(certificate);
        Assertions.assertEquals(certificate,service.update(certificate));
    }

    @Test
    void updateNonExistingEntity(){
        Mockito.when(giftCertificateRepository.getByID(Mockito.eq(1L))).thenReturn(null);
        Assertions.assertThrows(ServiceException.class,() -> service.update(certificate));
    }
}
