package com.salesianostriana.dam.proyectorepaso.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;

/**
 * @author Daniel Santano Fernández Repositorio de Usuario
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	/**
	 * Busca a los usuarios primero por el email se usa en el login y en la
	 * seguridad
	 * 
	 * @param email email a buscar
	 * @return email buscado
	 */
	Usuario findFirstByEmail(String email);
	
	

	/**
	 * Busca los usuarios por email
	 * 
	 * @param email
	 * @return
	 */
	List<Usuario> findByEmail(String email);

	/**
	 * Consulta que devuelve una lista de los usuarios pendientes de confirmar
	 * registro.
	 */
	List<Usuario> findByRegistroConfirmadoFalse();

	/**
	 * Consulta que devuelve una lista de usuarios activos con el registro
	 * confirmado
	 */
	List<Usuario> findByRegistroConfirmadoTrueAndActivoTrue();

	/**
	 * Consulta que devuelve una lista de los usuarios desactivados.
	 */
	List<Usuario> findByActivoFalse();
}
