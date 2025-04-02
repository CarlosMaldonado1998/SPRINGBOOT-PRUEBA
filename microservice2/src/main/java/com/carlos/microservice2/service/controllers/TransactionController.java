package com.carlos.microservice2.service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.carlos.microservice2.client.entities.CustomerEntity;
import com.carlos.microservice2.client.entities.TransactionEntity;
import com.carlos.microservice2.core.services.AccountService;
import com.carlos.microservice2.core.services.ClientService;
import com.carlos.microservice2.core.services.TransactionService;
import com.carlos.microservice2.service.common.CustomBadRequestException;
import com.carlos.microservice2.vo.dto.CreateTransactionDto;
import com.carlos.microservice2.vo.dto.FilterTransactionDto;
import com.carlos.microservice2.vo.dto.TransactionDto;
import com.carlos.microservice2.vo.report.ReportResponse;

import jakarta.persistence.EntityNotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final ClientService clientService;

    public TransactionController(TransactionService transactionService, AccountService accountService,
            ClientService clientService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<TransactionEntity> createTransaction(@Validated @RequestBody TransactionDto transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> getTransactionById(@PathVariable Integer id) {
        Optional<TransactionEntity> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionEntity> updateTransaction(@PathVariable Integer id,
            @RequestBody @Validated TransactionDto transactionDTO) {
        try {
            TransactionEntity updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
            return ResponseEntity.ok(updatedTransaction);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTransaction(@PathVariable Integer id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear/{accountId}")
    public ResponseEntity<TransactionEntity> createTransaction(
            @PathVariable Integer accountId,
            @RequestBody @Validated CreateTransactionDto createTransactionDto) {

        TransactionEntity transaction = accountService.createTransaction(accountId, createTransactionDto);
        return ResponseEntity.ok(transaction);

    }

    /**
     * Genera un reporte de estado de cuenta para un cliente en un rango de fechas.
     * 
     * @param fecha          Rango de fechas en formato 'yyyy-MM-dd,yyyy-MM-dd'.
     * @param identification Identificación del cliente.
     * @return Lista de reportes en formato JSON.
     */

    @GetMapping("/reportes")
    public ResponseEntity<List<ReportResponse>> getReport(
            @RequestParam String fecha,
            @RequestParam String identification) {

        // Dividir el rango de fechas
        String[] fechas = fecha.split(",");
        if (fechas.length != 2) {
            throw new IllegalArgumentException("El parámetro fecha debe ser en formato 'yyyy-MM-dd,yyyy-MM-dd'");
        }

        Date startDate;
        Date endDate;

        try {
            startDate = Date.valueOf(fechas[0]);
            endDate = Date.valueOf(fechas[1]);
        } catch (IllegalArgumentException e) {
            throw new CustomBadRequestException("El formato de las fechas debe ser 'yyyy-MM-dd'",
                    HttpStatus.BAD_REQUEST);
        }
        // Obtener el customerId desde la identificación
        CustomerEntity customerId = clientService.getCustomerIdByIdentification(identification);

        if (customerId == null) {
            throw new CustomBadRequestException("Cliente no encontrado con identificación: " + identification,
                    HttpStatus.NOT_FOUND);
        }

        FilterTransactionDto filter = new FilterTransactionDto();
        filter.setStart(startDate);
        filter.setEnd(endDate);
        filter.setIdentification(identification);

        List<ReportResponse> report = transactionService.generateReport(filter, customerId.getCustomerId().longValue());

        return ResponseEntity.ok(report);
    }
}