package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PabellonMapper {
    PabellonDTO mapPabellonToDTO(Pabellon pabellon);
    Pabellon mapPabellonToEntity(PabellonDTO pabellonDTO);

    @Mapping(target = "pabellon", ignore = true)
    @Mapping(target = "medidas", ignore = true)
    @Mapping(target = "pizarron", ignore = true)
    @Mapping(target = "cantidadPupitres", ignore = true)
    AulaDTO mapAula(Aula aula);
}
