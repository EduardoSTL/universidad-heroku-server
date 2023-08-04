package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alumnos")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Tag(name = "alumnos", description = "Registro de alumnos")
public class AlumnoDtoController extends PersonaDtoController{

    public AlumnoDtoController(PersonaDAO service, AlumnoMapper alumnoMapper){
        super(service, "Alumno", alumnoMapper);
    }

    @Operation(summary = "Get ALUMNO by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2XX", description = "registro de alumno",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "4XX", description = "registro de alumno no existe",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "5XX", description = "server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })

    //obtenerPorId:
    @GetMapping("/{id}")
    public ResponseEntity<?> findAlumnoById(@Parameter(description = "digita el id del alumno")@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        PersonaDTO dto = super.buscarPersonaPorId(id);
        if (dto == null){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No existe %s con ID %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Create ALUMNO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2XX", description = "registro de alumno",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "4XX", description = "error al crear un nuevo alumno",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "5XX", description = "server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })

    @PostMapping
    public ResponseEntity<?> altaAlumno(@Parameter(description = "crear un nuevo alumno")@Valid @RequestBody PersonaDTO personaDTO, BindingResult result){
        Map<String, Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        PersonaDTO save = super.altaPersona(alumnoMapper.mapAlumno((AlumnoDTO) personaDTO));
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }
}
