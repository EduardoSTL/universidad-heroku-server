package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AulaRepository extends CrudRepository<Aula, Integer> {
    @Query("SELECT a FROM Aula a WHERE a.pizarron=?1")
    Iterable<Aula>findAulasByPizarron(Pizarron pizarron);

    @Query("SELECT a FROM Aula a JOIN FETCH a.pabellon p WHERE p.nombre = ?1")
    Iterable<Aula>findAulasByPabellonNombre(String nombre);

    @Query("SELECT a FROM Aula a WHERE a.nroAula=?1")
    Optional<Aula> findAulaByNroAula(Integer nroAula);
}
