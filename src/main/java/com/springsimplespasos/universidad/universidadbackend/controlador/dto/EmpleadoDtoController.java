package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.EmpleadoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.EmpleadoMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.EmpleadoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empleados")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class EmpleadoDtoController extends PersonaDtoController {

    public EmpleadoDtoController(@Qualifier("empleadoDAOImpl") PersonaDAO service, EmpleadoMapper empleadoMapper) {
        super(service, "empleado", empleadoMapper);
    }

    @GetMapping
    public ResponseEntity<?> findAllEmpleados(){
        Map<String, Object> mensaje = new HashMap<>();
        List<PersonaDTO> dtos = super.findAll();
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findEmpleadoById(@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaById(id);
        if (dto == null){
            mensaje.put("success", Boolean.TRUE);
            mensaje.put("message", String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok().body(mensaje);
    }

    @GetMapping
    public ResponseEntity<?> createEmpleado(@Valid @RequestBody PersonaDTO personaDTO, BindingResult result){
        Map<String, Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        PersonaDTO save = super.createEntidadPersona(empleadoMapper.mapEmpleadoDTO((EmpleadoDTO) personaDTO));
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",save);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpleado(@PathVariable Integer id,
                                            @Valid @RequestBody EmpleadoDTO empleadoDTO, BindingResult result){

        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO= super.findPersonaById(id);
        EmpleadoDTO dto;
        Empleado empleadoUpdate;

        if(personaDTO==null) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("%s con id %d no existe",nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        dto = ((EmpleadoDTO)personaDTO);
        dto.setNombre(empleadoDTO.getNombre());
        dto.setApellido(empleadoDTO.getApellido());
        dto.setDireccion(empleadoDTO.getDireccion());
        dto.setDni(empleadoDTO.getDni());
        dto.setTipoEmpleado(empleadoDTO.getTipoEmpleado());
        dto.setSueldo(empleadoDTO.getSueldo());
        dto.setPabellon(empleadoDTO.getPabellon());

        empleadoUpdate = empleadoMapper.mapEmpleadoDTO(dto);
        mensaje.put("datos",super.altaEntidad(empleadoUpdate));
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpleadoId(@PathVariable Integer id){

        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaById(id);

        if(personaDTO==null) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }

        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @GetMapping("/nombre-apellido/{nombre}/{apellido}")
    public ResponseEntity<?> findEmpleadoNombreApellido(@PathVariable String nombre, @PathVariable String apellido){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaByNombreYApellido(nombre,apellido);
        if (personaDTO==null){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("No se encontro persona con nombre +%s y appelido %s",nombre,apellido));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",personaDTO);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @GetMapping("/empleado-dni")
    public ResponseEntity<Map<String, Object>> findAlumnoDni(@RequestParam String dni){

        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaByDni(dni);

        if (dto == null){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encontro %s con DNI: %s",nombre_entidad,dni));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",dto);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @GetMapping("/tipo-empleado")
    public ResponseEntity<?> findEmpleadosTipoEmpleado(@RequestBody TipoEmpleado tipoEmpleado){

        Map<String,Object> mensaje = new HashMap<>();
        List<Persona> personas = ((List<Persona>)((EmpleadoDAO)service).buscarEmpleadosPorTipoEmpleado(tipoEmpleado));
        List<EmpleadoDTO> dtos = personas.stream()
                .map(persona -> empleadoMapper.mapEmpleado((Empleado) persona))
                .collect(Collectors.toList());

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }
}