package com.rodrigues.restapi.unittests.mapper;

import com.rodrigues.restapi.data.vo.v1.BookVOV1;

import com.rodrigues.restapi.mapper.DozerMapper;
import com.rodrigues.restapi.model.Book;
import com.rodrigues.restapi.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DozerConverterBookTest {
    MockBook inputObject ;

    @BeforeEach
    void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() {
        Book input = inputObject.mockEntity();
        BookVOV1 output = DozerMapper.parseObject(input, BookVOV1.class);

        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Title Test0", output.getTitle());
        assertEquals("Author Test0", output.getAuthor());
        assertEquals(10.0, output.getPrice());
        assertEquals(input.getLaunchDate(), output.getLaunchDate());


    }

    @Test
    void parseVoToEntityTest() {
        BookVOV1 input = inputObject.mockVO(1);
        Book output = DozerMapper.parseObject(input, Book.class);

        assertEquals(Long.valueOf(1L), output.getId());
        assertEquals("Title Test1", output.getTitle());
        assertEquals("Author Test1", output.getAuthor());
        assertEquals(11.0, output.getPrice());
        assertEquals(input.getLaunchDate(), output.getLaunchDate());
    }


    @Test
    void parseEntityListTOVoList() {
        List<Book> input = inputObject.mockEntityList();

        List<BookVOV1> output = DozerMapper.parseListObjects(input, BookVOV1.class);

        // verifica se o primeiro elemento da lista é igual ao primeiro elemento da lista de entrada
        BookVOV1 outputZero = output.get(0);
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Title Test0", outputZero.getTitle());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertEquals(10.0, outputZero.getPrice());
        assertEquals(input.get(0).getLaunchDate(), outputZero.getLaunchDate());

        // verifica se o sétimo elemento da lista é igual ao sétimo elemento da lista de entrada
        BookVOV1 outputSeven = output.get(7);
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("Title Test7", outputSeven.getTitle());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertEquals(17.0, outputSeven.getPrice());
        assertEquals(input.get(7).getLaunchDate(), outputSeven.getLaunchDate());

        // verifa se o decimo terceiro elemnto da lista é igual ao decimo terceiro elemneto da lista de entrada
        BookVOV1 outputTwelve = output.get(12);
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("Title Test12", outputTwelve.getTitle());
        assertEquals("Author Test12", outputTwelve.getAuthor());
        assertEquals(22.0, outputTwelve.getPrice());
        assertEquals(input.get(12).getLaunchDate(), outputTwelve.getLaunchDate());

    }

    @Test
    void parseVoListToEntityList() {
        List<BookVOV1> input = inputObject.mockVOList();

        List<Book> output = DozerMapper.parseListObjects(input, Book.class);

        // verifica se o primeiro elemento da lista é igual ao primeiro elemento da lista de entrada
        Book outputZero = output.get(0);
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Title Test0", outputZero.getTitle());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertEquals(10.0, outputZero.getPrice());
        assertEquals(input.get(0).getLaunchDate(), outputZero.getLaunchDate());

    }
}
