package com.springsimplespasos.universidad.universidadbackend.comandos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@Order(4)
public class AlumnoCommand implements CommandLineRunner {

    @Autowired
    @Qualifier("alumnoDAOImpl")
    private PersonaDAO personaDao;

    @Autowired
    private CarreraDAO carreraDao;

    @Override
    public void run(String... args) throws Exception {
        /*System.out.println("----------------- ALumnos Command -----------------");
        ((AlumnoDAO)personaDao).save(ObjetosDummy.getAlumnoUno());
        ((AlumnoDAO)personaDao).save(ObjetosDummy.getAlumnoDos());

        Iterable<Persona> alumnos=((AlumnoDAO)personaDao).findAll();
        alumnos.forEach(System.out::println);
        System.out.println("Alumno ");
        Persona alumno =personaDao.findById(5).orElseThrow();
        System.out.println(alumno);

        Carrera carrera= carreraDao.findById(1).orElseThrow();
        ((Alumno)alumno).setCarrera(carrera);
        personaDao.save(alumno);


        System.out.println("----------------- Busqueda de Personas por Apellido -----------------");
        Iterable<Persona> iPersona = personaDao.buscarPersonaPorApellido(ObjetosDummy.getAlumnoDos().getApellido());
        iPersona.forEach(System.out::println);

        System.out.println("----------------- Busqueda de Personas por DNI -----------------");
        Optional<Persona> oPersona = personaDao.buscarPorDni(ObjetosDummy.getEmpleadoUno().getDni());
        if (oPersona.isPresent()){
            System.out.println(oPersona.get().toString());
        }
        System.out.println("--------- Busqueda de Personas por Nombre y Apellido ---------");
        Optional<Persona> oPersonaDos = personaDao.buscarPorNombreYApellido(ObjetosDummy.getProfesorDos().getNombre(),
                ObjetosDummy.getProfesorDos().getApellido());
        if (oPersona.isPresent()){
            System.out.println(oPersonaDos.get().toString());
        }
        System.out.println("--------- Busqueda de Alumnos por Carrera ---------");
        Iterable<Persona> iAlumnos= ((AlumnoDAO)personaDao).buscarAlumnosPorNombreCarrera
                (ObjetosDummy.getCarreraIngSis().getNombre());
        iAlumnos.forEach(System.out::println);*/
    }
}
