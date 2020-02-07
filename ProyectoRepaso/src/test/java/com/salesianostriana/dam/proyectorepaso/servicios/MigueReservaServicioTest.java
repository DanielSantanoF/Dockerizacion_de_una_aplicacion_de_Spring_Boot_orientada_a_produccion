package com.salesianostriana.dam.proyectorepaso.servicios;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Reserva;
import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.repositorios.ReservaRepositorio;

@SpringBootTest
public class MigueReservaServicioTest {

	@Mock
	private ReservaRepositorio reservaRepositorio;

	@InjectMocks
	private ReservaServicio reservaServicio;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	private static Usuario usuario = new Usuario(1L, "Miguel", "mlazaro@dam.es", "1234", false, false, true, true,
			LocalDate.now(), null);
	List<LocalTime> horarios = new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8, 0), LocalTime.of(9, 0),
			LocalTime.of(10, 0), LocalTime.of(11, 30), LocalTime.of(12, 30), LocalTime.of(13, 30)));
	private static Espacio e = new Espacio(1, "centro",null, 1, 1);
	private static List<Reserva> reservaUsuario = Arrays.asList(new Reserva(1L, LocalDate.now(), LocalTime.of(9, 0), e, usuario));

	@Test
	public final void testGetHorarios() {

		List<LocalTime> esperado = new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8, 0), LocalTime.of(9, 0),
				LocalTime.of(10, 0), LocalTime.of(11, 30), LocalTime.of(12, 30), LocalTime.of(13, 30)));
		List<LocalTime> resultado = reservaServicio.getHorarios();

		assertEquals(esperado, resultado);
	}

	@Test
	public final void testBuscarPorUsuarioId() {
		
		when(reservaRepositorio.findByUsuarioId(usuario.getId())).thenReturn(reservaUsuario);
		List<Reserva>  esperado = reservaUsuario;
		List<Reserva> resultado = reservaServicio.buscarPorUsuarioId(usuario.getId());
		assertEquals(esperado, resultado);
	}
	
	@Test
	public final void testBuscarPorFechaYEspacio() {
		
	when(reservaRepositorio.findByFechaAndEspacio(LocalDate.now(), e)).thenReturn(reservaUsuario);
		List<Reserva> esperado = reservaUsuario;
		
	List<Reserva> resultado = reservaServicio.buscarPorFechaYEspacio(LocalDate.now(), e);
		assertEquals(esperado, resultado);
	}
	
	@Test
	public final void testBuscarHorasDisponibles() {
		
		when(reservaRepositorio.findByFechaAndEspacio(LocalDate.now(), e)).thenReturn(reservaUsuario);
		List<LocalTime> esperado = new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8, 0),
				LocalTime.of(10, 0), LocalTime.of(11, 30), LocalTime.of(12, 30), LocalTime.of(13, 30)));
		List<LocalTime> resultado = reservaServicio.buscarHorasDisponibles(horarios, LocalDate.now(), e);
		assertEquals(esperado, resultado);
		
	}
}
