package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.builder.AulaDTOBuilder;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AulaMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
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
@RequestMapping("/aulas")
@Tag(name = "Aulas", description = "Registro de aulas")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class AulaDtoController extends GenericDtoController<Aula, AulaDAO> {

    private final AulaMapper aulaMapper;
    private final PabellonDAO pabellonDAO;

    public AulaDtoController(AulaDAO service, AulaMapper aulaMapper, PabellonDAO pabellonDAO) {
        super(service, "aula");
        this.aulaMapper = aulaMapper;
        this.pabellonDAO = pabellonDAO;
    }

    @GetMapping("/aula")
    public ResponseEntity<AulaDTO> obtenerAula() {
        AulaDTO aulaDTO = new AulaDTOBuilder()
                .withId()
                .withNroAula()
                .withMedidas("")
                .withCantidadPupitres()
                .withPizarron() // Configuración del enum Pizarron
                .withPabellonDTO(new PabellonDTO())
                .build();
        return ResponseEntity.ok(aulaDTO);
    }

    @Operation(summary = "Buscar todos los registros de aula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "todos los registros de alumnos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping
    public ResponseEntity<?> findAllAulas(){
        Map<String, Object> mensaje = new HashMap<>();
        List<Aula> aulas = super.obtenerTodos();
        List<AulaDTO> save = aulas.stream()
                .map(aula -> aulaMapper.mapAula(aula))
                .collect(Collectors.toList());

        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar aula por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro obtenido",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findAulaById(@PathVariable Integer id) {
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula= super.obtenerPorId(id);
        Aula aula;
        AulaDTO save;
        if (optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message", String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            aula = optionalAula.get();
        }
        save = aulaMapper.mapAula(aula);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Crear un Aula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "se creo el registro de alumno",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "422", description = "No se pudo crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PostMapping
    public ResponseEntity<?> createAula(@Valid @RequestBody AulaDTO aulaDTO, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        Aula aula = aulaMapper.mapAula(aulaDTO);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(aula));
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @Operation(summary = "Actualizar un registro de Aula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizó el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "422", description = "No se pudo crear el registro: Datos no validos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con el id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAula(@PathVariable Integer id,@Valid @RequestBody AulaDTO aulaDTO,BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = super.obtenerPorId(id);
        Aula aula;
        AulaDTO dto;
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.unprocessableEntity().body(mensaje);
        }
        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            aula = optionalAula.get();
        }
        dto = aulaMapper.mapAula(aula);
        dto.setNroAula(aulaDTO.getNroAula());
        dto.setPizarron(aulaDTO.getPizarron());
        dto.setMedidas(aulaDTO.getMedidas());
        dto.setCantidadPupitres(aulaDTO.getCantidadPupitres());
        aula = aulaMapper.mapAula(dto);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(aula));
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Borra un registro de Aula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se borro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumnoById(@PathVariable Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = super.obtenerPorId(id);
        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("message", String.format("Se eliminó %s con Id %d", nombre_entidad, id));
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @Operation(summary = "Buscar aulas por pizarron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con ese pizarron",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PostMapping("/aulas-pizarras")
    public  ResponseEntity<?>findAulasByPizarron(@Valid @RequestBody Pizarron pizarron, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();
        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<Aula> aulas = (List<Aula>) service.findAulasByPizarron(pizarron);
        if (aulas.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("No existen aulas con pizarron: %s",pizarron));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<AulaDTO> dtos = aulas.stream()
                .map(aulaMapper::mapAula)
                .collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar aulas por pabellon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con ese pabellon",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PostMapping("/aulas-pabellon")
    public ResponseEntity<?> findAulasByPabellonNombre(@RequestBody String nombre){
        Map<String,Object> mensaje = new HashMap<>();
        List<Aula> aulas = (List<Aula>) service.findAulasByPabellonNombre(nombre);
        if (aulas.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("No existen aulas en ese pabellon: %s",nombre));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<AulaDTO> dtos = aulas.stream()
                .map(aulaMapper::mapAula)
                .collect(Collectors.toList());

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Buscar aulas por nroAula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con ese nro",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @GetMapping("/nroaulas/{nroAula}")
    public ResponseEntity<?> findAulaByNroAula(@PathVariable Integer nroAula){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = service.findAulaByNroAula(nroAula);
        Aula aula;
        AulaDTO save;
        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message",  String.format("No existe %s con numero de aula: %d", nombre_entidad, nroAula));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            aula = optionalAula.get();
        }
        save = aulaMapper.mapAula(aula);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",save);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Asignar aula a un pabellon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontro el registro ",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
            @ApiResponse(responseCode = "400", description = "No existe registro con ese id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)))),
    })
    @PutMapping("/{idAula}/pabellon/{idPabellon}")
    public ResponseEntity<?> assignPabellonToAula(@PathVariable Integer idAula, @PathVariable Integer idPabellon){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = service.findById(idAula);
        Optional<Pabellon> oPabellon = pabellonDAO.findById(idPabellon);
        Aula aula;
        Pabellon pabellon;
        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, idAula));
            return ResponseEntity.badRequest().body(mensaje);
        }
        if(oPabellon.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("message",String.format("%s con id %d no existe",nombre_entidad, idAula));
            return ResponseEntity.badRequest().body(mensaje);
        }
        pabellon = oPabellon.get();
        aula = optionalAula.get();
        aula.setPabellon(pabellon);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(aula));
        return ResponseEntity.ok().body(mensaje);
    }
}