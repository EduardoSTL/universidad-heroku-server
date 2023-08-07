package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.CarreraDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.CarreraMapperMS;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carreras")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Tag(name = "Carreras", description = "Registro de carreras")
public class CarreraDtoController extends GenericDtoController<Carrera, CarreraDAO>{

    private final CarreraMapperMS carreraMapper;
    public CarreraDtoController(CarreraDAO service, CarreraMapperMS carreraMapper) {
        super(service, "Carrera");
        this.carreraMapper = carreraMapper;
    }

    @Operation(summary = "Obtener todas las carreras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2XX", description = "registro de carreras",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "4XX", description = "no existen carreras",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "5XX", description = "server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @GetMapping
    public ResponseEntity<?> findAllCarreras() {
        Map<String,Object> mensaje= new HashMap<>();
        List<Carrera> carreras = (List<Carrera>) super.obtenerTodos();
        List<CarreraDTO> dtos = carreras
                .stream()
                .map(carreraMapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro obtenido",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findCarreraById(@PathVariable Integer id) {
        Map<String,Object> mensaje= new HashMap<>();
        Optional<Carrera> optionalCarrera =  super.service.findById(id);
        Carrera carrera;
        CarreraDTO dto;
        if ( optionalCarrera== null || optionalCarrera.isEmpty()) {
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            carrera = optionalCarrera.get();
        }
        dto = carreraMapper.mapCarrera(carrera);

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dto);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Crear un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "se creo el registro de alumno",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "422", description = "No se pudo crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @PostMapping
    public ResponseEntity<?> createCarrera( @Valid @RequestBody CarreraDTO carreraDTO,BindingResult result ) {
        Map<String,Object> mensaje = new HashMap<>();

        Carrera carrera = carreraMapper.mapCarrera(carreraDTO);
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        super.altaEntidad(carrera);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data","");
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @Operation(summary = "Actualizar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "422", description = "No se pudo actualizar el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con el id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarrera(@PathVariable Integer id, @Valid @RequestBody CarreraDTO carreraDTO,BindingResult result){
        Map<String,Object> mensaje= new HashMap<>();
        Carrera carrera;
        CarreraDTO dto;
        Optional<Carrera> optionalCarrera = super.service.findById(id);
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        if (optionalCarrera ==null || optionalCarrera.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con di %d no existe",nombre_entidad,id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            carrera = optionalCarrera.get();
        }
        dto = carreraMapper.mapCarrera(carrera);
        dto.setNombre(carreraDTO.getNombre());
        dto.setCantidad_anios(carreraDTO.getCantidad_anios());
        dto.setCantidad_materias(carreraDTO.getCantidad_materias());
        carrera = carreraMapper.mapCarrera(dto);
        mensaje.put("datos",service.save(carrera));
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Eliminar un registro ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "registro eliminado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarrera(@PathVariable Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Carrera> optionalCarrera = super.service.findById(id);
        if(optionalCarrera == null || optionalCarrera.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("message",  String.format("Se elimino %s con Id %d", nombre_entidad, id));
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar todos las carreras por nombre si contiene")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @GetMapping("/find-carreras")
    public ResponseEntity<?> findCarreraByNombreContains(@RequestParam String carrera){
        Map<String,Object> mensaje = new HashMap<>();
        List<Carrera> carreras = (List<Carrera>) service.findCarrerasByNombreContains(carrera);
        if (carreras.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existe %s : con nombre %s || El nombre de la carrera debe ser correcto ",nombre_entidad,carrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<CarreraDTO> save = carreras.stream()
                .map(carreraMapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar todos las carreras por nombre ignorando")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe el registro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @GetMapping("/find-carreras/ignorecase")
    public ResponseEntity<?> findCarreraByNombreContainsIgnoreCase(@RequestParam String carrera){
        Map<String,Object> mensaje = new HashMap<>();
        List<Carrera> carreras = (List<Carrera>) service.findCarrerasByNombreContainsIgnoreCase(carrera);
        if (carreras.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existe %s : con nombre %s ",nombre_entidad,carrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<CarreraDTO> dtos = carreras.stream()
                .map(carreraMapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar todas las carreras por cantidad de años")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe el registro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @GetMapping("/find-carreras/anios/{anios}")
    public ResponseEntity<?> findCarreraByCantidadAniosAfter(@PathVariable Integer anios){
        Map<String,Object> mensaje = new HashMap<>();
        List<Carrera> carreras = (List<Carrera>) service.findCarrerasByCantidadAniosAfter(anios);
        if (carreras.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existe %s mayor a %d anios",nombre_entidad,anios));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<CarreraDTO> dtos = carreras.stream()
                .map(carreraMapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar todas las carreras por nombre y apellido del profesor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
            @ApiResponse(responseCode = "400", description = "No existe el registro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Carrera.class)))),
    })
    @GetMapping("profesor-carreras/{nombre}/{apellido}")
    public ResponseEntity<Map<String, Object>> findCarrerasProfesorNombreApellido(@PathVariable String nombre, @PathVariable String apellido){
        Map<String,Object> mensaje = new HashMap<>();
        List<Carrera> carreras = (List<Carrera>) service.buscarCarrerasPorProfesorNombreYApellido(nombre,apellido);
        if (carreras.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existe %s vincula al profesor: %s %s",nombre_entidad,nombre,apellido));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<CarreraDTO> dtos = carreras.stream()
                .map(carreraMapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }
}