package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.EmpleadoMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruck.ProfesorMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaDtoController extends GenericDtoController<Persona, PersonaDAO> {

    protected AlumnoMapper alumnoMapper;
    protected EmpleadoMapper empleadoMapper;
    protected ProfesorMapper profesorMapper;

    public PersonaDtoController(PersonaDAO service, String nombre_entidad, AlumnoMapper alumnoMapper) {
        super(service, nombre_entidad);
        this.alumnoMapper = alumnoMapper;
    }
    public PersonaDtoController(PersonaDAO service, String nombre_entidad, EmpleadoMapper empleadoMapper) {
        super(service, nombre_entidad);
        this.empleadoMapper = empleadoMapper;
    }

    public PersonaDtoController(PersonaDAO service, String nombre_entidad, ProfesorMapper profesorMapper) {
        super(service, nombre_entidad);
        this.profesorMapper = profesorMapper;
    }

    public List<PersonaDTO> findAll(){
        List<Persona> personas = super.obtenerTodos();
        List<PersonaDTO> dtos = new ArrayList<>();
        personas.forEach(persona -> {
            if (persona instanceof Alumno && alumnoMapper != null) {
                dtos.add(alumnoMapper.mapAlumno((Alumno) persona));
            } else if (persona instanceof Profesor && profesorMapper != null) {
                dtos.add(profesorMapper.mapProfesor((Profesor) persona));
            } else if (persona instanceof Empleado && empleadoMapper != null) {
                dtos.add(empleadoMapper.mapEmpleado((Empleado) persona));
            }
        });
        return dtos;
    }

    public PersonaDTO findPersonaById(Integer id){
        Optional<Persona>optionalPersona = super.obtenerPorId(id);
        Persona persona;
        PersonaDTO dto = null;
        if (optionalPersona == null || optionalPersona.isEmpty()) {
            return null;
        }else {
            persona = optionalPersona.get();
        }
        if (persona instanceof Alumno){
            dto = alumnoMapper.mapAlumno((Alumno) persona);
        }else if(persona instanceof Profesor){
            dto = profesorMapper.mapProfesor((Profesor) persona);
        }else  if (persona instanceof Empleado){
            dto = empleadoMapper.mapEmpleado((Empleado) persona);
        }
        return dto;
    }

    public PersonaDTO createEntidadPersona(Persona persona){
        Persona personaEntidad = super.altaEntidad(persona);
        PersonaDTO dto = null;
        if (personaEntidad instanceof Alumno){
            dto =alumnoMapper.mapAlumno((Alumno) personaEntidad);
        }else if(personaEntidad instanceof Profesor){
            dto =profesorMapper.mapProfesor((Profesor) personaEntidad);
        }else  if (personaEntidad instanceof Empleado){
            dto =empleadoMapper.mapEmpleado((Empleado) personaEntidad);
        }
        return dto;
    }

    public void deletePersonaById(Integer id){
        super.obtenerPorId(id);
    }

    public PersonaDTO findPersonaByNombreYApellido( String nombre,String apellido){
        Optional<Persona>optionalPersona =service.buscarPorNombreYApellido(nombre,apellido);
        Persona persona;
        PersonaDTO dto = null;
        if (optionalPersona == null) {
            return null;
        }else {
            persona=optionalPersona.get();
        }
        if (persona instanceof Alumno){
            dto =alumnoMapper.mapAlumno((Alumno) persona);

        }else if(persona instanceof Profesor){
            dto =profesorMapper.mapProfesor((Profesor) persona);

        }else  if (persona instanceof Empleado){
            dto =empleadoMapper.mapEmpleado((Empleado) persona);
        }
        return dto;
    }

    public PersonaDTO findPersonaByDni(String dni){
        Optional<Persona>optionalPersona =service.buscarPorDni(dni);
        Persona persona;
        PersonaDTO dto = null;
        if (optionalPersona == null || optionalPersona.isEmpty()) {
            return null;
        }else {
            persona= optionalPersona.get();
        }
        if (persona instanceof Alumno){
            dto = alumnoMapper.mapAlumno((Alumno) persona);

        }else if(persona instanceof Profesor){
            dto =profesorMapper.mapProfesor((Profesor) persona);

        }else  if (persona instanceof Empleado){
            dto =empleadoMapper.mapEmpleado((Empleado) persona);
        }
        return dto;
    }
}