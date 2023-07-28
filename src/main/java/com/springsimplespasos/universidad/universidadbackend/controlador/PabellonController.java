
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PabellonController extends GenericController<Pabellon, PabellonDAO>{

    public PabellonController(PabellonDAO service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPabellon(@PathVariable Integer id, @RequestBody Pabellon pabellon){
        Map<String, Object> mensaje = new HashMap<>();
        Pabellon pabellonUpdate;
        Optional<Pabellon> oPabellon = service.findById(id);
        if(!oPabellon.isPresent()) {
            //throw new BadRequestException(String.format("Pabellon con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Pabellon con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        pabellonUpdate = oPabellon.get();
        pabellonUpdate.setNombre(pabellon.getNombre());
        pabellonUpdate.setMts2(pabellon.getMts2());
        pabellonUpdate.setDireccion(pabellon.getDireccion());
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(pabellonUpdate));
        return ResponseEntity.ok(mensaje);
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

