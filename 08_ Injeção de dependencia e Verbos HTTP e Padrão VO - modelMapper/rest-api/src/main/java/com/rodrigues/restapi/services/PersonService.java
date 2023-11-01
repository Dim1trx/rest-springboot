package com.rodrigues.restapi.services;

import com.rodrigues.restapi.exceptions.ResourceNotFoundException;
import com.rodrigues.restapi.data.vo.v1.PersonVO;
import com.rodrigues.restapi.mapper.Mapper;
import com.rodrigues.restapi.model.Person;
import com.rodrigues.restapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    public PersonVO findById(Long id) {
        logger.info("finding one person");

        Person entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return Mapper.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll() {
        logger.info("finding all persons");

        return Mapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("creating person");

        Person entity = Mapper.parseObject(person, Person.class);

        return Mapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public PersonVO update(PersonVO person) {
        logger.info("updating person");

        Person entity = repository.findById(person.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return Mapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {
        logger.info("deleting person");

        Person entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }
}
