package com.salesianostriana.dam.proyectorepaso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.salesianostriana.dam.proyectorepaso.model.Centro;
import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Festivo;
import com.salesianostriana.dam.proyectorepaso.model.Reserva;
import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.servicios.CentroServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.EspacioServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.FestivoServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.ReservaServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.UsuarioServicio;

@SpringBootApplication
public class ProyectoRepasoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoRepasoApplication.class, args);
	}

	@Profile("dev")
	@Bean
	public CommandLineRunner init(UsuarioServicio servicio, CentroServicio cservicio, EspacioServicio eservicio,
			ReservaServicio rservicio, FestivoServicio fservicio, BCryptPasswordEncoder passwordEncoder) {
		return args -> {

			Centro c = new Centro();
			c.setNombre("Salesianos de Triana");
			c.setLocalidad("Sevilla");
			c.setProvincia("Sevilla");
			c.setCcaa("Andalucía");
			cservicio.save(c);
			
			Centro c2 = new Centro();
			c2.setNombre("Santa Joaquina de Vedruna");
			c2.setLocalidad("Sevilla");
			c2.setProvincia("Sevilla");
			c2.setCcaa("Andalucía");
			cservicio.save(c2);
			
			Espacio e = new Espacio();
			e.setNombre("1DAM");
			e.setCentro(c);
			e.setAlumnos(30);
			e.setPuestos(15);
			eservicio.save(e);

			Espacio e2 = new Espacio();
			e2.setNombre("2DAM");
			e2.setCentro(c);
			e2.setAlumnos(30);
			e2.setPuestos(15);
			eservicio.save(e2);
			
			Espacio e3 = new Espacio();
			e3.setNombre("1TELECO");
			e3.setCentro(c);
			e3.setAlumnos(30);
			e3.setPuestos(15);
			eservicio.save(e3);
			
			Espacio e4 = new Espacio();
			e4.setNombre("2TELECO");
			e4.setCentro(c);
			e4.setAlumnos(30);
			e4.setPuestos(15);
			eservicio.save(e4);
			
			Usuario u = new Usuario();
			u.setAdmin(false);
			u.setSuperadmin(false);
			u.setActivo(true);
			u.setRegistroConfirmado(true);
			u.setUsername("Usuario");
			u.setEmail("usuario@usuario.com");
			u.setCentro(c);
			u.setPassword("1234");
			servicio.save(u);

			Reserva r = new Reserva();
			r.setFecha(LocalDate.of(2019, 10, 20));
			r.setHora(LocalTime.of(8, 0));
			r.setEspacio(e);
			r.setUsuario(u);
			rservicio.save(r);

			Reserva r2 = new Reserva();
			r2.setFecha(LocalDate.of(2019, 10, 5));
			r2.setHora(LocalTime.of(9, 0));
			r2.setEspacio(e);
			r2.setUsuario(u);
			rservicio.save(r2);
			
			Reserva r3 = new Reserva();
			r3.setFecha(LocalDate.of(2019, 10, 8));
			r3.setHora(LocalTime.of(10, 0));
			r3.setEspacio(e);
			r3.setUsuario(u);
			rservicio.save(r3);
			
			Usuario a = new Usuario();
			a.setAdmin(true);
			a.setSuperadmin(false);
			a.setActivo(true);
			a.setRegistroConfirmado(true);
			a.setUsername("Admin");
			a.setEmail("admin@admin.com");
			a.setCentro(c);
			a.setPassword("admin");

			servicio.save(a);

			Usuario sa = new Usuario();
			sa.setAdmin(false);
			sa.setSuperadmin(true);
			sa.setActivo(true);
			sa.setRegistroConfirmado(true);
			sa.setUsername("Superadmin");
			sa.setEmail("superadmin@superadmin.com");
			sa.setCentro(c);
			sa.setPassword("superadmin");

			servicio.save(sa);

			Festivo f = new Festivo();
			f.setFecha(LocalDate.of(2019, 10, 6));
			f.setDescripcion("Dia festivo por domingo");
			f.setCentro(c);
			f.setReservashabilitadas(false);
			fservicio.save(f);
			
			Festivo f2 = new Festivo();
			f2.setFecha(LocalDate.of(2019, 10, 13));
			f2.setDescripcion("Dia festivo por domingo");
			f2.setCentro(c);
			f2.setReservashabilitadas(false);
			fservicio.save(f2);

			for (LocalTime hora : rservicio.getHorarios()) {
				System.out.println(hora);
			}

			// Cifrar contraseña de los usuarios en el data sql
			List<Usuario> lista = servicio.findAll();

			for (Usuario user : lista) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				servicio.save(user);
			}
		};

	}
}