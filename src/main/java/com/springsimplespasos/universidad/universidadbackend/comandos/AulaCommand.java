package com.springsimplespasos.universidad.universidadbackend.comandos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Order(5)
public class AulaCommand implements Serializable {

    @Autowired
    private AulaDAO aulaDao;
    @Autowired
    private PabellonDAO pabellonDao;

    //@Override
    public void run(String... args) throws Exception {
        /*System.out.println("---------- Aula Command ----------");
        aulaDao.save(ObjetosDummy.getAula123());
        aulaDao.save(ObjetosDummy.getAula451());

        //findAulaByNroAula
        System.out.println("---------- buscar aula por N° de aula ----------");
        System.out.println(aulaDao.findAulaByNroAula(ObjetosDummy.getAula123().getNroAula()));
        //Pabellon pabellon=pabellonDAO.findById(1).orElseThrow();
        aulaDao.findAulasByPizarron(Pizarron.PIZARRA_BLANCA);
        //aulaDAO.findAulasByPabellonNombre(pabellon.toString());*/
    }
}
