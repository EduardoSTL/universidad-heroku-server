/*
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carreras")
public class CarreraController extends GenericController<Carrera, CarreraDAO>{

    @Autowired
    public CarreraController(CarreraDAO service) {
        super(service);
        nombreEntidad = "Carrera";
    }

    @GetMapping("/{codigo}")
    public Carrera obtenerPorId(@PathVariable(value = "codigo", required = false) Integer id) {
        Optional<Carrera> oCarrera = service.findById(id);
        if(!oCarrera.isPresent()){
            throw new BadRequestException(String.format("La carrera con id %d no existe", id));
        }
        return oCarrera.get();
    }

    @PostMapping
    public Carrera altaCarrera(@RequestBody Carrera carrera){
        if(carrera.getCantidadAnios() < 0) {
            throw new BadRequestException("El campo cantida de años no puede ser negativo");
        }
        if(carrera.getCantidaMaterias() < 0) {
            throw new BadRequestException("El campo cantida de materias no puede ser negativo");
        }
        return service.save(carrera);
    }

    @PutMapping("/{id}")
    public Carrera actualizarCarrera(@PathVariable Integer id, @RequestBody Carrera carrera){
        Carrera carreraUpdate = null;
        Optional<Carrera> oCarrera = service.findById(id);
        if(!oCarrera.isPresent()){
            throw new BadRequestException(String.format("La carrera con id %d no existe", id));
        }
        carreraUpdate = oCarrera.get();
        carreraUpdate.setCantidadAnios(carrera.getCantidadAnios());
        carreraUpdate.setCantidaMaterias(carrera.getCantidaMaterias());
        return service.save(carreraUpdate);
    }

    @DeleteMapping("/{id}")
    public void eliminarCarrera(@PathVariable Integer id) {
        service.deleteById(id);
    }


    @PostMapping("/busca-carreras")
    public Iterable<Carrera> findCarreraByNombreContains(@RequestParam String carrera){
        return service.findCarrerasByNombreContains(carrera);
    }

    @PostMapping("/busca-carreras/ignorecase")
    public Iterable<Carrera> findCarreraByNombreContainsIgnoreCase(@RequestParam String carrera){
        return service.findCarrerasByNombreContainsIgnoreCase(carrera);
    }

    @PostMapping("/busca-carreras/{anios}")
    public Iterable<Carrera> findCarreraByCantidadAniosAfter(@PathVariable Integer anios){
        return service.findCarrerasByCantidadAniosAfter(anios);
    }

    @GetMapping("profesor-carreras/{nombre}/{apellido}")
    public Iterable<Carrera> buscarCarrerasPorProfesorNombreYApellido(@PathVariable String nombre, @PathVariable String apellido){
        return service.buscarCarrerasPorProfesorNombreYApellido(nombre,apellido);
    }
}
*/
