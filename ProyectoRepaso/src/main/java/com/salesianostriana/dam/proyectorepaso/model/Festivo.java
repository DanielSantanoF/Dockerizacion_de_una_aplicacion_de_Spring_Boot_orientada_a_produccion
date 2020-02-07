/**
 * 
 */
package com.salesianostriana.dam.proyectorepaso.model;

import java.time.LocalDate;

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
public class Festivo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	private boolean reservashabilitadas;

	private String descripcion;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Centro centro;

	/**
	 * Constructor con todos los parámetros
	 * 
	 * @param id                  Id del día festivo
	 * @param fecha               Fecha de la fiesta
	 * @param reservashabilitadas Booleano que nos permite habilitar un festivo para
	 *                            hacer reservas.
	 * @param descripción         Descripción del día festivo
	 * @param centro              Centro en el que esa fecha es festivo
	 */
	public Festivo(long id, LocalDate fecha, boolean reservashabilitadas, String descripcion, Centro centro) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.reservashabilitadas = reservashabilitadas;
		this.descripcion = descripcion;
		this.centro = centro;
	}

}
