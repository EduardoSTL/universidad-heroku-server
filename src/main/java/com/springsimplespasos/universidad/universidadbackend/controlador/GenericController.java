package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class GenericController <E, S extends GenericoDAO<E>> {

    protected final S service;
    protected String nombreEntidad;

    public GenericController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?>obtenerTodos(){
        Map<String, Object> mensaje = new HashMap<>();
        List<E> listado = (List<E>) service.findAll();
        if(listado.isEmpty()) {
            //throw new BadRequestException(String.format("No se han encontrado %ss", nombreEntidad));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No existen %ss", nombreEntidad));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("datos", listado);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping
    public E altaEntidad(@RequestBody E entidad){
        return service.save(entidad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<E> oEntidad = service.findById(id);
        if(!oEntidad.isPresent()) {
            //throw new BadRequestException(String.format("Entidad con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Entidad con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", oEntidad);
        return ResponseEntity.ok(mensaje);
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

