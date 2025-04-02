package com.carlos.microservice2.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.microservice2.client.entities.AccountEntity;
import com.carlos.microservice2.client.entities.TransactionEntity;

import java.util.Date;
import java.util.List;

public interface ITransactionRepository extends JpaRepository<TransactionEntity, Integer> {
        List<TransactionEntity> findByAccount(AccountEntity account);

        List<TransactionEntity> findByAccountCustomerPersonIdentificationAndTransactionDateBetween(
                        String identification, Date startDate, Date endDate);

}
