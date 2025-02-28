package com.carlos.microservice1.client.services;

import java.util.Optional;

import com.carlos.microservice1.client.entities.CustomerEntity;
import com.carlos.microservice1.vo.dto.CreateCustomerDto;
import com.carlos.microservice1.vo.dto.CustomerDto;



public interface ICustomerService {

    public CustomerEntity createCustomer(CreateCustomerDto createCustomerDto);

    public Optional<CustomerEntity> getCustomerById(Integer id);

    public CustomerEntity updateCustomer(Integer id, CustomerDto customerDto);

    public void deleteCustomer(Integer id);

    public Optional<CustomerEntity> getCustomerByIdentification(String identification);

}
