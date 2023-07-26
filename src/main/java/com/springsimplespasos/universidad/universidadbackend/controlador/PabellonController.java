/*
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class PabellonController extends GenericController<Pabellon, PabellonDAO>{

    public PabellonController(PabellonDAO service) {
        super(service);
    }

    @PutMapping("/{id}")
    public Pabellon actualizarPabellon(@PathVariable Integer id, @RequestBody Pabellon pabellon){
        Pabellon pabellonUpdate = null;
        Optional<Pabellon> oPabellon = service.findById(id);
        if(!oPabellon.isPresent()) {
            throw new BadRequestException(String.format("Pabellon con id %d no existe", id));
        }
        pabellonUpdate =  oPabellon.get();
        pabellonUpdate.setNombre(pabellon.getNombre());
        pabellonUpdate.setMts2(pabellon.getMts2());
        pabellonUpdate.setDireccion(pabellon.getDireccion());
        return service.save(pabellonUpdate);
    }

    @PostMapping("/pabellones-localidad")
    public Iterable<Pabellon> findAllPabellonByLocalidad(@RequestParam String localidad){
        return service.findAllPabellonByLocalidad(localidad);
    }

    @PostMapping("/pabellones-nombre")
    public Iterable<Pabellon> findAllPabellonByNombre(@RequestParam String nombre){
        return service.findAllPabellonByNombre(nombre);
    }
}
*/
