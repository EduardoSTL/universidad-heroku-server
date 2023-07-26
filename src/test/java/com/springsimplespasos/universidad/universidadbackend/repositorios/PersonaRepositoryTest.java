package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.datos.DatosDummy;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.springsimplespasos.universidad.universidadbackend.datos.DatosDummy.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonaRepositoryTest {

    @Autowired
    @Qualifier("repositorioAlumnos")
    PersonaRepository alumnoRepository;
    @Autowired
    @Qualifier("empleadoRepository")
    PersonaRepository empleadoRepository;
    @Autowired
    @Qualifier("profesorRepository")
    PersonaRepository profesoreRepository;

    @Test
    void buscarPorNombreYApellido() {
        //Given
        Persona save = empleadoRepository.save(empleado01());

        //When
        Optional<Persona> expected = empleadoRepository.buscarPorNombreYApellido(empleado01().getNombre(),
                empleado01().getApellido());

        //Then
        assertThat(expected.get()).isInstanceOf(Empleado.class);
        assertThat(expected.get()).isEqualTo(save);
    }

    @Test
    void buscarPorDni() {
        //Given
        Persona save = profesoreRepository.save(profesor01());

        //When
        Optional<Persona> expected = profesoreRepository.buscarPorDni(profesor01().getDni());

        //Then
        assertThat(expected.get()).isInstanceOf(Profesor.class);
        assertThat(expected.get()).isEqualTo(save);
        assertThat(expected.get().getDni()).isEqualTo(save.getDni());
    }

    @Test
    void buscarPersonaPorApellido() {
        //Given
        Iterable<Persona> personas = alumnoRepository.saveAll(
                Arrays.asList(
                        alumno01(),
                        alumno02(),
                        alumno03())
        );

        //When
        String apelllido = "Benitez";
        List<Persona> expected = (List<Persona>) alumnoRepository.buscarPersonaPorApellido(apelllido);

        //Then
        assertThat(expected.size() == 2).isTrue();
    }

}