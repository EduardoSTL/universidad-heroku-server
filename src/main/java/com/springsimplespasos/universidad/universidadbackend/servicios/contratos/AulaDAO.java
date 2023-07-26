package com.springsimplespasos.universidad.universidadbackend.servicios.contratos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;

import java.util.Optional;

public interface AulaDAO extends GenericoDAO<Aula>{

    Iterable<Aula>findAulasByPizarron(Pizarron pizarron);

    Iterable<Aula>findAulasByPabellonNombre(String nombre);

    Optional<Aula> findAulaByNroAula(Integer nroAula);
}
