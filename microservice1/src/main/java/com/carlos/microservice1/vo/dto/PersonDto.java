package com.carlos.microservice1.vo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

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

    private Boolean status = true;
}
