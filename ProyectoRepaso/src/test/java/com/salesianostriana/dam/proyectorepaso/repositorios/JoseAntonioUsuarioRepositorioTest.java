package com.salesianostriana.dam.proyectorepaso.repositorios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;



@DataJpaTest
@ActiveProfiles("test")
public class JoseAntonioUsuarioRepositorioTest  {

	@Autowired
	private UsuarioRepositorio uRepositorio;
	
	@Test
	@Sql("sql/sqltest.sql")
	public void findByRegistroConfirmadoFalseTest() {
		List<Usuario> result=uRepositorio.findByRegistroConfirmadoFalse();
		assertThat(result).isNotEmpty();
	}
	
	@Test
	@Sql("sql/sqltest.sql")
	public void findByRegistroConfirmadoTrueAndActivoTrueTest() {
		List<Usuario> result=uRepositorio.findByRegistroConfirmadoTrueAndActivoTrue();
		assertThat(result).isNotEmpty();
	}
	

}
