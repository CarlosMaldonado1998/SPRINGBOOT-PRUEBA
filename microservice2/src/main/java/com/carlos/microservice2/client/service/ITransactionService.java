package com.carlos.microservice2.client.service;

import java.util.Optional;

import com.carlos.microservice2.client.entities.TransactionEntity;
import com.carlos.microservice2.vo.dto.TransactionDto;

public interface ITransactionService {

    public TransactionEntity createTransaction(TransactionDto transactionDto);

    public Optional<TransactionEntity> getTransactionById(Integer id);

    public TransactionEntity updateTransaction(Integer id, TransactionDto transactionDto);

    public void deleteTransaction(Integer id);

}
