package com.carlos.microservice1.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.microservice1.client.entities.PersonEntity;
import java.util.List;

public interface IPersonRepository extends JpaRepository<PersonEntity, Integer> {

    List<PersonEntity> findByIdentification(String identification);

}
