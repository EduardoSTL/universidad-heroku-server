package com.springsimplespasos.universidad.universidadbackend.comandos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;

import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Order(1)
public class ProfesorCommand implements CommandLineRunner {

    @Autowired
    @Qualifier("profesorDAOImpl")
    private PersonaDAO personaDao;

    @Autowired
    private CarreraDAO carreraDao;

    @Override
    public void run(String... args) throws Exception {
        /*System.out.println("----------------- Profesor Command -----------------");
        personaDao.save(ObjetosDummy.getProfesorUno());
        personaDao.save(ObjetosDummy.getProfesorDos());

        System.out.println("----Busqueda todos los profesores ---");
        Iterable<Persona> profesores=((ProfesorDAO)personaDao).findAll();
        profesores.forEach(System.out::println);

        System.out.println("--------- Busqueda de Profesores por Carrera ---------");
        Iterable<Persona> iProfesores = ((ProfesorDAO)personaDao).buscarProfesoresPorCarrera(ObjetosDummy.getCarreraIngSis().getNombre());
        iProfesores.forEach(System.out::println);

        System.out.println("----Busqueda por apellido---");
        Iterable<Persona> iPersona = personaDao.buscarPersonaPorApellido(ObjetosDummy.getProfesorUno().getApellido());
        iPersona.forEach(System.out::println);
        System.out.println("---Busqueda de persona por DNI----");
        Optional<Persona> optionalPersona = personaDao.buscarPorDni(ObjetosDummy.getProfesorDos().getDni());
        optionalPersona.ifPresent(persona -> System.out.println(persona.toString()));

        System.out.println("----Busqueda de persona por nombre y apellido");
        optionalPersona = personaDao.buscarPorNombreYApellido(ObjetosDummy.getProfesorUno().getNombre(),ObjetosDummy.getProfesorDos().getApellido());
        optionalPersona.ifPresent(persona -> System.out.println(persona.toString()));*/
    }
}
