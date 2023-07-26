package com.springsimplespasos.universidad.universidadbackend.servicios.contratos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;

public interface ProfesorDAO extends PersonaDAO {

    Iterable<Persona> buscarProfesoresPorCarrera(String carrera);
}
