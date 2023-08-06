package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AulaMapper {
    AulaDTO mapAula(Aula aula);
    Aula mapAula(AulaDTO aulaDTO);
    @Mapping(target = "mts2", ignore = true)
    @Mapping(target = "aulas", ignore = true)
    PabellonDTO pabellonToPabellonDTO(Pabellon pabellon);
}