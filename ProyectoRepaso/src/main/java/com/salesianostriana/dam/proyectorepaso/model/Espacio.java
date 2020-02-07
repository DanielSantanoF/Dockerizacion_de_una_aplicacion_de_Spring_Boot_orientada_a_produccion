/**
 * 
 */
package com.salesianostriana.dam.proyectorepaso.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author José Antonio Llamas Álvarez
 *
 */

@Data
@NoArgsConstructor
@Entity
public class Espacio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String nombre;
	private Integer alumnos;
	private Integer puestos;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Centro centro;

	/**
	 * Constructor con todos los parámetros
	 * 
	 * @param id     Id del Espacio
	 * @param nombre Nombre del espacio
	 * @param centro Centro en el que se encuentra el espacio
	 * @param alumnos Alumnos de la clase
	 * @param puestos Puestos de la clase
	 */
	public Espacio(long id, String nombre, Centro centro, Integer alumnos, Integer puestos) {
		super();
		this.alumnos = alumnos;
		this.puestos = puestos;
		this.id = id;
		this.nombre = nombre;
		this.centro = centro;
	}

}
