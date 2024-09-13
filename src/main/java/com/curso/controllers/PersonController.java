package com.curso.controllers;

import com.curso.data.vo.v1.PersonVO;
// import com.curso.data.vo.v2.PersonVOV2;
import com.curso.services.PersonServices;
import com.curso.util.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, 
        MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public List<PersonVO> findlAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, 
        MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping (
            produces = {MediaType.APPLICATION_JSON, 
                MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, 
                MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public PersonVO create(@RequestBody PersonVO person) {
        return service.create(person);
    }

    // @PostMapping (
    //         value = "/v2",
    //         produces = MediaType.APPLICATION_JSON,
    //         consumes = MediaType.APPLICATION_JSON)
    // public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
    //     return service.createV2(person);
    // }

    @PutMapping (
            produces = {MediaType.APPLICATION_JSON, 
                MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, 
                MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public PersonVO update(@RequestBody PersonVO person) {
        return service.update(person);
    }

    @DeleteMapping (value = "/{id}", produces = {MediaType.APPLICATION_JSON, 
        MediaType.APPLICATION_XML, 
        MediaType.APPLICATION_YML})
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }
}
