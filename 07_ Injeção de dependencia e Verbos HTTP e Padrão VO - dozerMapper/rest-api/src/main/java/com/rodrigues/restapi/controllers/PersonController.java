package com.rodrigues.restapi.controllers;


import com.rodrigues.restapi.data.vo.v1.PersonVOV1;
import com.rodrigues.restapi.data.vo.v2.PersonVOV2;
import com.rodrigues.restapi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(value = "/{id}")
    public PersonVOV1 findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping()
    public ResponseEntity<List<PersonVOV1>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV1 create(@RequestBody PersonVOV1 person) {
        return service.create(person);
    }

    @PostMapping(value = "/v2",consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
        return service.createV2(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV1 update(@RequestBody PersonVOV1 person) {
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
