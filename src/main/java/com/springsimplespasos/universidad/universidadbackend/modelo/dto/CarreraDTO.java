package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {

    private Integer codigo;

    @NotNull
    @NotEmpty(message = "Debe de ingresar un valor")
    @Size(min = 0, max = 80)
    @Column(nullable = false, unique = true, length = 80)
    private String nombre;
    @Positive(message = "El valor no puede ser negativo")
    private Integer cantidad_materias;
    @Positive
    private Integer cantidad_anios;


}
