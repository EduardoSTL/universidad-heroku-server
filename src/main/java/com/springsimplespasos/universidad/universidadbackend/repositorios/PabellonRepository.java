package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PabellonRepository extends CrudRepository<Pabellon, Integer> {

    @Query("SELECT p FROM Pabellon p  WHERE p.direccion.localidad=?1")
    Iterable<Pabellon> findAllPabellonByLocalidad(String localidad);
    @Query("SELECT p FROM Pabellon p WHERE p.nombre=?1")
    Iterable<Pabellon> findAllPabellonByNombre(String nombre);
}
