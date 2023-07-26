package com.springsimplespasos.universidad.universidadbackend.comandos;

import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class InitCommand implements CommandLineRunner {

    @Autowired
    @Qualifier(value = "alumnoDAOImpl")
    private PersonaDAO servicioAlumno;

    @Autowired
    @Qualifier(value = "empleadoDAOImpl")
    private PersonaDAO servicioEmpleado;

    @Autowired
    @Qualifier(value = "alumnoDAOImpl")
    private PersonaDAO servicioProfesor;

    @Autowired
    private AulaDAO servicioAula;

    @Autowired
    private PabellonDAO servicioPabellon;

    @Autowired
    private CarreraDAO servicioCarrera;

    @Override
    public void run(String... args) throws Exception {

    }
}
