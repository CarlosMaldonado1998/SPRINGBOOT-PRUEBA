package com.carlos.microservice1.client.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carlos.microservice1.client.entities.CustomerEntity;

public interface ICustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.person.identification = :identification")
    Optional<CustomerEntity> findByPersonIdentification(String identification);
}
