package com.salesianostriana.dam.proyectorepaso.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Reserva;

@DataJpaTest
@ActiveProfiles("test")
public class MiguelReservaRepositorioTest {
	
	@Autowired
	private ReservaRepositorio reservaRepositorio;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	
	@Test
	@Sql("sql/sqltest.sql")
	public void testFindByUsuarioId() {
		List<Reserva> result = reservaRepositorio.findByUsuarioId(2);
		assertThat(result).isEmpty();
	}
	
	@Test
	@Sql("sql/sqltest.sql")
	public void testfindByFechaYEspacio() {
		
		Espacio e = new Espacio();
		testEntityManager.persist(e);
		List<Reserva> result = reservaRepositorio.findByFechaAndEspacio(LocalDate.now(),e );
		assertThat(result).isEmpty();
	}
	

}
