package com.carlos.microservice2.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carlos.microservice2.client.entities.AccountEntity;
import com.carlos.microservice2.client.entities.TransactionEntity;

import java.util.Date;
import java.util.List;

public interface ITransactionRepository extends JpaRepository<TransactionEntity, Integer> {
        List<TransactionEntity> findByAccount(AccountEntity account);

        @Query("SELECT t.transactionDate, c.person.name,  a.accountNumber, a.accountType, a.initialBalance, a.status, t.amount, t.balance "
                        +
                        "FROM TransactionEntity t " +
                        "JOIN t.account a " +
                        "JOIN CustomerEntity c ON a.customerId = c.customerId " + 
                        "WHERE c.person.identification = :identification " +
                        "AND t.transactionDate BETWEEN :startDate AND :endDate")
        List<Object[]> findTransactionsByCustomerAndDateRange(
                        @Param("identification") String identification,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate);
}
