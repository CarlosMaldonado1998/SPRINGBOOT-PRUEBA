package com.carlos.microservice2.client.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
    private PersonEntity person;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status")
    private Boolean status = true;

}
