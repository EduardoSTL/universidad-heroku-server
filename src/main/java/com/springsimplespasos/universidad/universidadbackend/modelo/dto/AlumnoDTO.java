package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Direccion;
import lombok.*;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlumnoDTO extends PersonaDTO{

    private CarreraDTO carreraDTO;

    public AlumnoDTO(Integer id, String nombre, String apellido, String dni, Direccion direccion) {
        super(id, nombre, apellido, dni, direccion);
    }
}
