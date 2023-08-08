package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.ProfesorDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.CarreraMapperMS;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.ProfesorMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profesores")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Tag(name = "Profesores", description = "Registro de profesores")
public class ProfesorDtoController extends PersonaDtoController {
    private final CarreraDAO carreraDAO;
    private final CarreraMapperMS carreraMapperMs;

    public ProfesorDtoController(@Qualifier("profesorDAOImpl") PersonaDAO service, ProfesorMapper profesorMapper, CarreraDAO carreraDAO, CarreraMapperMS carreraMapperMs) {
        super(service, "profesor", profesorMapper);
        this.carreraDAO = carreraDAO;
        this.carreraMapperMs = carreraMapperMs;
    }

    @Operation(summary = "Obtener todos los profesores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registros encontrados con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @GetMapping
    public ResponseEntity<?> findAllProfesores(){
        Map<String, Object> mensaje = new HashMap<>();
        List<PersonaDTO> dtos = super.findAll();
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar el registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findProfesorById(@PathVariable Integer id) {
        Map<String, Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.findPersonaById(id);
        if (dto == null) {
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("No existe registro de %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Crear un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "registro creado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "Error al crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @PostMapping
    public ResponseEntity<?> createProfesor(@Valid @RequestBody PersonaDTO personaDTO, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        PersonaDTO save = super.createEntidadPersona(profesorMapper.mapProfesorDTO((ProfesorDTO) personaDTO));
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",save);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @Operation(summary = "Actualizar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro actualizado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "Error al actualizar el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "404 Not Found", description = "No existe registro con el id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfesor(@PathVariable Integer id, @Valid @RequestBody ProfesorDTO profesorDTO, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO= super.findPersonaById(id);
        ProfesorDTO dto;
        Profesor profesorUpdate;
        if(personaDTO==null) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        dto = ((ProfesorDTO)personaDTO);
        dto.setNombre(profesorDTO.getNombre());
        dto.setApellido(profesorDTO.getApellido());
        dto.setDireccion(profesorDTO.getDireccion());
        dto.setDni(profesorDTO.getDni());
        dto.setSueldo(profesorDTO.getSueldo());
        profesorUpdate = profesorMapper.mapProfesorDTO(dto);
        mensaje.put("datos",super.createEntidadPersona(profesorUpdate));
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Eliminar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro eliminado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesorById(@PathVariable Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaById(id);
        if(personaDTO==null) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("message",  String.format("Se elimino %s con Id %d", nombre_entidad, id));
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar un registro por nombre y apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @GetMapping("/nombre-apellido/{nombre}/{apellido}")
    public ResponseEntity<?> findProfesorByNombreApellido(@PathVariable String nombre, @PathVariable String apellido){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaByNombreYApellido(nombre,apellido);
        if (personaDTO==null){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("No se existe registro de persona con nombre +%s y appelido %s",nombre,apellido));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos",personaDTO);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar registro por DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con DNI",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @GetMapping("/profesor-dni")
    public ResponseEntity<Map<String, Object>> findProfesorByDni(@RequestParam String dni){
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

    @Operation(summary = "Buscar un registro por nombre y apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @GetMapping("/profesores-carreras")
    public ResponseEntity<?> findProfesoresCarrera(@RequestBody String carrera){
        Map<String,Object> mensaje = new HashMap<>();
        List<Persona> profeores = ((List<Persona>)((ProfesorDAO)service).buscarProfesoresPorCarrera(carrera));
        if (profeores.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existen profesores en carrera: %s ",carrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<ProfesorDTO> dtos = profeores.stream()
                .map(persona -> profesorMapper.mapProfesor((Profesor) persona))
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Asignar una carrera a profesor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 OK", description = "Carrera asignada con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
            @ApiResponse(responseCode = "400 Bad Request", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)))),
    })
    @PutMapping("/{idProfesor}/carrera/{idCarrera}")
    public ResponseEntity<?> assignCarreraProfesor(@PathVariable Integer idProfesor, @PathVariable Integer idCarrera){
        Map<String,Object> mensaje = new HashMap<>();
        PersonaDTO personaDTO = super.findPersonaById(idProfesor);
        Optional<Carrera> oCarrera;
        Profesor profesor;
        Carrera carrera;
        Set<Carrera> carreras;
        if(personaDTO==null) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, idProfesor));
            return ResponseEntity.badRequest().body(mensaje);
        }
        oCarrera = carreraDAO.findById(idCarrera);
        if(oCarrera.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("Carrera con id %d no existe",idCarrera ));
            return ResponseEntity.badRequest().body(mensaje);
        }
        profesor = profesorMapper.mapProfesorDTO((ProfesorDTO) personaDTO);
        carrera = oCarrera.get();
        carreras = new HashSet<>();
        carreras.add(carrera);
        profesor.setCarreras(carreras);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",service.save(profesor));
        return ResponseEntity.ok().body(mensaje);
    }
}