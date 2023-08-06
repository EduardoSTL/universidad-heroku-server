package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.ProfesorDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ProfesorMapperConfig.class)
public interface PersonaMapper {
    ProfesorDTO mapProfesor(Profesor profesor);
    Profesor mapProfesor(ProfesorDTO profesorDTO);
}
