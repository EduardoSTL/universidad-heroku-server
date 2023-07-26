package com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.repositorios.EmpleadoRepository;
import com.springsimplespasos.universidad.universidadbackend.repositorios.PersonaRepository;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoDAOImpl extends PersonaDAOImpl implements EmpleadoDAO {
    public EmpleadoDAOImpl(@Qualifier("repositorioEmpleados") EmpleadoRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Persona> findAll() {
        Iterable<Persona> personas = super.findAll();
        List<Persona> empleados = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Empleado) {
                empleados.add(persona);
            }
        }
        return empleados;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findById(Integer id) {
        Optional<Persona> optionalPersona=super.findById(id);
        Persona persona = optionalPersona.get();
        if (persona instanceof Empleado){
            return optionalPersona;
        }
        return null;
    }

    @Override
    public Iterable<Persona> buscarEmpleadosPorTipoEmpleado(TipoEmpleado tipoEmpleado) {
        return null;
    }
}
