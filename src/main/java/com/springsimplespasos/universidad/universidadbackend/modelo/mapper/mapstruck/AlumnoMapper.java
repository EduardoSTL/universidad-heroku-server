package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = AlumnoMapperConfig.class, uses = CarreraMapperMS.class)
public interface AlumnoMapper {
    //fixed to interface
    abstract AlumnoDTO mapAlumno(Alumno alumno);
    abstract Alumno mapAlumno(AlumnoDTO alumnoDTO);
}
