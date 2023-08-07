package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Direccion;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlumnoDTO.class, name = "alumno"),
        @JsonSubTypes.Type(value = ProfesorDTO.class, name = "profesor"),
        @JsonSubTypes.Type(value = EmpleadoDTO.class, name = "empleado"),
})
public abstract class PersonaDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    @Pattern(regexp = "[0-9]+")
    @Size(min = 1, max = 9)
    private String dni;
    private Direccion direccion;
}