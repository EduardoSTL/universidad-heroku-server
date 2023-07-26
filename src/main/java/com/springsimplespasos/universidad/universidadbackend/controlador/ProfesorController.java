/*
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProfesorController extends PersonaController{
    private final CarreraDAO carreraDAO;

    public ProfesorController(@Qualifier("profesorDAOImpl") PersonaDAO service, CarreraDAO carreraDAO) {
        super(service);
        this.carreraDAO = carreraDAO;
        nombreEntidad="Profesor";
    }
    @GetMapping("/profesores-carreras")
    public Iterable<Persona> buscarProfesoresPorCarrera(@RequestBody String carrera){
        return ((ProfesorDAO)service).buscarProfesoresPorCarrera(carrera);
    }

    @PutMapping("/{idProfesor}/carrera/{idCarrera}")
    public Persona asignarCarreraProfesor(@PathVariable Integer idProfesor, @PathVariable Integer idCarrera){
        Optional<Persona> oProfesor = service.findById(idProfesor);
        if(!oProfesor.isPresent()) {
            throw new BadRequestException(String.format("Profesor con id %d no existe", idProfesor));
        }
        Optional<Carrera> oCarrera = carreraDAO.findById(idCarrera);
        if(!oCarrera.isPresent()){
            throw new BadRequestException(String.format("Carrera con id %d no existe", idCarrera));
        }
        Persona profesor = oProfesor.get();
        Carrera carrera = oCarrera.get();
        Set<Carrera> carreras = new HashSet<>();
        carreras.add(carrera);
        ((Profesor)profesor).setCarreras(carreras);
        return service.save(profesor);
    }

    @PutMapping("/{id}")
    public Persona actualizarProfesor(@PathVariable Integer id, @RequestBody Profesor profesor){
        Profesor profesorUpdate = null;
        Optional<Persona> oProfesor = service.findById(id);
        if(!oProfesor.isPresent()) {
            throw new BadRequestException(String.format("Profesor con id %d no existe", id));
        }
        profesorUpdate = (Profesor) oProfesor.get();
        profesorUpdate.setNombre(profesor.getNombre());
        profesorUpdate.setApellido(profesor.getApellido());
        profesorUpdate.setDireccion(profesor.getDireccion());
        profesorUpdate.setSueldo(profesor.getSueldo());
        return service.save(profesorUpdate);
    }
}
*/
