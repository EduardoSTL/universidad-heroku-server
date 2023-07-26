package com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones;

import com.springsimplespasos.universidad.universidadbackend.repositorios.AlumnoRepository;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AlumnoDAOImplTest {

    @MockBean
    AlumnoRepository alumnoRepository;
    @Autowired
    AlumnoDAO alumnoDAO;

    @Test
    void buscarAlumnosPorNombreCarrera() {
        //When
        alumnoDAO.buscarAlumnosPorNombreCarrera(anyString());

        //Then
        verify(alumnoRepository).buscarAlumnosPorNombreCarrera(anyString());
    }


}