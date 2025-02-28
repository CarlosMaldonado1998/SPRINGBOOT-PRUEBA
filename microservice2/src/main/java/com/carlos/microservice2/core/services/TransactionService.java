package com.carlos.microservice2.core.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carlos.microservice2.client.entities.AccountEntity;
import com.carlos.microservice2.client.entities.TransactionEntity;
import com.carlos.microservice2.client.repositories.IAccountRepository;
import com.carlos.microservice2.client.repositories.ITransactionRepository;
import com.carlos.microservice2.client.service.ITransactionService;
import com.carlos.microservice2.service.common.CustomBadRequestException;
import com.carlos.microservice2.vo.dto.FilterTransactionDto;
import com.carlos.microservice2.vo.dto.TransactionDto;
import com.carlos.microservice2.vo.report.ReportResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;

    @Autowired
    public TransactionService(ITransactionRepository transactionRepository, IAccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionEntity createTransaction(TransactionDto transactionDTO) {

        Optional<AccountEntity> existingAccount = accountRepository.findById(transactionDTO.getAccountId());
        if (!existingAccount.isPresent()) {
            throw new CustomBadRequestException("Cuenta no encontrada", HttpStatus.NOT_FOUND);
        }

        if (!isValidTransaction(transactionDTO.getTransactionType())) {
            throw new CustomBadRequestException("Transacción inválida. Debe ser 'Depósito' o 'Retiro'.",
                    HttpStatus.BAD_REQUEST);
        }

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionDate(transactionDTO.getTransactionDate());
        transactionEntity.setTransactionType(transactionDTO.getTransactionType());
        transactionEntity.setAmount(transactionDTO.getAmount());
        transactionEntity.setBalance(transactionDTO.getBalance());
        transactionEntity.setStatus(transactionDTO.getStatus());
        transactionEntity.setAccount(existingAccount.get());

        return transactionRepository.save(transactionEntity);
    }

    public Optional getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    public TransactionEntity updateTransaction(Integer id, TransactionDto transactionDTO) {
        Optional<TransactionEntity> existingTransactionOpt = transactionRepository.findById(id);

        if (!isValidTransaction(transactionDTO.getTransactionType())) {
            throw new CustomBadRequestException("Transacción inválida. Debe ser 'Depósito' o 'Retiro'.",
                    HttpStatus.BAD_REQUEST);
        }

        if (existingTransactionOpt.isPresent()) {
            TransactionEntity existingTransaction = existingTransactionOpt.get();
            existingTransaction.setTransactionDate(transactionDTO.getTransactionDate());
            existingTransaction.setTransactionType(transactionDTO.getTransactionType());
            existingTransaction.setAmount(transactionDTO.getAmount());
            existingTransaction.setBalance(transactionDTO.getBalance());
            existingTransaction.setStatus(transactionDTO.getStatus());
            return transactionRepository.save(existingTransaction);
        } else {
            throw new EntityNotFoundException("El movimiento con ID " + id + " no se ha encontrado");
        }
    }

    public void deleteTransaction(Integer id) {
        TransactionEntity transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El movimiento con ID " + id + " no se ha encontrado"));
        transaction.setStatus(false);
        transactionRepository.save(transaction);
    }

    private boolean isValidTransaction(String transaction) {
        return transaction != null && (transaction.equals("Depósito") || transaction.equals("Retiro"));
    }

    public List<ReportResponse> generateReport(FilterTransactionDto filter, Long customerId) {
        System.out.println(filter.getIdentification());
        System.out.println(filter.getStart());
        System.out.println(filter.getEnd());
        List<Object[]> transactions = transactionRepository.findTransactionsByCustomerAndDateRange(
                filter.getIdentification(), filter.getStart(), filter.getEnd());
        return mapTransactionsToReportResponse(transactions);
    }

    public List<ReportResponse> mapTransactionsToReportResponse(List<Object[]> transactions) {
        List<ReportResponse> reportResponses = new ArrayList<>();
        for (Object[] row : transactions) {
            ReportResponse response = new ReportResponse();
            response.setDate((Date) row[0]);
            response.setClient((String) row[1]);
            response.setAccountNumber((String) row[2]);
            response.setAccountType(row[3] != null ? row[3].toString() : ""); // Asegúrate de manejar valores nulos
            response.setInitialBalance(row[4] != null ? (Double) row[4] : 0.0); // Valor por defecto si es null
            response.setStatus(row[5] != null ? (Boolean) row[5] : Boolean.FALSE); // Valor por defecto si es null
            response.setAmount(row[6] != null ? (Double) row[6] : 0.0); // Valor por defecto si es null
            response.setBalance(row[7] != null ? (Double) row[7] : 0.0); 
        }

        return reportResponses;
    }
}
