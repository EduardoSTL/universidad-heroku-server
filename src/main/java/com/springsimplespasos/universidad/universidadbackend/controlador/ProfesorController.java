
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Deprecated
@RestController
@RequestMapping("/profesores")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
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
    public ResponseEntity<?> asignarCarreraProfesor(@PathVariable Integer idProfesor, @PathVariable Integer idCarrera){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona> oProfesor = service.findById(idProfesor);
        if(!oProfesor.isPresent()) {
            //throw new BadRequestException(String.format("Profesor con id %d no existe", idProfesor));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Profesor con id %d no existe", idProfesor));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Optional<Carrera> oCarrera = carreraDAO.findById(idCarrera);
        if(!oCarrera.isPresent()){
            //throw new BadRequestException(String.format("Carrera con id %d no existe", idCarrera));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Carrera con id %d no existe", idCarrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Persona profesor = oProfesor.get();
        Carrera carrera = oCarrera.get();
        Set<Carrera> carreras = new HashSet<>();
        carreras.add(carrera);
        ((Profesor)profesor).setCarreras(carreras);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos1", service.save(profesor));
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProfesor(@PathVariable Integer id, @RequestBody Profesor profesor){
        Map<String, Object> mensaje = new HashMap<>();
        Profesor profesorUpdate;
        Optional<Persona> oProfesor = service.findById(id);
        if(!oProfesor.isPresent()) {
            //throw new BadRequestException(String.format("Profesor con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Profesor con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        profesorUpdate = (Profesor) oProfesor.get();
        profesorUpdate.setNombre(profesor.getNombre());
        profesorUpdate.setApellido(profesor.getApellido());
        profesorUpdate.setDireccion(profesor.getDireccion());
        profesorUpdate.setSueldo(profesor.getSueldo());
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(profesorUpdate));
        return ResponseEntity.ok(mensaje);
    }
}

