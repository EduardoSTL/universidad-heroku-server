package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.EmpleadoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.EmpleadoMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.EmpleadoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.condition.*;
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
@Tag(name = "Empleados", description = "Registro de empleados")
public class EmpleadoDtoController extends PersonaDtoController {

    public EmpleadoDtoController(@Qualifier("empleadoDAOImpl") PersonaDAO service, EmpleadoMapper empleadoMapper) {
        super(service, "empleado", empleadoMapper);
    }

    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Todos los registros de empleados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @GetMapping
    public ResponseEntity<?> findAllEmpleados(){
        Map<String, Object> mensaje = new HashMap<>();
        List<PersonaDTO> dtos = super.findAll();
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findEmpleadoById(@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaById(id);
        if (dto == null){
            mensaje.put("success", Boolean.TRUE);
            mensaje.put("message", String.format("No existe %s con el Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Crea un registro de empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201 Created", description = "Registro creado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "Error al crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @PostMapping("/empleados")
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

    @Operation(summary = "Actualizar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro actualizado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "Error al actualizar el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpleado(@PathVariable Integer id, @Valid @RequestBody EmpleadoDTO empleadoDTO, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO= super.findPersonaById(id);
        EmpleadoDTO dto;
        Empleado empleadoUpdate;
        if(personaDTO==null) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, id));
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

    @Operation(summary = "Eliminar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro eliminado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpleadoId(@PathVariable Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaById(id);
        if(personaDTO==null) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar un registro por nombre y apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @GetMapping("/nombre-apellido/{nombre}/{apellido}")
    public ResponseEntity<?> findEmpleadoNombreApellido(@PathVariable String nombre, @PathVariable String apellido){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaByNombreYApellido(nombre,apellido);
        if (personaDTO==null){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("No existe persona con nombre +%s y appelido %s en el registro",nombre,apellido));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",personaDTO);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar un registro por DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con el DNI",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
    @GetMapping("/empleado-dni")
    public ResponseEntity<Map<String, Object>> findAlumnoDni(@RequestParam String dni){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaByDni(dni);
        if (dto == null){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message", String.format("No se encontro %s con DNI: %s",nombre_entidad,dni));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",dto);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar todos los empleados por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Empleado.class)))),
    })
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