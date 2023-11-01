package com.rodrigues.restapi.services;


import com.rodrigues.restapi.exceptions.ResourceNotFoundException;
import com.rodrigues.restapi.data.vo.v1.PersonVOV1;
import com.rodrigues.restapi.mapper.DozerMapper;
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

/*    @Autowired
    PersonMapper mapper;*/

    private Logger logger = Logger.getLogger(PersonService.class.getName());
    public PersonVOV1 findById(Long id) {
        logger.info("finding one person");

        Person entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return DozerMapper.parseObject(entity, PersonVOV1.class);
    }

    public List<PersonVOV1> findAll() {
        logger.info("finding all persons");

        return DozerMapper.parseListObjects(repository.findAll(), PersonVOV1.class);
    }

    public PersonVOV1 create(PersonVOV1 person) {
        logger.info("creating person");

        Person entity = DozerMapper.parseObject(person, Person.class);

        return DozerMapper.parseObject(repository.save(entity), PersonVOV1.class);
    }

/*    public PersonVOV2 createV2(PersonVOV2 vo) {
        logger.info("creating person... version 2");

        Person convertedPerson = mapper.convertVOToEntity(vo);
        return mapper.convertEntityToVO(repository.save(convertedPerson));
    }*/

    public PersonVOV1 update(PersonVOV1 person) {
        logger.info("updating person");

        Person entity = repository.findById(person.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return DozerMapper.parseObject(repository.save(entity), PersonVOV1.class);
    }

    public void delete(Long id) {
        logger.info("deleting person");

        Person entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }
}
