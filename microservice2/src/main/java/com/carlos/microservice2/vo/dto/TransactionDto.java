package com.carlos.microservice2.vo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    
    private Integer transactionId;

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Integer accountId;

    @NotNull(message = "La fecha de la transacción es obligatoria")
    private java.sql.Date transactionDate;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private String transactionType;

    @NotNull(message = "El monto es obligatorio")
    private Double amount;

    @NotNull(message = "El saldo es obligatorio")
    private Double balance;

    
    private Boolean status = true;

}
