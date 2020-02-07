package com.salesianostriana.dam.proyectorepaso.servicios;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.repositorios.EspacioRepositorio;

/**
 * @author Daniel Santano Fernández Tests de algunas funciones del Servicio de Usuario
 */

@ExtendWith(MockitoExtension.class)
public class DanielSantanoEspacioServicioTests {

	@InjectMocks
	private EspacioServicio espacioServicio;
	
	@Mock
	private EspacioRepositorio espacioRepositorio;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findAllPageableTest() {
		int INITIAL_PAGE = 0;
		int INITIAL_PAGE_SIZE = 2;
		PageRequest pageable = PageRequest.of(INITIAL_PAGE, INITIAL_PAGE_SIZE);
		List<Espacio> lista = new ArrayList<Espacio>();
		Espacio e = new Espacio(123123, "EspacioTest", null, 45, 20);
		Espacio e2 = new Espacio(321321, "EspacioTest2", null, 50, 23);
		lista.add(e);
		lista.add(e2);
		Page<Espacio> listPage = new PageImpl<>(lista, pageable, lista.size());
		when(espacioRepositorio.findAll(pageable)).thenReturn(listPage);
	    Page<Espacio> result = espacioServicio.findAllPageable(pageable);
	    assertNotEquals(result, null);
	}
	
	
}
