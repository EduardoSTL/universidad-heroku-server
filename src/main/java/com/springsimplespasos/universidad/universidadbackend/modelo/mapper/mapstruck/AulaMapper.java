package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PabellonMapper.class)
public interface AulaMapper {
    AulaDTO mapAula(Aula aula);
    Aula mapAula(AulaDTO aulaDTO);
    @Mapping(target = "mts2", ignore = true)
    //@Mapping(target = "pabellonDTO", ignore = true)
        @Mapping(target = "aulas", ignore = true)
    PabellonDTO mapPabellonToDTO(Pabellon pabellon);
}