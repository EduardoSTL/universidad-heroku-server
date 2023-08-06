package com.springsimplespasos.universidad.universidadbackend.modelo.builder;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AulaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PabellonDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import lombok.Data;

@Data
public class AulaDTOBuilder {
    private Integer id;
    private Integer nroAula;
    private String medidas;
    private Integer cantidadPupitres;
    private Pizarron pizarron;
    private PabellonDTO pabellonDTO;

    public AulaDTOBuilder withId() {
        this.id = id;
        return this;
    }

    public AulaDTOBuilder withNroAula() {
        this.nroAula = nroAula;
        return this;
    }

    public AulaDTOBuilder withMedidas(String medidas) {
        this.medidas = medidas;
        return this;
    }

    public AulaDTOBuilder withCantidadPupitres() {
        this.cantidadPupitres = cantidadPupitres;
        return this;
    }

    public AulaDTOBuilder withPizarron() {
        this.pizarron = pizarron;
        return this;
    }

    public AulaDTOBuilder withPabellonDTO(PabellonDTO pabellonDTO) {
        this.pabellonDTO = pabellonDTO;
        return this;
    }

    public AulaDTO build() {
        return new AulaDTO(id, nroAula, medidas, cantidadPupitres, pizarron, pabellonDTO);
    }
}
