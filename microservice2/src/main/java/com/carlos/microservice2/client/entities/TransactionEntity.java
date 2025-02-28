package com.carlos.microservice2.client.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "transaction")
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private AccountEntity account;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "status")
    private Boolean status = true;
}
