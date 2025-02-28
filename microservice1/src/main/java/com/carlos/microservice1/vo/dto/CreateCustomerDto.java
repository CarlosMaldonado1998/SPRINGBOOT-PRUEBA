package com.carlos.microservice1.vo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDto {

    @NotBlank(message = "Nombre es requerido")
    private String name;

    @NotBlank(message = "Genero es requerido")
    private String gender;

    @NotNull(message = "Edad es requerido")
    private Integer age;

    @NotBlank(message = "Identificación es requerido")
    private String identification;

    private String address;
    private String phone;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private Boolean status = true;
}
