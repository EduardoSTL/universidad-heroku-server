package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class GenericController <E, S extends GenericoDAO<E>> {

    protected final S service;
    protected String nombreEntidad;

    public GenericController(S service) {
        this.service = service;
    }

    @GetMapping
    public List<E> obtenerTodos(){
        List<E> listado = (List<E>) service.findAll();
        if(listado.isEmpty()) {
            throw new BadRequestException(String.format("No se han encontrado %ss", nombreEntidad));
        }
        return listado;
    }

    @PostMapping
    public E altaEntidad(@RequestBody E entidad){
        return service.save(entidad);
    }

    @GetMapping("/{id}")
    public E obtenerPorId(@PathVariable Integer id){
        Optional<E> oEntidad = service.findById(id);
        if(!oEntidad.isPresent()) {
            throw new BadRequestException(String.format("Entidad con id %d no existe", id));
        }
        return oEntidad.get();
    }

    @DeleteMapping("/{id}")
    public void borrarPorId(@PathVariable Integer id){
        Optional<E> oEntidad = service.findById(id);
        if(!oEntidad.isPresent()) {
            throw new BadRequestException(String.format("Entidad con id %d no existe", id));
        }
        service.deleteById(id);
    }
}

