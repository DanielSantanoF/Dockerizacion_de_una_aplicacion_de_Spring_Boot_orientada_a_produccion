/**
 * 
 */
package com.salesianostriana.dam.proyectorepaso.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	private String username;

	@Column(unique = true)
	private String email;

	private String password;

	private boolean admin;
	private boolean superadmin;
	private boolean activo;
	private boolean registroConfirmado;

	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private LocalDate fechaalta;

	@ManyToOne
	private Centro centro;
	@OneToMany
	private List<Reserva> reserva;

	/**
	 * Constructor con todos los parámetros
	 * 
	 * @param id                 Id del usuario
	 * @param nombre             Nombre y apellidos del usuario
	 * @param email              dirección de correo electrónico
	 * @param password           Contraseña
	 * @param admin              Booleano que indica si es administrador de un
	 *                           centro
	 * @param superadmin         Booleano que indica si es el superadministrador
	 * @param activo             Booleano que indica si el usuario está activo en la
	 *                           aplicación
	 * @param registroConfirmado Booleano que indica si el usuario tiene confirmado
	 *                           el registro en la aplicación
	 * @param fechaalta          Fecha del alta del usuario en el sistema
	 */
	public Usuario(long id, String nombre, String email, String password, boolean admin, boolean superadmin,
			boolean activo, boolean registroConfirmado, LocalDate fechaalta) {
		super();
		this.id = id;
		this.username = nombre;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.superadmin = superadmin;
		this.activo = activo;
		this.registroConfirmado = registroConfirmado;
		this.fechaalta = fechaalta;
	}

	/**
	 * Constructor para las altas de los usuarios de nuevo registro
	 * 
	 * @param id                 Id del usuario
	 * @param nombre             Nombre y apellidos del usuario
	 * @param email              dirección de correo electrónico
	 * @param password           Contraseña
	 * @param admin              Booleano que indica si es administrador de un
	 *                           centro
	 * @param superadmin         Booleano que indica si es el superadministrador
	 * @param activo             Booleano que indica si el usuario está activo en la
	 *                           aplicación
	 * @param registroConfirmado Booleano que indica si el usuario tiene confirmado
	 *                           el registro en la aplicación
	 * @param fechaalta          Fecha del alta del usuario en el sistema
	 */
	public Usuario(long id, String nombre, String email, String password, boolean admin, boolean superadmin,
			boolean activo, boolean registroConfirmado, LocalDate fechaalta, Centro centro) {
		super();
		this.id = id;
		this.username = nombre;
		this.email = email;
		this.password = password;
		this.admin = false;
		this.superadmin = false;
		this.activo = true;
		this.registroConfirmado = false;
		this.fechaalta = LocalDate.now();
		this.centro = centro;
	}

}
