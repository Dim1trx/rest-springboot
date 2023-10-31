package com.rodrigues.restapi.services;

import com.rodrigues.restapi.exceptions.ResourceNotFoundException;
import com.rodrigues.restapi.model.Person;
import com.rodrigues.restapi.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    public Person findById(Long id) {
        logger.info("finding one person");


        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person not found")
        );
    }

    public List<Person> findAll() {
        logger.info("finding all persons");

        return repository.findAll();
    }

    public Person create(Person person) {
        logger.info("creating person");
        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("updating person");

        Person entity = findById(person.getId());

        entity.setName(person.getName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        logger.info("deleting person");

        Person entity = findById(id);

        repository.delete(entity);
    }
}
