package com.salesianostriana.dam.proyectorepaso.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.proyectorepaso.model.Reserva;
import com.salesianostriana.dam.proyectorepaso.servicios.ReservaServicio;


/**
 * 
 * @author Daniel Santano Fernández
 * Clase para controlar las peticiones del administrador respecto a las reservas de los usuarios
 */

@Controller
@RequestMapping("/admin")
public class AdminReservasUsuariosController {

	private ReservaServicio reservaServicio;

	/**
	 * Contrructor de la clase
	 * @param usuarioServicio
	 */
	public AdminReservasUsuariosController(ReservaServicio reservaServicio) {
		super();
		this.reservaServicio = reservaServicio;
	}
	
	/**
	 * Metodo que muestra las reservas de todos los usuarios
	 * @param model Modelo que usamos para pasar las reservas
	 * @return view
	 */
	@GetMapping({ "/reservasUsuarios" })
	public String listarTodos(Model model) {
		model.addAttribute("reservasUsuarios", reservaServicio.findAll());
		return "admin/ReservasUsuarios";
	}
	
	/**
	 * Metodo para borrar una reserva
	 * @param id id de la reserva que se usa para eliminarla
	 * @return view
	 */
	@GetMapping("/borrarReservaUsuario/{id}")
	public String borrar(@PathVariable("id") long id) {
		reservaServicio.deleteById(id);
		return "redirect:/admin/reservasUsuarios";
	}
	
	/**
	 * Metodo para ver las reservas de un usuario en especifico
	 * @param id id del usuario
	 * @param model las reservas encontradas por el id de usuario
	 * @return view
	 */
	@GetMapping({ "/reservaUsuarioEspecifico/{id}" })
	public String listarReservasEspecificas(@PathVariable("id") long id,Model model) {
		//model.addAttribute("reservasUsuarios", reservaServicio.findAll());
		List<Reserva> r = reservaServicio.buscarPorUsuarioId(id);
		model.addAttribute("reservas", r);
		return "admin/ReservasUsuarioEspecifico";
	}
	
	/**
	 * Metodo para borrar una reserva de un usuario especifico
	 * @param id id del usuario
	 * @return view
	 */
	@GetMapping({ "/borrarReservaUsuarioEspecifico/{id}" })
	public String borrarListarReservasEspecificas(@PathVariable("id") long id) {
		reservaServicio.deleteById(id);
		return "admin/ReservasUsuarioEspecifico";
	}
	
}
