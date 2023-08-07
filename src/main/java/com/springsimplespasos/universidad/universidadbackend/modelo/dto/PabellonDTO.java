package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Direccion;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
public class PabellonDTO {
    private Integer id;
    @NotNull
    @NotEmpty
    private String nombre;
    private Double mts2;
    private Set<AulaDTO> aulaDTOS;
    private Direccion direccion;
}
