
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/aulas")
public class AulaController extends GenericController<Aula, AulaDAO>{

    private final PabellonDAO pabellonDAO;

    public AulaController(AulaDAO service, PabellonDAO pabellonDAO) {
        super(service);
        this.pabellonDAO = pabellonDAO;
    }

    @PostMapping("/aulas-pizarras")
    public  Iterable<Aula>findAulasByPizarron(@RequestBody Pizarron pizarron){
        return service.findAulasByPizarron(pizarron);
    }
    @PostMapping("/aulas-pabellon")
    public Iterable<Aula>findAulasByPabellonNombre(@RequestBody String nombre){
        return service.findAulasByPabellonNombre(nombre);
    }
    @GetMapping("/nroaulas/{nroAula}")
    public Optional<Aula> findAulaByNroAula(@PathVariable Integer nroAula){
        return service.findAulaByNroAula(nroAula);
    }

    @PutMapping("/{idAula}/pabellon/{idPabellon}")
    public ResponseEntity<?> asignarPabellonAula(@PathVariable Integer idAula, @PathVariable Integer idPabellon){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Aula> oAula = service.findById(idAula);
        if(!oAula.isPresent()) {
            //throw new BadRequestException(String.format("Aula con id %d no existe", idAula));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("Aula con id %d no existe", idAula));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Optional<Pabellon> oPabellon = pabellonDAO.findById(idPabellon);
        if(!oPabellon.isPresent()){
            //throw new BadRequestException(String.format("Pabellon con id %d no existe", idPabellon));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("Alumno con id %d no existe", idPabellon));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Aula aula = oAula.get();
        Pabellon pabellon = oPabellon.get();
        ((Aula)aula).setPabellon(pabellon);
        mensaje.put("datos", service.save(aula));
        mensaje.put("succes", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAula(@PathVariable Integer id, @RequestBody Aula aula){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Aula> oAula = service.findById(id);
        if(!oAula.isPresent()) {
            //throw new BadRequestException(String.format("Aula con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("Aula con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Aula aulaUpdate = oAula.get();
        aulaUpdate.setNroAula(aula.getNroAula());
        aulaUpdate.setMedidas(aula.getMedidas());
        aulaUpdate.setPizarron(aula.getPizarron());
        aulaUpdate.setCantidadPupitres(aula.getCantidadPupitres());
        mensaje.put("datos", service.save(aulaUpdate));
        mensaje.put("succes", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }
}

