package com.rodrigues.restapi.services;

import com.rodrigues.restapi.controllers.BookController;
import com.rodrigues.restapi.data.vo.v1.BookVOV1;
import com.rodrigues.restapi.exceptions.RequiredObjectIsNullException;
import com.rodrigues.restapi.exceptions.ResourceNotFoundException;
import com.rodrigues.restapi.mapper.DozerMapper;
import com.rodrigues.restapi.model.Book;
import com.rodrigues.restapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;
    private Logger logger = Logger.getLogger(BookService.class.getName());

    public BookVOV1 findById(Long id) {
       logger.info("finding one book");

       Book entity = repository.findById(id).
               orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

       var vo = DozerMapper.parseObject(entity, BookVOV1.class);

       vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());


       return vo;
    }

    public List<BookVOV1> findAll() {
        logger.info("finding all books");

        List<BookVOV1> vos = DozerMapper.parseListObjects(repository.findAll(), BookVOV1.class);

        vos.forEach(
                b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel())
        );

        return vos;
    }

    public BookVOV1 create(BookVOV1 vo) {
        if (vo == null || vo.getAuthor() == null || vo.getTitle() == null) throw new RequiredObjectIsNullException();

        logger.info("creating book");

        Book entity = DozerMapper.parseObject(vo, Book.class);

        var createdVo = DozerMapper.parseObject(repository.save(entity), BookVOV1.class);

        createdVo.add(linkTo(methodOn(BookController.class).findById(createdVo.getKey())).withSelfRel());

        return createdVo;
    }

    public BookVOV1 update(BookVOV1 vo) {
        if (vo == null || vo.getAuthor() == null || vo.getTitle() == null) throw new RequiredObjectIsNullException();
        logger.info("updating book");

        Book entity = repository.findById(vo.getKey()).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));


        entity.setAuthor(vo.getAuthor());
        entity.setPrice(vo.getPrice());
        entity.setLaunchDate(vo.getLaunchDate());
        entity.setTitle(vo.getTitle());

        var updatedVo = DozerMapper.parseObject(repository.save(entity), BookVOV1.class);

        updatedVo.add(linkTo(methodOn(BookController.class).findById(updatedVo.getKey())).withSelfRel());

        return updatedVo;
    }

    public void delete(Long id) {
        logger.info("deleting book");

        Book entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }
}
