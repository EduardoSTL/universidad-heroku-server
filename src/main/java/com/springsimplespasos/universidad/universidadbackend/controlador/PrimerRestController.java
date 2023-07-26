package com.springsimplespasos.universidad.universidadbackend.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restapi")
public class PrimerRestController {

    @GetMapping("/hola-mundo")
    public String holaMundo(){
        return "Hola Mundo! =D";
    }

}
