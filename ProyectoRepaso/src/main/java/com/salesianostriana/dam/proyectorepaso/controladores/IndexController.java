package com.salesianostriana.dam.proyectorepaso.controladores;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.servicios.CentroServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.UsuarioServicio;

/**
 * @author Daniel Santano Fernández Clase para controlar las peticiones con las
 *         que no necesitas estar registrado
 */

@Controller
public class IndexController {

	/**
	 * Atributos necesarios para el controlador
	 */
	private UsuarioServicio usuarioServicio;
	private CentroServicio centroServicio;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * Constructor del controlador
	 * 
	 * @param usuarioServicio UsuarioServicio
	 */
	public IndexController(UsuarioServicio usuarioServicio, CentroServicio centroServicio) {
		super();
		this.usuarioServicio = usuarioServicio;
		this.centroServicio = centroServicio;
	}

	/**
	 * Manejo de la petición de ver el login
	 * 
	 * @return view
	 */
	@GetMapping({ "/", "/login" })
	public String getLogin() {
		return "Login";
	}

	/**
	 * Manejo de la petición de regístrarte
	 * 
	 * @param model model para el usuario
	 * @return view
	 */
	@GetMapping("/registrarse")
	public String mostrarFormulario(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("centro", centroServicio.findAll());
		return "Registrarse";
	}

	/**
	 * Manejo de la petición de enviar el registro
	 * 
	 * @param u usuario a guardar en la bd
	 * @return view
	 */
	@PostMapping("/registrarse/submit")
	public String procesarFormulario(@ModelAttribute("usuario") Usuario u) {
		if (usuarioServicio.findByEmail(u.getEmail()).isEmpty()) {

			u.setPassword(passwordEncoder.encode(u.getPassword()));
			u.setRegistroConfirmado(false);
			u.setActivo(true);
			u.setAdmin(false);
			u.setSuperadmin(false);
			usuarioServicio.save(u);
			return "redirect:/login";
		} else {
			return "redirect:/usuarioExistente";
		}

	}

	/**
	 * Mostrar pantalla de usuario existente
	 * 
	 * @return view
	 */
	@GetMapping({ "/usuarioExistente" })
	public String getUsuarioExistente() {
		return "usuarioExistente";
	}
}
