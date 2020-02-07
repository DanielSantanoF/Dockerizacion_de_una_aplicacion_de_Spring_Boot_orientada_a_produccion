package com.salesianostriana.dam.proyectorepaso.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.repositorios.UsuarioRepositorio;

@SpringBootTest
public class JoseLuisusuarioServicioTest {
	
	@InjectMocks
	private UsuarioServicio usuarioServicio;

	@Mock
	private UsuarioRepositorio usarioRepositorio;

	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	private static Usuario usuario = new Usuario(1L, "JoseLuis", "JoseLuis@dam.es", "1234", false, false, false, false,
			LocalDate.now(), null);
	private static List<Usuario> listaUsuario = Arrays.asList(
			usuario,
			new Usuario(1L, "Miguel", "mlazaro@dam.es", "1234", false, false, true, true,LocalDate.now(), null));
	
	
	@Test
	public void testBuscarPorEmail() {
		when(usarioRepositorio.findFirstByEmail(usuario.getEmail())).thenReturn(usuario);
		Usuario esperado = usuario;
		Usuario resultado = usuarioServicio.buscarPorEmail("JoseLuis@dam.es");
		
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testBuscarPorEmailNoEncontrado() {
		when(usarioRepositorio.findFirstByEmail(usuario.getEmail())).thenReturn(usuario);
		Usuario resultado = usuarioServicio.buscarPorEmail("pedro@comercio.es");
		
		assertNull(resultado);
	}
	
	@Test
	public void testFindByEmail() {
		when(usarioRepositorio.findByEmail(usuario.getEmail())).thenReturn(listaUsuario);
		List<Usuario> esperado = listaUsuario;
		List<Usuario> resultado = usuarioServicio.findByEmail("JoseLuis@dam.es");
		
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testFindByEmailNoEncontrado() {
		when(usarioRepositorio.findByEmail(usuario.getEmail())).thenReturn(listaUsuario);
		List<Usuario> resultado = usuarioServicio.findByEmail("pedro@comercio.es");
		
		assertTrue(resultado.isEmpty());
		//assertThat(resultado.size(), is(0));
	}
	
	@Test
	public void testListarUsuariosPendientes() {
		when(usarioRepositorio.findByRegistroConfirmadoFalse()).thenReturn(listaUsuario);
		List<Usuario> esperado = listaUsuario;
		List<Usuario> resultado = usuarioServicio.listarUsuariosPendientes();
		
		//assertThat(resultado.size(), is(1));
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testListarUsuariosActivos() {
		when(usarioRepositorio.findByRegistroConfirmadoTrueAndActivoTrue()).thenReturn(listaUsuario);
		List<Usuario> esperado = listaUsuario;
		List<Usuario> resultado = usuarioServicio.listarUsuariosActivos();
		
		//assertThat(resultado.size(), is(1));
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testListarUsuariosInactivos() {
		when(usarioRepositorio.findByActivoFalse()).thenReturn(listaUsuario);
		List<Usuario> esperado = listaUsuario;
		List<Usuario> resultado = usuarioServicio.listarUsuariosInactivos();
		
		//assertThat(resultado.size(), is(1));
		assertEquals(esperado, resultado);
	}
	
	@Test
	public void testActivarUsuario() {
		
		Usuario us = new Usuario(1L, "Jose", "Jose@dam.es", "1234", false, false, false, false,
				LocalDate.now(), null);
		usuarioServicio.activarUsuario(us);
		assertTrue(us.isActivo());
	}
}