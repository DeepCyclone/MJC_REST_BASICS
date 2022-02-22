package com.epam.esm.service;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private static TagRepository tagRepository;

    @InjectMocks
    private static TagServiceImpl service;

    private static final List<Tag> tags = Arrays.asList();
    private static final Tag tag = Tag.builder().build();

    @Test
    void getAll(){
        Mockito.when(tagRepository.readAll()).thenReturn(tags);
        Assertions.assertEquals(tags,tagRepository.readAll());
    }

    @Test
    void getByID(){
        Mockito.when(tagRepository.getByID(Mockito.anyLong())).thenReturn(tag);
        Assertions.assertEquals(tag,tagRepository.getByID(1L));
    }

    @Test
    void addEntity(){

    }

    @Test
    void deleteExistingEntity(){

    }

    @Test
    void deleteNonExistingEntity(){

    }


}
