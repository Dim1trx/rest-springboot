package com.rodrigues.restapi.unittests.mockito.services;

import com.rodrigues.restapi.data.vo.v1.BookVOV1;
import com.rodrigues.restapi.model.Book;
import com.rodrigues.restapi.repositories.BookRepository;
import com.rodrigues.restapi.services.BookService;
import com.rodrigues.restapi.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    MockBook input;


    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;


    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void findById() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals(1L, result.getKey());
        assertEquals("Title Test1", result.getTitle());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals(11.0, result.getPrice());
        assertEquals(entity.getLaunchDate(), result.getLaunchDate());

    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        List<BookVOV1> people = service.findAll();

        assertNotNull(people);

        assertEquals(14, people.size());


        BookVOV1 personOne = people.get(1);

        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals(1L, personOne.getKey());
        assertEquals("Title Test1", personOne.getTitle());
        assertEquals("Author Test1", personOne.getAuthor());
        assertEquals(11.0, personOne.getPrice());
        assertEquals(list.get(1).getLaunchDate(), personOne.getLaunchDate());

    }
}
