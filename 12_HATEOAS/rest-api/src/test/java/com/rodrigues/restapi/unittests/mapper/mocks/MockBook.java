package com.rodrigues.restapi.unittests.mapper.mocks;

import com.rodrigues.restapi.data.vo.v1.BookVOV1;
import com.rodrigues.restapi.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {
    public Book mockEntity() {
        return mockEntity(0);
    }

    public Book mockEntity(Integer number) {
        Date date = new Date();
        date.setTime(date.getTime() + number.longValue());

        Book book = new Book(
                number.longValue(),
                "Author Test" + number,
                "Title Test" + number,
                (number.doubleValue() + 10),
                date
        );

        return book;
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }

        return books;
    }

    public BookVOV1 mockVO() {
        return mockVO(0);
    }

    public BookVOV1 mockVO(Integer number) {
        Date date = new Date();
        date.setTime(date.getTime() + number.longValue());

        BookVOV1 book = new BookVOV1();
        book.setKey(number.longValue());
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(date);
        book.setPrice((number.doubleValue() + 10));
        book.setTitle("Title Test" + number);

        return book;
    }

    public List<BookVOV1> mockVOList() {
        List<BookVOV1> books = new ArrayList<BookVOV1>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }

        return books;
    }
}
