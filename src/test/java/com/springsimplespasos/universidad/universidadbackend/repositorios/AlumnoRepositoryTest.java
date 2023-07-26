package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static com.springsimplespasos.universidad.universidadbackend.datos.DatosDummy.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AlumnoRepositoryTest {

    @Autowired
    @Qualifier("repositorioAlumnos")
    PersonaRepository alumnoRepository;
    @Autowired
    CarreraRepository carreraRepository;

    @Test
    void buscarAlumnosPorNombreCarrera() {
        //Given
        Iterable<Persona> personas = alumnoRepository.saveAll(
                Arrays.asList(
                        alumno01(),
                        alumno02(),
                        alumno03())
        );

        Carrera save = carreraRepository.save(carrera01(false));

        personas.forEach(alumno -> ((Alumno)alumno).setCarrera(save));

        alumnoRepository.saveAll(personas);

        //When
        String carreraNombre = "Ingenieria en Sistemas";
        List<Persona> expected = (List<Persona>) ((AlumnoRepository) alumnoRepository).buscarAlumnosPorNombreCarrera(carreraNombre);

        //Then
        assertThat(expected.size() == 3).isTrue();
    }

    @Test
    void buscarAlumnosPorNombreCarrerasinValores() {
        //Given
        Iterable<Persona> personas = alumnoRepository.saveAll(
                Arrays.asList(
                        alumno01(),
                        alumno02(),
                        alumno03())
        );

        Carrera save = carreraRepository.save(carrera01(false));

        personas.forEach(alumno -> ((Alumno)alumno).setCarrera(save));

        alumnoRepository.saveAll(personas);

        //When
        String carreraNombre = "Ingenieria en Alimentos";
        List<Persona> expected = (List<Persona>) ((AlumnoRepository) alumnoRepository).buscarAlumnosPorNombreCarrera(carreraNombre);

        //Then
        assertThat(expected.isEmpty()).isTrue();
    }

}