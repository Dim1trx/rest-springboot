package com.rodrigues.restapi.services;


import com.rodrigues.restapi.controllers.PersonController;
import com.rodrigues.restapi.exceptions.RequiredObjectIsNullException;
import com.rodrigues.restapi.exceptions.ResourceNotFoundException;
import com.rodrigues.restapi.data.vo.v1.PersonVOV1;
import com.rodrigues.restapi.mapper.DozerMapper;
import com.rodrigues.restapi.model.Person;
import com.rodrigues.restapi.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVOV1 findById(Long id) {
        logger.info("finding one person");

        Person entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var vo = DozerMapper.parseObject(entity, PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVOV1> findAll() {
        logger.info("finding all persons");

        var persons =  DozerMapper.parseListObjects(repository.findAll(), PersonVOV1.class);

        persons.forEach(
                p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel())
        );


        return persons;
    }

    public PersonVOV1 create(PersonVOV1 person) {
        if (person == null ) throw new RequiredObjectIsNullException();
        if(person.getFirstName() == null || person.getLastName() == null)
            throw new RequiredObjectIsNullException("First Name and Last Name are required");

        logger.info("creating person");

        Person entity = DozerMapper.parseObject(person, Person.class);

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV1 update(PersonVOV1 person) {
        if (person == null ) throw new RequiredObjectIsNullException();
        if(person.getFirstName() == null || person.getLastName() == null)
            throw new RequiredObjectIsNullException("First Name and Last Name are required");

        logger.info("updating person");

        Person entity = repository.findById(person.getKey()).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVOV1 disablePerson(Long id) {
        logger.info("disabling one person");

        repository.disablePerson(id);

        var entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        var vo = DozerMapper.parseObject(entity, PersonVOV1.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        repository.save(entity);
        return vo;
    }

    public void delete(Long id) {
        logger.info("deleting person");

        Person entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }

}
