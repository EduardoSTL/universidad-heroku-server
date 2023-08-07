package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.*;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.*;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.*;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
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
@RequestMapping("/pabellones")
@Tag(name = "Pabellones", description = "Registro de Pabellones")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class PabellonDtoController extends GenericDtoController<Pabellon, PabellonDAO>{

    private final PabellonMapper pabellonMapper;
    private final AulaDAO aulaDAO;

    public PabellonDtoController(PabellonDAO service, PabellonMapper pabellonMapper, AulaDAO aulaDAO) {
        super(service, "pabellon");
        this.pabellonMapper = pabellonMapper;
        this.aulaDAO = aulaDAO;
    }

    @Operation(summary = "Obtiene todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todos los registros de alumnos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @GetMapping
    public ResponseEntity<?> findAllPabellones(){
        Map<String, Object> mensaje = new HashMap<>();
        List<Pabellon> pabellones = super.obtenerTodos();
        List<PabellonDTO> dtos = pabellones.stream()
                .map(pabellon -> pabellonMapper.mapPabellonToDTO(pabellon))
                .collect(Collectors.toList());
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar pabellon por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findPabellonById(@PathVariable Integer id) {
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Pabellon> optionalPabellon = super.obtenerPorId(id);
        Pabellon pabellon;
        PabellonDTO dto;
        if (optionalPabellon == null || optionalPabellon.isEmpty()) {
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            pabellon = optionalPabellon.get();
        }
        dto = pabellonMapper.mapPabellonToDTO(pabellon);
        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Crear un registro de pabellon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "registro creado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "422", description = "Error al crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @PostMapping
    public ResponseEntity<?> createPabellon(@Valid @RequestBody PabellonDTO pabellonDTO, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Pabellon pabellon = pabellonMapper.mapPabellonToEntity(pabellonDTO);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(pabellon));
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @Operation(summary = "Actualizar un registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "422", description = "Error al actualizar el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPabellon(@PathVariable Integer id, @Valid @RequestBody PabellonDTO pabellonDTO,BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Pabellon> optionalPabellon = super.obtenerPorId(id);
        Pabellon pabellon;
        PabellonDTO dto;
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        if(optionalPabellon == null || optionalPabellon.isEmpty()) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            pabellon = optionalPabellon.get();
        }
        dto = pabellonMapper.mapPabellonToDTO(pabellon);
        dto.setNombre(pabellonDTO.getNombre());
        dto.setMts2(pabellonDTO.getMts2());
        dto.setDireccion(pabellonDTO.getDireccion());
        pabellon = pabellonMapper.mapPabellonToEntity(dto);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(pabellon));
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Eliminar un registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro eliminado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePabellonById(@PathVariable Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Pabellon> optionalPabellon = super.obtenerPorId(id);
        if(optionalPabellon == null || optionalPabellon.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("message", String.format("Se elimino %s con Id %d", nombre_entidad, id));
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar registro por localidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con la localidad ingresada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @GetMapping("/pabellones-localidad")
    public ResponseEntity<?> findAllPabellonesByLocalidad(@RequestParam String localidad){
        Map<String,Object> mensaje = new HashMap<>();
        List<Pabellon> pabellones = (List<Pabellon>) service.findAllPabellonByLocalidad(localidad);
        if (pabellones.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existen registros de pabellones en la localidad: %s ",localidad));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<PabellonDTO> dtos = pabellones.stream()
                .map(pabellonMapper::mapPabellonToDTO)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar pabellones por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado con exito",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con nombre",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pabellon.class)))),
    })
    @GetMapping("/pabellones-nombre")
    public ResponseEntity<Map<String, Object>> findAllPabellonByNombre(@RequestParam String nombre){
        Map<String,Object> mensaje = new HashMap<>();
        List<Pabellon> pabellones = (List<Pabellon>) service.findAllPabellonByNombre(nombre);
        if (pabellones.isEmpty()){
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("message", String.format("No existen registros de pabellones con el nombre: %s ",nombre));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<PabellonDTO> dtos = pabellones.stream()
                .map(pabellonMapper::mapPabellonToDTO)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }
}