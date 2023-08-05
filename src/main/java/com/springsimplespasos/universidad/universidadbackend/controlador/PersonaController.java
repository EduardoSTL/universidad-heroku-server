
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Deprecated
public class PersonaController extends GenericController<Persona, PersonaDAO> {

    public PersonaController(PersonaDAO service){
        super(service);
    }

    //ResponseEntity
    @GetMapping("/nombre-apellido")
    public ResponseEntity<?> buscarPersonaPorNombreYApellido(@RequestParam String nombre, @RequestParam String apellido){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona> oPersona = service.buscarPorNombreYApellido(nombre, apellido);
        if(!oPersona.isPresent()) {
            //throw new BadRequestException(String.format("No se encontro Persona con nombre %s y apellido %s", nombre, apellido));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("no se encontro Persona con nombre %s y apellido %s", nombre, apellido));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("datos", oPersona);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/persona-dni")
    public ResponseEntity<?> buscarPorDni(@RequestParam String dni){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona>personaOptional =service.buscarPorDni(dni);
        if (!personaOptional.isPresent()){
            //throw new BadRequestException(String.format("No se encontro persona con DNI " + "%s",dni));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encontro persona con DNI " + "%s", dni));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", personaOptional);
        return ResponseEntity.ok(mensaje);
    }
}