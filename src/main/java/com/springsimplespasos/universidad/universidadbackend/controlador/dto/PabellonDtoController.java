package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.*;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.*;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.*;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping
    public ResponseEntity<?> findAllPabellones(){
        Map<String, Object> mensaje = new HashMap<>();
        List<Pabellon> pabellones = super.obtenerTodos();
        List<PabellonDTO> dtos = pabellones.stream()
                .map(pabellon -> pabellonMapper.mapPabellon(pabellon))
                .collect(Collectors.toList());

        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Obtiene el Pabellon id")
    @ApiResponses(value = {
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> findPabellonId(@PathVariable Integer id) {

        Map<String, Object> mensaje = new HashMap<>();
        Optional<Pabellon> optionalPabellon = super.obtenerPorId(id);
        Pabellon pabellon;
        PabellonDTO dto;

        if (optionalPabellon == null || optionalPabellon.isEmpty()) {
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            pabellon = optionalPabellon.get();
        }
        dto = pabellonMapper.mapPabellon(pabellon);

        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", dto);
        return ResponseEntity.ok().body(mensaje);
    }

    @PostMapping
    public ResponseEntity<?> createPabellon(@Valid @RequestBody PabellonDTO pabellonDTO, BindingResult result){

        Map<String,Object> mensaje = new HashMap<>();

        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }

        Pabellon pabellon = pabellonMapper.mapPabellon(pabellonDTO);

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(pabellon));
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPabellon(@PathVariable Integer id,
                                                @Valid @RequestBody PabellonDTO pabellonDTO,BindingResult result){

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
            mensaje.put("mensaje",String.format("%s con id %d no existe",nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            pabellon = optionalPabellon.get();
        }
        dto = pabellonMapper.mapPabellon(pabellon);
        dto.setNombre(pabellonDTO.getNombre());
        dto.setMts2(pabellonDTO.getMts2());
        dto.setDireccion(pabellonDTO.getDireccion());
        pabellon = pabellonMapper.mapPabellon(dto);

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(pabellon));
        return ResponseEntity.ok().body(mensaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePabellonId(@PathVariable Integer id){

        Map<String,Object> mensaje = new HashMap<>();
        Optional<Pabellon> optionalPabellon = super.obtenerPorId(id);

        if(optionalPabellon == null || optionalPabellon.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }

        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @PostMapping("/pabellones-localidad")
    public ResponseEntity<?> findAllPabellonByLocalidad(@RequestParam String localidad){
        Map<String,Object> mensaje = new HashMap<>();
        List<Pabellon> pabellones = (List<Pabellon>) service.findAllPabellonByLocalidad(localidad);
        List<PabellonDTO> dtos = pabellones.stream()
                .map(pabellon -> pabellonMapper.mapPabellon(pabellon))
                .collect(Collectors.toList());

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }

    @PostMapping("/pabellones-nombre")
    public ResponseEntity<Map<String, Object>> findAllPabellonByNombre(@RequestParam String nombre){
        Map<String,Object> mensaje = new HashMap<>();

        List<Pabellon> pabellones = (List<Pabellon>) service.findAllPabellonByNombre(nombre);
        List<PabellonDTO> dtos = pabellones.stream()
                .map(pabellon -> pabellonMapper.mapPabellon(pabellon))
                .collect(Collectors.toList());

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }
}