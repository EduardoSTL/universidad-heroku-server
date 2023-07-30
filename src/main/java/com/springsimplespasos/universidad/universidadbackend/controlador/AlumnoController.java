
package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController extends PersonaController{

    private final CarreraDAO carreraDAO;

    @Autowired
    public AlumnoController(@Qualifier("alumnoDAOImpl") PersonaDAO alumnoDao, CarreraDAO carreraDAO) {
        super(alumnoDao);
        nombreEntidad = "Alumno";
        this.carreraDAO = carreraDAO;
    }

    /*@GetMapping
    public ResponseEntity<?> obtenerTodos(){
        Map<String, Object> mensaje = new HashMap<>();
        List<Persona> alumnos = (List<Persona>) service.findAll();
        if(alumnos.isEmpty()){
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message", "No existen alumnos");
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("datos", alumnos);
        mensaje.put("success", Boolean.TRUE);
        return ResponseEntity.ok(mensaje  + "\tAlumnos: " + alumnos);
    }*/

    /*@GetMapping("/{id}")
    public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable(required = false) Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        Persona alumnoId = null;
        Optional<Persona> oAlumno = service.findById(id);
        if(!oAlumno.isPresent()) {
            //throw new BadRequestException(String.format("Alumno con id %d no existe", id));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("message", String.format("Alumno con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        alumnoId = oAlumno.get();
        mensaje.put("datos", service.save(alumnoId));
        mensaje.put("succes", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }*/

    /*@PostMapping
    public Persona altaAlumno(@RequestBody Persona alumno){
        return service.save(alumno);
    }*/

    //ResponseEntity
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAlumno(@PathVariable Integer id, @RequestBody Persona alumno){
        Map<String, Object> mensaje = new HashMap<>();
        Persona alumnoUpdate = null;
        Optional<Persona> oAlumno = service.findById(id);
        if(!oAlumno.isPresent()) {
            //throw new BadRequestException(String.format("Alumno con id %d no existe", id));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("Alumno con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        alumnoUpdate = oAlumno.get();
        alumnoUpdate.setNombre(alumno.getNombre());
        alumnoUpdate.setApellido(alumno.getApellido());
        alumnoUpdate.setDireccion(alumno.getDireccion());
        mensaje.put("datos", service.save(alumnoUpdate));
        mensaje.put("succes", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }


    @GetMapping("/alumnos-carrera/{carrera}")
    public Iterable<Persona> buscarAlumnosPorNombreCarrera(@PathVariable String nombre){
        return ((AlumnoDAO)service).buscarAlumnosPorNombreCarrera(nombre);
    }

    /*@DeleteMapping("/{id}")
    public void eliminarAlumno(@PathVariable Integer id){
        service.deleteById(id);
    }*/


    @PutMapping("/{idAlumno}/carrera/{idCarrera}")
    public ResponseEntity<?> asignarCarreraAlumno(@PathVariable Integer idAlumno, @PathVariable Integer idCarrera){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona> oAlumno = service.findById(idAlumno);
        if(!oAlumno.isPresent()) {
            //throw new BadRequestException(String.format("Alumno con id %d no existe", idAlumno));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("Alumno con id %d no existe", idAlumno));
            return ResponseEntity.badRequest().body(mensaje);
        }

        Optional<Carrera> oCarrera = carreraDAO.findById(idCarrera);
        if(!oCarrera.isPresent()){
            //throw new BadRequestException(String.format("Carrera con id %d no existe", idCarrera));
            mensaje.put("succes", Boolean.FALSE);
            mensaje.put("message", String.format("Carrera con id %d no existe", idCarrera));
            return ResponseEntity.badRequest().body(mensaje);
        }

        Persona alumno = oAlumno.get();
        Carrera carrera = oCarrera.get();
        ((Alumno)alumno).setCarrera(carrera);
        mensaje.put("datos", service.save(alumno));
        mensaje.put("succes", Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }
}

