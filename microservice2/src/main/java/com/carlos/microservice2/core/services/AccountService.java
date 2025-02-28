package com.carlos.microservice2.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.carlos.microservice2.client.entities.AccountEntity;
import com.carlos.microservice2.client.entities.TransactionEntity;
import com.carlos.microservice2.client.repositories.IAccountRepository;
import com.carlos.microservice2.client.repositories.ITransactionRepository;
import com.carlos.microservice2.client.service.IAccountService;
import com.carlos.microservice2.service.common.CustomBadRequestException;
import com.carlos.microservice2.vo.dto.AccountDto;
import com.carlos.microservice2.vo.dto.CreateAccountDto;
import com.carlos.microservice2.vo.dto.CreateTransactionDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ITransactionRepository transactionRepository;
    private final ClientService clientService;

    @Autowired
    public AccountService(IAccountRepository accountRepository,
            ITransactionRepository transactionRepository,
            ClientService clientService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AccountEntity createAccount(CreateAccountDto createAccountDto) {

        Optional<AccountEntity> existingAccountNumber = accountRepository
                .findByAccountNumber(createAccountDto.getAccountNumber());
        if (!existingAccountNumber.isEmpty()) {
            throw new CustomBadRequestException("Ya existe ese número de cuenta", HttpStatus.CONFLICT);
        }
        if (!isValidAccountType(createAccountDto.getAccountType())) {
            throw new CustomBadRequestException("Tipo de cuenta inválido. Debe ser 'Ahorros' o 'Corriente'.",
                    HttpStatus.BAD_REQUEST);
        }
        Long customerId = clientService.getCustomerIdByIdentification(createAccountDto.getIdentification());
        if (customerId == null) {
            throw new CustomBadRequestException("Client no encontrado", HttpStatus.NOT_FOUND);
        }
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountNumber(createAccountDto.getAccountNumber());
        accountEntity.setAccountType(createAccountDto.getAccountType());
        accountEntity.setInitialBalance(createAccountDto.getInitialBalance());
        accountEntity.setBalance(createAccountDto.getInitialBalance());
        accountEntity.setStatus(createAccountDto.getStatus());
        accountEntity.setCustomerId(customerId);

        return accountRepository.save(accountEntity);

    }

    public Optional<AccountEntity> getAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    public AccountEntity updateAccount(Integer id, AccountDto accountDTO) {
        Optional<AccountEntity> existingAccountOpt = accountRepository
                .findByAccountNumber(accountDTO.getAccountNumber());

        if (!isValidAccountType(accountDTO.getAccountType())) {
            throw new CustomBadRequestException("Tipo de cuenta inválido. Debe ser 'Ahorros' o 'Corriente'.",
                    HttpStatus.BAD_REQUEST);
        }

        if (existingAccountOpt.isPresent()) {
            AccountEntity existingAccount = existingAccountOpt.get();
            existingAccount.setAccountNumber(accountDTO.getAccountNumber());
            existingAccount.setAccountType(accountDTO.getAccountType());
            existingAccount.setInitialBalance(accountDTO.getInitialBalance());
            existingAccount.setStatus(accountDTO.getStatus());
            return accountRepository.save(existingAccount);
        } else {
            throw new EntityNotFoundException("La cuenta con ID " + id + " no se ha encontrado");
        }
    }

    public void deleteAccount(Integer id) {
        AccountEntity account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cuenta con ID " + id + " no se ha encontrado"));
        account.setStatus(false);
        accountRepository.save(account);
    }

    private boolean isValidAccountType(String type) {
        return type != null && (type.equals("Ahorros") || type.equals("Corriente"));
    }

    @Transactional
    public TransactionEntity createTransaction(Integer accountId, CreateTransactionDto createTransactionDto) {
        // Buscar la cuenta usando el accountId
        List<AccountEntity> accounts = accountRepository.findByAccountId(accountId);
        if (accounts.isEmpty()) {
            throw new CustomBadRequestException("No se ha encontrado ninguna cuenta con el ID " + accountId,
                    HttpStatus.NOT_FOUND);
        }

        AccountEntity account = accounts.get(0);

        // Verificar si el saldo es suficiente para un retiro (si el tipo de transacción
        // es "Retiro")
        if ("Retiro".equals(createTransactionDto.getTransactionType())
                && account.getBalance() < createTransactionDto.getAmount()) {
            throw new CustomBadRequestException("Saldo no disponible", HttpStatus.CONFLICT);
        }

        Double amount = Math.abs(createTransactionDto.getAmount());

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
        transaction.setTransactionType(createTransactionDto.getTransactionType());
        transaction.setAmount(amount);
        transaction.setStatus(true);

        // Actualizar el saldo de la cuenta dependiendo del tipo de transacción
        Double newBalance;
        if ("Depósito".equals(createTransactionDto.getTransactionType())) {
            newBalance = account.getBalance() + amount;
        } else if ("Retiro".equals(createTransactionDto.getTransactionType())) {
            newBalance = account.getBalance() - amount;
        } else {
            throw new CustomBadRequestException("Tipo de transacción no válido", HttpStatus.BAD_REQUEST);
        }

        transaction.setBalance(newBalance);
        transactionRepository.save(transaction);
        account.setBalance(newBalance);
        accountRepository.save(account);

        return transaction;
    }

}