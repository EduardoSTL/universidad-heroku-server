
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.EmpleadoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public class EmpleadoController extends PersonaController{
    public EmpleadoController(@Qualifier("empleadoDAOImpl") PersonaDAO service) {
        super(service);
        nombreEntidad = "Empleado";
    }

    @GetMapping("/tipo-empleado")
    public Iterable<Persona> buscarEmpleadosPorTipoEmpleado(@RequestBody TipoEmpleado tipoEmpleado){
        return ((EmpleadoDAO)service).buscarEmpleadosPorTipoEmpleado(tipoEmpleado);
    }

    @PutMapping("/{id}")
    public Persona actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado){
        Empleado empleadoUpdate;
        Optional<Persona> oEmpleado = service.findById(id);
        if(!oEmpleado.isPresent()) {
            throw new BadRequestException(String.format("Empleado con id %d no existe", id));
        }
        empleadoUpdate = (Empleado) oEmpleado.get();
        empleadoUpdate.setNombre(empleado.getNombre());
        empleadoUpdate.setApellido(empleado.getApellido());
        empleadoUpdate.setDireccion(empleado.getDireccion());
        empleadoUpdate.setTipoEmpleado(empleado.getTipoEmpleado());
        empleadoUpdate.setSueldo(empleado.getSueldo());
        return service.save(empleadoUpdate);
    }
}

