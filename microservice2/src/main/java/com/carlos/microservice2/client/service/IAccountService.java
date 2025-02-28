package com.carlos.microservice2.client.service;

import java.util.Optional;

import com.carlos.microservice2.client.entities.AccountEntity;
import com.carlos.microservice2.vo.dto.AccountDto;
import com.carlos.microservice2.vo.dto.CreateAccountDto;

public interface IAccountService {

    public AccountEntity createAccount(CreateAccountDto createAccountDto);

    public Optional<AccountEntity> getAccountById(Integer id);

    public AccountEntity updateAccount(Integer id, AccountDto accountDto);

    public void deleteAccount(Integer id);

}
