package com.carlos.microservice1.java.client.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.carlos.microservice1.client.entities.CustomerEntity;
import com.carlos.microservice1.client.entities.PersonEntity;

public class CustomerEntityTest {

    @Test
    void testCustomerEntityCreation() {
        
       PersonEntity person = new PersonEntity();
        person.setPersonId(1);
        person.setIdentification("1727044651");
        person.setName("Daniel Gonzalez");
        person.setAge(28);
        person.setGender("Male");
        person.setAddress("Quito, Ecuador");
        person.setPhone("555-1234");
        person.setStatus(true);

        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(1);
        customer.setPerson(person);
        customer.setPassword("securepassword");
        customer.setStatus(true);

        // Verificar que los valores se asignaron correctamente
        assertEquals(1, customer.getCustomerId());
        assertEquals(person, customer.getPerson());
        assertEquals("securepassword", customer.getPassword());
        assertTrue(customer.getStatus());
    }


    @Test
    void testCustomerEntityFind() {
        PersonEntity person = new PersonEntity();
        person.setPersonId(2);
        person.setIdentification("987654321");
        person.setName("Ana Lopez");
        person.setAge(35);
        person.setGender("Female");
        person.setAddress("Guayaquil, Ecuador");
        person.setPhone("555-5678");
        person.setStatus(true);

        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(2);
        customer.setPerson(person);
        customer.setPassword("mypassword");
        customer.setStatus(true);


        Optional<CustomerEntity> optionalCustomer = Optional.of(customer);

         // Verificar que el cliente se recuperó correctamente
        assertNotNull(optionalCustomer);
        assertTrue(optionalCustomer.isPresent());
        assertEquals(2, optionalCustomer.get().getCustomerId());
        assertEquals("Ana Lopez", optionalCustomer.get().getPerson().getName());
        assertEquals("mypassword", optionalCustomer.get().getPassword());
    }

}
