package com.carlos.microservice1.client.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "initial_balance")
    private Double initialBalance;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "person_id") // Relación con CustomerEntity
    private CustomerEntity customer;
}