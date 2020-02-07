package com.salesianostriana.dam.proyectorepaso.controladores;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Festivo;
import com.salesianostriana.dam.proyectorepaso.model.Reserva;
import com.salesianostriana.dam.proyectorepaso.servicios.EspacioServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.FestivoServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.ReservaServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class ReservaController {

	@Autowired
	private UsuarioServicio usuarioServicio;
	@Autowired
	private EspacioServicio espacioServicio;

	@Autowired
	private ReservaServicio reservaServicio;
	@Autowired
	private FestivoServicio festivoServicio;
	
	/**
	 * Controlador que reserva la fecha en un aula específica
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping({ "/reservarespacio/{id}" })
	public String irAReservarAula(@PathVariable("id") Long id, Model model) {

		Reserva reserva = new Reserva();
		Espacio e = espacioServicio.findById(id);
		reserva.setEspacio(e);

		model.addAttribute("horarios", reservaServicio.getHorarios());
		model.addAttribute("nuevaReserva", reserva);

		return "formularioReserva";
	}
	/**
	 * Controlador que reserva la hora de un espacio específico comprobando que no este la hora y la fecha de un espacio determinado ya reservada
	 * @param reserva
	 * @param model
	 * @return
	 */
	@PostMapping("/reservarHora")
	public String reservarHora(@ModelAttribute("nuevaReserva") Reserva reserva, Model model) {

		List<LocalDate> festivos = new ArrayList<>();
		for (Festivo festivo : festivoServicio.findAll()) {

			festivos.add(festivo.getFecha());
		}
		if (festivos.contains(reserva.getFecha())) {
			return "ErrorFestivo";
		} else {
			model.addAttribute("espacio", espacioServicio.findById(reserva.getEspacio().getId()));
			model.addAttribute("horarios", reservaServicio.buscarHorasDisponibles(reservaServicio.getHorarios(),
					reserva.getFecha(), reserva.getEspacio()));
			model.addAttribute("nuevaReserva", reserva);
			return "ReservarHora";
		}
	}
	
	/**
	 * Reserva el aula
	 * @param reserva
	 * @param model
	 * @param principal
	 * @return
	 */
	@PostMapping("/reservarAula")
	public String guardarReserva(@ModelAttribute("nuevaReserva") Reserva reserva, Model model, Principal principal) {

		reserva.setUsuario(usuarioServicio.buscarPorEmail(principal.getName()));

		reservaServicio.save(reserva);

		return "redirect:/usuario/reservas";
	}

}
