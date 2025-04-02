package com.carlos.microservice1.core.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carlos.microservice1.client.entities.PersonEntity;
import com.carlos.microservice1.client.repositories.IPersonRepository;
import com.carlos.microservice1.client.services.IPersonService;
import com.carlos.microservice1.service.common.CustomBadRequestException;
import com.carlos.microservice1.vo.dto.PersonDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService{

    private final IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Transactional
    public PersonEntity createPerson(PersonDto personDTO) {
        
        List<PersonEntity> existingPerson = personRepository.findByIdentification(personDTO.getIdentification());
        if (!existingPerson.isEmpty()) {
            throw new CustomBadRequestException("Ya existe una persona con la misma identificación", HttpStatus.CONFLICT);
        }

        if (!isValidGender(personDTO.getGender())) {
            throw new CustomBadRequestException("Género inválido. Debe ser 'F', 'M' o 'O'.", HttpStatus.BAD_REQUEST);
        }
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(personDTO.getName());
        personEntity.setGender(personDTO.getGender());
        personEntity.setAge(personDTO.getAge());
        personEntity.setIdentification(personDTO.getIdentification());
        personEntity.setAddress(personDTO.getAddress());
        personEntity.setPhone(personDTO.getPhone());
        personEntity.setStatus(personDTO.getStatus());

        return personRepository.save(personEntity);
    }

    public Optional<PersonEntity> getPersonById(Integer id) {
        return personRepository.findById(id);
    }

    public PersonEntity updatePerson(Integer id, PersonDto personDTO) {
        Optional<PersonEntity> existingPersonOpt = personRepository.findById(id);
        
        if (!isValidGender(personDTO.getGender())) {
            throw new CustomBadRequestException("Género inválido. Debe ser 'F', 'M' o 'O'.", HttpStatus.BAD_REQUEST);
        }

        if (existingPersonOpt.isPresent()) {
            PersonEntity existingPerson = existingPersonOpt.get();
            existingPerson.setName(personDTO.getName());
            existingPerson.setGender(personDTO.getGender());
            existingPerson.setAge(personDTO.getAge());
            existingPerson.setIdentification(personDTO.getIdentification());
            existingPerson.setAddress(personDTO.getAddress());
            existingPerson.setPhone(personDTO.getPhone());
            existingPerson.setStatus(personDTO.getStatus());
            return personRepository.save(existingPerson); 
        } else {
            throw new EntityNotFoundException("La persona con ID " + id + " no se ha encontrado");
        }
    }

    public void deletePerson(Integer id) {
        PersonEntity person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La persona con ID " + id + " no se ha encontrado"));
        person.setStatus(false);
        personRepository.save(person);
    }

    private boolean isValidGender(String gender) {
        return gender != null && (gender.equals("F") || gender.equals("M") || gender.equals("O"));
    }
}