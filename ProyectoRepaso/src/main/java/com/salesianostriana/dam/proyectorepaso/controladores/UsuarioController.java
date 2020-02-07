package com.salesianostriana.dam.proyectorepaso.controladores;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.proyectorepaso.model.Reserva;
import com.salesianostriana.dam.proyectorepaso.servicios.EspacioServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.ReservaServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.UsuarioServicio;

/**
 * @author Daniel Santano Fernández y José Antonio Llamas Álvarez. Clase para
 *         controlar las peticiones de los usuarios
 */

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private EspacioServicio espacioServicio;
	@Autowired
	private UsuarioServicio usuarioServicio;
	@Autowired
	private ReservaServicio reservaServicio;

	/**
	 * Manejo de la petición de ver menu de usuario
	 * 
	 * @return view
	 */
	@GetMapping({ "/menu" })
	public String getMenu() {
		return "Menu";
	}

	/**
	 * Controlador que gestiona el listado de espacios para reservar de un usuario
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping({ "/reservar" })
	public String getEspaciosReservar(Model model) {

		model.addAttribute("lista", espacioServicio.findAll());
		return "espaciosReserva";
	}

	/**
	 * Gestiona las reservas de los usuarios
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping({ "/reservas" })

	public String mostrarReservas(Model model, Principal principal) {
		List<Reserva> r = reservaServicio
				.buscarPorUsuarioId(usuarioServicio.buscarPorEmail(principal.getName()).getId());
		model.addAttribute("lista", r);
		return "Reservas";
	}

	/**
	 * Borra las reservas de un usuario
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/borrarReserva/{id}")
	public String borrarReserva(@PathVariable("id") long id) {
		Reserva c = reservaServicio.findById(id);
		c.setFecha(null);
		c.setHora(null);
		reservaServicio.save(c);
		return "redirect:/usuario/reservas"; 
	}

}
