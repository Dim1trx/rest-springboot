package com.rodrigues.restapi.services;

import com.rodrigues.restapi.exceptions.ResourceNotFoundException;
import com.rodrigues.restapi.data.vo.v1.PersonVO;
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
   /* public PersonVO findById(Long id) {
        logger.info("finding one person");


        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("PersonVO not found")
        );
    }

    public List<PersonVO> findAll() {
        logger.info("finding all persons");

        return repository.findAll();
    }

    public PersonVO create(PersonVO person) {
        logger.info("creating person");
        return repository.save(person);
    }

    public PersonVO update(PersonVO person) {
        logger.info("updating person");

        PersonVO entity = findById(person.getId());

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("deleting person");

        PersonVO entity = findById(id);

        repository.delete(entity);
    }*/
}
