package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.EmpleadoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = EmpleadoMapperConfig.class)
public interface EmpleadoMapper {
    EmpleadoDTO mapEmpleado(Empleado empleado);

    Empleado mapEmpleadoDTO(EmpleadoDTO empleadoDTO);
}
