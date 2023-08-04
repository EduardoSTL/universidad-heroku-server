package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.*;

@AllArgsConstructor
public class GenericDtoController <E, S extends GenericoDAO<E>> {

    protected final S service;
    protected final String nombre_entidad;

    public List<E> obtenerTodos(){
        return (List<E>) service.findAll();
    }

    public Optional<E> obtenerPorId(Integer id){
        return (Optional<E>) service.findById(id);
    }

    public E altaEntidad(E entidad){
        return service.save(entidad);
    }

    protected Map<String, Object> obtenerValidaciones(BindingResult result){
        Map<String, Object> validaciones = new HashMap<>();
        result.getFieldErrors().forEach(error -> validaciones.put(error.getField(), error.getDefaultMessage()));
        return validaciones;
    }
}
