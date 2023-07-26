package com.springsimplespasos.universidad.universidadbackend.comandos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class PabellonCommand implements CommandLineRunner {
    @Autowired
    PabellonDAO pabellonDao;
    @Autowired
    AulaDAO aulaDao;

    @Override
    public void run(String... args) throws Exception {
        /*System.out.println("---------- Alumnos Command ----------");
        pabellonDao.save(ObjetosDummy.getPabellonUno());
        pabellonDao.save(ObjetosDummy.getPabellonDos());

        System.out.println("---------- Pabellones ----------");
        Iterable<Pabellon> pabellones= pabellonDao.findAll();
        pabellones.forEach(System.out::println);

        System.out.println("---------- buscar todos los Pabellones por localidad ----------");
        pabellones=pabellonDao.findAllPabellonByLocalidad("Rodriguez Peña");
        pabellones.forEach(System.out::println);

        System.out.println("---------- buscar Pabellon por nombre ----------");
        pabellonDao.findAllPabellonByNombre("Pabellon Dos");

        System.out.println("---------- buscar aulas por nombre de pabellon ----------");
        Pabellon pabellon=pabellonDao.findById(1).orElseThrow();
        Iterable<Aula>aulas= aulaDao.findAulasByPabellonNombre(pabellon.getNombre());
        aulas.forEach(System.out::println);*/
    }
}
