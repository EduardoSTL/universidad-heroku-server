/*
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exception.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.web.bind.annotation.*;

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
    public Aula asignarPabellonAula(@PathVariable Integer idAula, @PathVariable Integer idPabellon){
        Optional<Aula> oAula = service.findById(idAula);
        if(!oAula.isPresent()) {
            throw new BadRequestException(String.format("Aula con id %d no existe", idAula));
        }
        Optional<Pabellon> oPabellon = pabellonDAO.findById(idPabellon);
        if(!oPabellon.isPresent()){
            throw new BadRequestException(String.format("Pabellon con id %d no existe", idPabellon));
        }

        Pabellon pabellon = oPabellon.get();
        Aula aula = oAula.get();
        aula.setPabellon(pabellon);
        return service.save(aula);
    }

    @PutMapping("/{id}")
    public Aula actualizarAula(@PathVariable Integer id, @RequestBody Aula aula){
        Aula aulaUpdate = null;
        Optional<Aula> oAula = service.findById(id);
        if(!oAula.isPresent()) {
            throw new BadRequestException(String.format("Aula con id %d no existe", id));
        }
        aulaUpdate =  oAula.get();
        aulaUpdate.setNroAula(aula.getNroAula());
        aulaUpdate.setMedidas(aula.getMedidas());
        aulaUpdate.setPizarron(aula.getPizarron());
        aulaUpdate.setCantidadPupitres(aula.getCantidadPupitres());

        return service.save(aulaUpdate);
    }
}
*/
