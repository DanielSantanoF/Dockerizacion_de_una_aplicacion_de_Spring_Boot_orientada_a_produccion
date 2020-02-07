package com.salesianostriana.dam.proyectorepaso.repositorios;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;

/**
 * @author Daniel Santano Fernández Tests de algunas funciones del Repositorio de Usuario
 */

@DataJpaTest
@ActiveProfiles("test")
public class DanielSantanoUsuarioRepositorioTest {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Test
	@Sql("sql/sqltest.sql")
	public void findFirstByEmailTest() {
		String email = "santano@yo.es";
		Usuario result = usuarioRepositorio.findFirstByEmail(email);
		assertEquals(email, result.getEmail());
	}
	
	@Test
	@Sql("sql/sqltest.sql")
	public void findByEmailTest() {
		String email = "santano@yo.es";
		List<Usuario> result = usuarioRepositorio.findByEmail(email);
		assertEquals(email, result.get(0).getEmail());
	}
	
	@Test
	@Sql("sql/sqltest.sql")
	public void findByActivoFalseTest() {
		List<Usuario> result = usuarioRepositorio.findByActivoFalse();
		assertNotEquals(result, null);
	}
	
}
