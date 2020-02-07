package com.salesianostriana.dam.proyectorepaso.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Reserva;
import com.salesianostriana.dam.proyectorepaso.repositorios.ReservaRepositorio;

/**
 * @author Daniel Santano Fernández Servicio de Reserva
 */

@Service
public class ReservaServicio extends ServicioBase<Reserva, Long, ReservaRepositorio> {

	private List<LocalTime> horarios = new ArrayList<LocalTime>(Arrays.asList(LocalTime.of(8, 0), LocalTime.of(9, 0),
			LocalTime.of(10, 0), LocalTime.of(11, 30), LocalTime.of(12, 30), LocalTime.of(13, 30)));

	ReservaRepositorio reservaRepositorio;

	public List<LocalTime> getHorarios() {
		return horarios;
	}

	/**
	 * 
	 * @param id el id del usuario del que queremos sacar todas sus reservas
	 * @return reservas buscadas por id de usuario
	 */
	public List<Reserva> buscarPorUsuarioId(long id) {
		return (List<Reserva>) repositorio.findByUsuarioId(id);
	}

	public List<Reserva> buscarPorFechaYEspacio(LocalDate fecha, Espacio espacio) {
		return (List<Reserva>) repositorio.findByFechaAndEspacio(fecha, espacio);
	}

	public List<LocalTime> buscarHorasDisponibles(List<LocalTime> horarios, LocalDate fecha, Espacio espacio) {
		List<LocalTime> horarios1 = horarios;
		List<LocalTime> horasOcupadas = new ArrayList<>();
		// for (Reserva reserva2 : reservaRepositorio.findByFechaAndEspacio(fecha,
		// espacio)) {
		for (Reserva reserva2 : repositorio.findByFechaAndEspacio(fecha, espacio)) {

			horasOcupadas.add(reserva2.getHora());

		}
		for (LocalTime horas : horasOcupadas) {
			if (horarios1.contains(horas)) {

				horarios1.remove(horas);
			}
		}
		return horarios1;

	}

}
