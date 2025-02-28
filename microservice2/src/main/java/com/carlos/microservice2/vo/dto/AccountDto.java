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
public class AccountDto {
    private Integer accountId;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer customerId;

    @NotNull(message = "El número de cuenta es obligatorio")
    private String accountNumber;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private String accountType;

    @NotNull(message = "El saldo inicial es obligatorio")
    private Double initialBalance;

    private Boolean status = true;
}
