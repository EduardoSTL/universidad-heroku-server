package com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.repositorios.PersonaRepository;
import com.springsimplespasos.universidad.universidadbackend.repositorios.ProfesorRepository;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorDAOImpl extends PersonaDAOImpl implements ProfesorDAO {

    public ProfesorDAOImpl(@Qualifier("repositorioProfesores") PersonaRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Persona> findAll() {
        Iterable<Persona> personas = super.findAll();
        List<Persona> profesores = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Profesor) {
                profesores.add(persona);
            }
        }
        return profesores;
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findById(Integer id) {
        Optional<Persona> optionalPersona=super.findById(id);
        Persona persona = optionalPersona.get();
        if (persona instanceof Profesor){
            return optionalPersona;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Persona> buscarProfesoresPorCarrera(String carrera) {
        return  ((ProfesorRepository)repository).buscarProfesoresPorCarrera(carrera);
    }
}
