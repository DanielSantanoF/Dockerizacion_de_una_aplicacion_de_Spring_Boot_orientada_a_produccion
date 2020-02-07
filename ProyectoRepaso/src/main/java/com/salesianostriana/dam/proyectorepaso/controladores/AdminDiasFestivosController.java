package com.salesianostriana.dam.proyectorepaso.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.proyectorepaso.model.Festivo;
import com.salesianostriana.dam.proyectorepaso.servicios.CentroServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.FestivoServicio;

/**
 * @author Daniel Santano Fernández clase para controlar los dias festicos los cuales gestiona el admin
 */

@Controller
@RequestMapping("/admin")
public class AdminDiasFestivosController {

	/**
	 * Atributos de la clase
	 */
	private CentroServicio centroServicio;
	private FestivoServicio festivoServicio;
	
	/**
	 * Constructor de la clase
	 * @param centroServicio 
	 * @param festivoServicio
	 */
	public AdminDiasFestivosController(CentroServicio centroServicio, FestivoServicio festivoServicio) {
		super();
		this.centroServicio = centroServicio;
		this.festivoServicio = festivoServicio;
	}
	
	/**
	 * Metodo para ver todos los festivos
	 * @param model todos los festivos
	 * @return view
	 */
	@GetMapping("/festivos")
	public String gestionFestivos(Model model) {
		model.addAttribute("diasFestivos", festivoServicio.findAll());
		return "admin/festivos";
	}
	
	/**
	 * Metodo para borrar un festivo por id 
	 * @param id el id del festivo
	 * @return view
	 */
	@GetMapping("/borrarFestivo/{id}")
	public String borrar(@PathVariable("id") long id) {
		festivoServicio.deleteById(id);
		return "redirect:/admin/festivos";
	}
	
	/**
	 * Metodo para añadir un festivo
	 * @param model el festivo a añadir
	 * @return view
	 */
	@GetMapping("/nuevoDiaFestivo")
	public String nuevoDiaFestivo(Model model) {
		model.addAttribute("festivo", new Festivo());
		model.addAttribute("centro", centroServicio.findAll());
		return "admin/nuevoDiaFestivo";
	}
	
	/**
	 * Metodo para guardar el nuevo festivo en la base de datos
	 * @param festivo festivo a guardar
	 * @return view
	 */
	@PostMapping("/nuevoDiaFestivo/sumbit")
	public String procesarNuevoDiaFestivo(@ModelAttribute("festivo") Festivo festivo) {
		festivoServicio.save(festivo);
		return "redirect:/admin/festivos";
	}
	
	/**
	 * Metodo para editar un festivo por id
	 * @param id id del festivo
	 * @param model el festivo a editar
	 * @return view
	 */
	@GetMapping("/editarDiaFestivo/{id}")
	public String mostrarFormularioEdicionEspacio(@PathVariable("id") long id, Model model) {
		Festivo festivoaEditar = festivoServicio.findById(id);
		model.addAttribute("festivo", festivoaEditar);
		model.addAttribute("centro", centroServicio.findAll());
		return "admin/editarDiaFestivo";
	}
	
	/**
	 * Metodo para guardar el festivo con los cambios
	 * @param festivo festivo a guardar
	 * @return view
	 */
	@PostMapping("/editarDiaFestivo/sumbit")
	public String procesarEditarDiaFestivo(@ModelAttribute("festivo") Festivo festivo) {
		festivoServicio.save(festivo);
		return "redirect:/admin/festivos";
	}
}
