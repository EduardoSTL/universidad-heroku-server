
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Deprecated
@RestController
@RequestMapping("/carreras")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
public class CarreraController extends GenericController<Carrera, CarreraDAO>{

    @Autowired
    public CarreraController(CarreraDAO service) {
        super(service);
        nombreEntidad = "Carrera";
    }

    //ignore:
    @GetMapping("/{codigo}")
    public ResponseEntity<?> obtenerPorId(@PathVariable(value = "codigo", required = false) Integer id) {
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Carrera> oCarrera = service.findById(id);
        if (!oCarrera.isPresent()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message", String.format("La carrera con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Carrera carrera = oCarrera.get();
        mensaje.put("datos", carrera);
        mensaje.put("success", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }

    /*@PostMapping
    public ResponseEntity<?> altaCarrera(@Valid @RequestBody Carrera carrera, BindingResult result){
        *//*if(carrera.getCantidadAnios() < 0) {
            throw new BadRequestException("El campo cantida de años no puede ser negativo");
        }
        if(carrera.getCantidaMaterias() < 0) {
            throw new BadRequestException("El campo cantida de materias no puede ser negativo");
        }*//*
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()){
            result.getFieldErrors()
                    .forEach (error -> validaciones.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        return ResponseEntity.ok(service.save(carrera));
    }*/

    //ResponseEntity
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCarrera(@PathVariable Integer id, @RequestBody Carrera carrera){
        Map<String, Object> mensaje = new HashMap<>();
        Carrera carreraUpdate = null;
        Optional<Carrera> oCarrera = service.findById(id);
        if(!oCarrera.isPresent()){
            //throw new BadRequestException(String.format("La carrera con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("%s con ID %d no existe", nombreEntidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        if (carrera.getCantidadAnios()<0){
            //throw new BadRequestException("El campo de años no puede ser negativo");
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje","El campo de años no puede ser negativo");
            return ResponseEntity.badRequest().body(mensaje);
        }
        if (carrera.getCantidadMaterias()<0){
            //throw new BadRequestException("El campo de cantidad de materias no puede ser negativo");
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje","El campo de cantidad de materias no puede ser negativo");
            return ResponseEntity.badRequest().body(mensaje);
        }
        carreraUpdate = oCarrera.get();
        carreraUpdate.setCantidadAnios(carrera.getCantidadAnios());
        carreraUpdate.setCantidadMaterias(carrera.getCantidadMaterias());
        mensaje.put("datos", service.save(carreraUpdate));
        mensaje.put("succes", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/{id}")
    public void borrarPorId(@PathVariable Integer id) {
        service.deleteById(id);
    }


    @GetMapping("/busca-carreras")
    public Iterable<Carrera> findCarreraByNombreContains(@RequestParam String carrera){
        return service.findCarrerasByNombreContains(carrera);
    }

    @GetMapping("/busca-carreras/ignorecase")
    public Iterable<Carrera> findCarreraByNombreContainsIgnoreCase(@RequestParam String carrera){
        return service.findCarrerasByNombreContainsIgnoreCase(carrera);
    }

    @GetMapping("/busca-carreras/{anios}")
    public Iterable<Carrera> findCarreraByCantidadAniosAfter(@PathVariable Integer anios){
        return service.findCarrerasByCantidadAniosAfter(anios);
    }

    @GetMapping("profesor-carreras/{nombre}/{apellido}")
    public Iterable<Carrera> buscarCarrerasPorProfesorNombreYApellido(@PathVariable String nombre, @PathVariable String apellido){
        return service.buscarCarrerasPorProfesorNombreYApellido(nombre,apellido);
    }
}
