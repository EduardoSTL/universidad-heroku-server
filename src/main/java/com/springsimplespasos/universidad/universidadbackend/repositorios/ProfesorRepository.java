package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("repositorioProfesores")
public interface ProfesorRepository extends PersonaRepository{

    @Query("SELECT p FROM Profesor p JOIN p.carreras c WHERE c.nombre =?1")
    Iterable<Persona> buscarProfesoresPorCarrera(String carrera);
}
