package com.curso.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.curso.controllers.PersonController;
import com.curso.data.vo.v1.PersonVO;
import com.curso.data.vo.v2.PersonVOV2;
import com.curso.exceptions.ResourceNotFoundException;
import com.curso.mapper.DozerMapper;
import com.curso.mapper.custom.PersonMapper;
import com.curso.model.Person;
import com.curso.repositories.PersonRepository;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll(){

        logger.info("Finding all people!");

        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
        persons.stream()
            .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));


        return persons;
    }

    public PersonVO findById(Long key) {

        logger.info("Finding one person!");


        var entity = repository.findById(key)
                        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(key)).withSelfRel());

        return vo;
    }

    public PersonVO create(PersonVO person) {
        logger.info("Updating one person!");

        var entity = DozerMapper.parseObject(person, Person.class);

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Updating one person with V2!");

        var entity = mapper.convertVoToEntity(person);

        var vo = mapper.convertEntityToVo(repository.save(entity));

        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Creating one person!");
        
        var entity = repository.findById(person.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long key) {
        logger.info("Deleting one person!");

        var entity = repository.findById(key)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
