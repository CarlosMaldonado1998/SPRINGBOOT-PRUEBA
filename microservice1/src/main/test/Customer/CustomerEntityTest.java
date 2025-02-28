package com.prueba.tecnica.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carlos.microservice1.client.entities.CustomerEntity;
import com.carlos.microservice1.client.entities.PersonEntity;



public class CustomerEntityTest {

    private CustomerEntity customer;
    private PersonEntity person;

    @BeforeEach
    void setUp() {
        person = new PersonEntity();
        person.setAge(26);
        person.setAddress("Quito");
        person.setGender("M");
        person.setIdentification("123456789");
        person.setName("Carlos");
        person.setPhone("0990123456");
    
        customer = new CustomerEntity();
        customer.setCustomerId(1);
        customer.setPerson(person);
        customer.setPassword("123123");
    }

    @Test
    void testCustomerEntityFields() {
        assertEquals(1, customer.getCustomerId());
        assertEquals(person, customer.getPerson());
        assertEquals("123123", customer.getPassword());
    }

    @Test
    void testSettersAndGetters() {
        customer.setCustomerId(2);
        customer.setPerson(person);
        customer.setPassword("123123");
    
        assertEquals(2, customer.getCustomerId());
        assertEquals(person, customer.getPerson());
        assertEquals("123123", customer.getPassword());
    }
}
