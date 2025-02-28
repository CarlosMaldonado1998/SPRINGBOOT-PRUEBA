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
public class CreateTransactionDto {
  
    @NotNull(message = "El tipo de transacción es obligatorio")
    private String transactionType;

    @NotNull(message = "El monto es obligatorio")
    private Double amount;

}
