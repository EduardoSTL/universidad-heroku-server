package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AulaMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
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
@RequestMapping("/aulas")
@Tag(name = "aulas", description = "Registro de aulas")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class AulaDtoController extends GenericDtoController<Aula, AulaDAO> {

    private final AulaMapper aulaMapper;
    private final PabellonDAO pabellonDAO;

    public AulaDtoController(AulaDAO service, AulaMapper aulaMapper, PabellonDAO pabellonDAO) {
        super(service, "aula");
        this.aulaMapper = aulaMapper;
        this.pabellonDAO = pabellonDAO;
    }

    @GetMapping
    public ResponseEntity<?> findAllAulas(){
        Map<String, Object> mensaje = new HashMap<>();
        List<Aula> aulas = super.obtenerTodos();
        List<AulaDTO> save = aulas.stream()
                .map(aula -> aulaMapper.mapAula(aula))
                .collect(Collectors.toList());

        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.ok().body(mensaje);
    }

    @Operation(summary = "Obtiene el aula id")
    @ApiResponses(value = {
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> findAulaId(@PathVariable Integer id) {

        Map<String, Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula= super.obtenerPorId(id);
        Aula aula;
        AulaDTO save;

        if (optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            aula = optionalAula.get();
        }
        save = aulaMapper.mapAula(aula);

        mensaje.put("succes", Boolean.TRUE);
        mensaje.put("data", save);
        return ResponseEntity.ok().body(mensaje);
    }

    @PostMapping
    public ResponseEntity<?> createAula(@Valid @RequestBody AulaDTO aulaDTO, BindingResult result){

        Map<String,Object> mensaje = new HashMap<>();

        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }

        Aula aula = aulaMapper.mapAula(aulaDTO);

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",super.altaEntidad(aula));
        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateAula(@PathVariable Integer id,
                                            @Valid @RequestBody AulaDTO aulaDTO,BindingResult result){

        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = super.obtenerPorId(id);
        Aula aula;
        AulaDTO dto;

        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }

        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("%s con id %d no existe",nombre_entidad, id));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlumnoId(@PathVariable Integer id){

        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = super.obtenerPorId(id);

        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje",  String.format("No existe %s con Id %d", nombre_entidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }

        super.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.OK).body(mensaje);
    }

    @PostMapping("/aulas-pizarras")
    public  ResponseEntity<?>findAulasByPizarron(@Valid @RequestBody Pizarron pizarron, BindingResult result){
        Map<String,Object> mensaje = new HashMap<>();

        if (result.hasErrors()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<Aula> aulas = (List<Aula>) service.findAulasByPizarron(pizarron);
        List<AulaDTO> dtos = aulas.stream()
                .map(aula -> aulaMapper.mapAula(aula))
                .collect(Collectors.toList());

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }
    @PostMapping("/aulas-pabellon")
    public ResponseEntity<?> findAulasByPabellonNombre(@RequestBody String nombre){
        Map<String,Object> mensaje = new HashMap<>();
        List<Aula> aulas = (List<Aula>) service.findAulasByPabellonNombre(nombre);
        List<AulaDTO> dtos = aulas.stream()
                .map(aula -> aulaMapper.mapAula(aula))
                .collect(Collectors.toList());

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",dtos);
        return ResponseEntity.ok().body(mensaje);
    }
    @GetMapping("/nroaulas/{nroAula}")
    public ResponseEntity<?> findAulaByNroAula(@PathVariable Integer nroAula){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = service.findAulaByNroAula(nroAula);
        Aula aula;
        AulaDTO save;

        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje",  String.format("No existe %s con numero de aula: %d", nombre_entidad, nroAula));
            return ResponseEntity.badRequest().body(mensaje);
        }else {
            aula = optionalAula.get();
        }
        save = aulaMapper.mapAula(aula);

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",save);
        return ResponseEntity.ok().body(mensaje);
    }

    @PutMapping("/{idAula}/pabellon/{idPabellon}")
    public ResponseEntity<?> assignPabellonAula(@PathVariable Integer idAula, @PathVariable Integer idPabellon){

        Map<String,Object> mensaje = new HashMap<>();
        Optional<Aula> optionalAula = service.findById(idAula);
        Optional<Pabellon> oPabellon = pabellonDAO.findById(idPabellon);
        Aula aula;
        Pabellon pabellon;

        if(optionalAula == null || optionalAula.isEmpty()) {
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("%s con id %d no existe",nombre_entidad, idAula));
            return ResponseEntity.badRequest().body(mensaje);
        }

        if(oPabellon.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("%s con id %d no existe",nombre_entidad, idAula));
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