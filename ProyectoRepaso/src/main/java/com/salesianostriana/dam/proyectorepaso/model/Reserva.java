/**
 * 
 */
package com.salesianostriana.dam.proyectorepaso.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author José Antonio Llamas Álvarez
 *
 */

@Data
@NoArgsConstructor
@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	private LocalTime hora;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Espacio espacio;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario usuario;

	/**
	 * Constructor con todos los parámetros
	 * 
	 * @param id      Id de la reserva
	 * @param fecha   Fecha de la reserva
	 * @param hora    Hora de inicio de la reserva (duran una hora)
	 * @param espacio Espacio del que se hace la reserva
	 * @param usuario Usuario que realiza la reserva
	 */
	public Reserva(long id, LocalDate fecha, LocalTime hora, Espacio espacio, Usuario usuario) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.espacio = espacio;
		this.usuario = usuario;
	}

}
