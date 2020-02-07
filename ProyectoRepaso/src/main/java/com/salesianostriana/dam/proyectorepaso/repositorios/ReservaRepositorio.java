package com.salesianostriana.dam.proyectorepaso.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Reserva;

/**
 * @author Daniel Santano Fernández Repositorio de Reserva
 */
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

	/**
	 * Metodo para buscar todas las reservas de un usuario
	 * 
	 * @param id id del ususario
	 * @return usuario buscado
	 */
	public List<Reserva> findByUsuarioId(long id);

	public List<Reserva> findByFechaAndEspacio(LocalDate fecha, Espacio espacio);

}
