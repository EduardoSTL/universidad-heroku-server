package com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.repositorios.PabellonRepository;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.stereotype.Service;

@Service
public class PabellonDAOImpl extends GenericoDAOImpl<Pabellon, PabellonRepository> implements PabellonDAO {
    public PabellonDAOImpl(PabellonRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<Pabellon> findAllPabellonByLocalidad(String localidad) {
        return repository.findAllPabellonByLocalidad(localidad);
    }

    @Override
    public Iterable<Pabellon> findAllPabellonByNombre(String nombre) {
        return repository.findAllPabellonByNombre(nombre);
    }
}
