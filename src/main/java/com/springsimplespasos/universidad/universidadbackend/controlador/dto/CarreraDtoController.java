package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.CarreraDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.CarreraMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.CarreraMapperMS;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carreras")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Tag(name = "carreras", description = "Registro de carreras")
public class CarreraDtoController {

    @Autowired
    private CarreraDAO carreraDAO;
    @Autowired
    CarreraMapperMS mapper;

    @Operation(summary = "Get ALL CARERRAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2XX", description = "registro de carreras",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "4XX", description = "no existen carreras",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "5XX", description = "server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })

    @GetMapping
    public ResponseEntity<?> findAllCarreras(){
        Map<String, Object> mensaje = new HashMap<>();
        List<Carrera> carreras =(List<Carrera>) carreraDAO.findAll();
        List<CarreraDTO> carreraDTOS = carreras
                .stream()
                .map(mapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", carreraDTOS);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCarrerasById(@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Carrera> carreras = carreraDAO.findById(id);
        Carrera carrera = carreras.get();
        CarreraDTO save = mapper.mapCarrera(carrera);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.ok().body(mensaje);
    }

    @PostMapping
    public ResponseEntity<?> altaCarrera(@RequestBody CarreraDTO carreraDTO){
        Map<String, Object> mensaje = new HashMap<>();
        Carrera carrera = mapper.mapCarrera(carreraDTO);
        carreraDAO.save(carrera);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }
}
