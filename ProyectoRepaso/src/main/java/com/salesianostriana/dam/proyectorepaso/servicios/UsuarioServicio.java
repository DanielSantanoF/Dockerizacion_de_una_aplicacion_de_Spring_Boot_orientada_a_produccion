package com.salesianostriana.dam.proyectorepaso.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.repositorios.UsuarioRepositorio;

/**
 * @author Daniel Santano Fernández Servicio de usuario
 */

@Service
public class UsuarioServicio extends ServicioBase<Usuario, Long, UsuarioRepositorio> {

	/**
	 * Atributos necesarios para la clase
	 */

	BCryptPasswordEncoder passwordEncoder;
	UsuarioRepositorio usuarioRepository;

	/**
	 * Metodo para buscar por email en usuarios
	 * 
	 * @param email email a buscar
	 * @return email buscado
	 */
	public Usuario buscarPorEmail(String email) {
		return repositorio.findFirstByEmail(email);
	}

	/**
	 * Método para buscar la lista de usuario con un email
	 * 
	 * @param email
	 * @return
	 */
	public List<Usuario> findByEmail(String email) {
		return repositorio.findByEmail(email);
	}

	/**
	 * Metodo para cifrar la contraseña
	 * 
	 * @param password contraseña a cifrar
	 * @return contraseña cifrada
	 */
	public String cifrarPassword(String password) {
		return passwordEncoder.encode(password);

	}

	/**
	 * Método para validar el alta de un usuario
	 * 
	 * @param usuario Usuario a validar
	 */
	public void validarRegistro(Usuario usuario) {
		usuario.setRegistroConfirmado(true);
		usuario.setFechaalta(LocalDate.now());
		edit(usuario);
	}

	/**
	 * Método para listar los usuarios registrados pendientes de validación
	 * 
	 * @return La lista de usuarios pendientes
	 */
	public List<Usuario> listarUsuariosPendientes() {
		return repositorio.findByRegistroConfirmadoFalse();
	}

	/**
	 * Método para listar los usuarios activos en el sistema
	 * 
	 * @return
	 */
	public List<Usuario> listarUsuariosActivos() {
		return repositorio.findByRegistroConfirmadoTrueAndActivoTrue();
	}

	/**
	 * Método para desactivar un usuario al darlo de baja
	 * 
	 * @param id
	 */
	public void desactivarUsuario(Long id) {
		Usuario usuario;
		usuario = this.findById(id);
		usuario.setActivo(false);
		edit(usuario);
	}

	/**
	 * Método para listar los usuarios desactivados en el sistema
	 * 
	 * @return
	 */
	public List<Usuario> listarUsuariosInactivos() {
		return repositorio.findByActivoFalse();
	}

	/**
	 * Método para reactivar un usuario
	 * 
	 * @param id
	 */
	public void activarUsuario(Usuario usuario) {
		usuario.setActivo(true);
		edit(usuario);
	}
}
