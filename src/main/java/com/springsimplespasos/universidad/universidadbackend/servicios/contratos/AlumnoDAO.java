package com.springsimplespasos.universidad.universidadbackend.servicios.contratos;


import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;

public interface AlumnoDAO extends PersonaDAO {

    Iterable<Persona> buscarAlumnosPorNombreCarrera(String nombre);

}
