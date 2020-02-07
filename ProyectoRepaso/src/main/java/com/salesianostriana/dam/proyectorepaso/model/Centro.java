/**
 * 
 */
package com.salesianostriana.dam.proyectorepaso.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author José Antonio Llamas Álvarez
 *
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "centro")
public class Centro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	private String nombre;
	private String localidad;
	private String provincia;
	private String ccaa;
	
	@OneToMany
	private List<Usuario> usuarios;
	/**
	 * Constructor con todos los parámetros
	 * 
	 * @param id        Id del centro
	 * @param nombre    Nombre del centro
	 * @param localidad Localidad
	 * @param provincia Provincia
	 * @param ccaa      Comunidad Autónoma
	 */
	public Centro(long id, String nombre, String localidad, String provincia, String ccaa) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localidad = localidad;
		this.provincia = provincia;
		this.ccaa = ccaa;
	}

}
