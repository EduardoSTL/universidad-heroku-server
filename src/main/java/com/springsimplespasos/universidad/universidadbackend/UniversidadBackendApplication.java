package com.springsimplespasos.universidad.universidadbackend;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.*;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class UniversidadBackendApplication {

	@Autowired
	private AlumnoDAO service;

	public static void main(String[] args) {
		String[] beanDefinitionNames = SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
		/*for(String str : beanDefinitionNames){
			System.out.println(str);
		}*/
	}

	/*@Bean
	public CommandLineRunner runner(){
		return args -> {
			Aula aula = new Aula(null, 1, "20x50m", 24, Pizarron.PIZARRA_TIZA);
			Aula save = service.saveAula(aula);
		};
	}*/

	/*@Bean
	public CommandLineRunner runner(){
		return args -> {
			Direccion direccion = new Direccion
					("Reliquia", "211", "2000", "San Miguel", "", "");
			Empleado empleado = new Empleado(null, "Mauricio", "Gonzales", "7772323", direccion, new BigDecimal(280), TipoEmpleado.MANTENIMIENTO);
			Persona save = service.save(empleado);
			Direccion direccion1 = new Direccion("Lagos", "925", "3322", "Santa Ana", "", "Santa Ana");
			Empleado empleado1 = new Empleado(null, "Rodrigo", "Mendez", "408026", direccion1, new BigDecimal(400), TipoEmpleado.ADMINISTRATIVO);
			Persona save1 = service.save(empleado1);
			System.out.println(save.toString() + save1.toString());
		};
	}*/

	/*@Bean
	public CommandLineRunner runner(){
		return args -> {
			Direccion direccion = new Direccion
					("Santos", "646", "1000", "San Vicente", "", "Guadalupe");
			Profesor profesor = new Profesor(null, "Henry", "Morales", "004040", direccion, new BigDecimal(400));
			Persona save = service.save(profesor);
			System.out.println(save.toString());
		};
	}*/

	/*@Bean
	public CommandLineRunner runner(){
		return args -> {
			Direccion direccion = new Direccion
					("Calle Los Proceres", "001", "0011", "San Salvador", "", "San Salvador");
			Persona alumno = new Alumno(null, "Emilio", "Carcamo", "010109892", direccion);
			Persona save = service.save(alumno);
			System.out.println(save.toString());
		};
	}*/
}
