package com.salesianostriana.dam.proyectorepaso.seguridad;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.servicios.UsuarioServicio;

/**
 * @author Daniel Santano Fernández
 * Clase que implementa la seguridad para el login de usuarios
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	/**
	 * Lo necesario para la clase servicio en este caso
	 */
	UsuarioServicio usuarioServicio;
	
	/**
	 * Contructor del servicio
	 * @param servicio el servicio de usuario
	 */
	public UserDetailsServiceImpl(UsuarioServicio servicio) {
		this.usuarioServicio = servicio;
	}
	
	/**
	 * Clase para indicar con el boolean de admin el rol del usuario
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioServicio.buscarPorEmail(username);
		
		UserBuilder userBuilder = null;
		
		if (usuario != null) {
			userBuilder = User.withUsername(usuario.getEmail());
			userBuilder.disabled(false);
			userBuilder.password(usuario.getPassword());
			if(usuario.isRegistroConfirmado()) {
				if (usuario.isAdmin()) {
					// Este caso indica que un ADMIN también puede hacer todo lo que hace un USER
					userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));				
				} else {
					userBuilder.authorities(new SimpleGrantedAuthority("ROLE_USER"));				
				}
			}
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		
		return userBuilder.build();
		
		
	}

}