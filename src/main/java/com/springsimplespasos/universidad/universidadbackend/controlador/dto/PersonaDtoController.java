package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;

import java.util.Optional;

public class PersonaDtoController extends GenericDtoController<Persona, PersonaDAO> {

    protected final AlumnoMapper alumnoMapper;

    public PersonaDtoController(PersonaDAO service, String nombre_entidad, AlumnoMapper alumnoMapper) {
        super(service, nombre_entidad);
        this.alumnoMapper = alumnoMapper;
    }

    public PersonaDTO altaPersona(Persona persona){
        Persona personaEntidad = super.altaEntidad(persona);
        PersonaDTO dto = null;
        if (personaEntidad instanceof Alumno){
            dto = alumnoMapper.mapAlumno((Alumno) personaEntidad);
        } else if (personaEntidad instanceof Profesor) {
            //aplicamos el mapper de profesor
        } else if (personaEntidad instanceof Empleado) {
            //aplicamos el mapper de empleado
        }
        return dto;
    }

    public PersonaDTO buscarPersonaPorId(Integer id){
        Optional<Persona> oPersona = super.obtenerPorId(id);
        Persona persona;
        PersonaDTO dto = null;
        if (oPersona.isEmpty()){
            return null;
        } else {
            persona = oPersona.get();
        }
        if (persona instanceof Alumno){
            dto = alumnoMapper.mapAlumno((Alumno) persona);
        } else if (persona instanceof Profesor) {
            //aplicariamos mapper de profesor
        } else if (persona instanceof Empleado) {
            //aplicariamos mapper de empleado
        }
        return dto;
    }
}
