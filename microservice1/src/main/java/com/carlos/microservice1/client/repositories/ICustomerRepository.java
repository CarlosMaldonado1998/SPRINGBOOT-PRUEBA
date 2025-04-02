package com.carlos.microservice1.client.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.microservice1.client.entities.CustomerEntity;
import com.carlos.microservice1.client.entities.PersonEntity;

public interface ICustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    Optional<CustomerEntity> findByPerson(PersonEntity person);

    Optional<CustomerEntity> findByPersonIdentification(String identification); // Cambia person.identification por
                                                                          // identification
}
