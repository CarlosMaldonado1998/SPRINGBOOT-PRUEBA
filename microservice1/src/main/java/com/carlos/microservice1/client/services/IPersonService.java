package com.carlos.microservice1.client.services;

import java.util.Optional;

import com.carlos.microservice1.client.entities.PersonEntity;
import com.carlos.microservice1.vo.dto.PersonDto;


public interface IPersonService {

    public PersonEntity createPerson(PersonDto personDTO);

    public Optional<PersonEntity> getPersonById(Integer id);

    public PersonEntity updatePerson(Integer id, PersonDto personDTO);

    public void deletePerson(Integer id);

}