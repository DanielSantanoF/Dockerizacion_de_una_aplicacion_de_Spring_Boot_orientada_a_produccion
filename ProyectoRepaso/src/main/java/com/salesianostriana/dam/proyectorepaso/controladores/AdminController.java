package com.salesianostriana.dam.proyectorepaso.controladores;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.proyectorepaso.model.Espacio;
import com.salesianostriana.dam.proyectorepaso.model.Pager;
import com.salesianostriana.dam.proyectorepaso.model.Usuario;
import com.salesianostriana.dam.proyectorepaso.servicios.CentroServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.EspacioServicio;
import com.salesianostriana.dam.proyectorepaso.servicios.UsuarioServicio;

/**
 * 
 * @author jallamas Clase para controlar las peticiones del administrador
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	private UsuarioServicio usuarioServicio;
	private CentroServicio centroServicio;
	private EspacioServicio espacioServicio;
	private BCryptPasswordEncoder passwordEncoder;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 2;
	private static final int[] PAGE_SIZES = { 2, 4, 8, 16 };

	/**
	 * @param usuarioServicio
	 */
	public AdminController(UsuarioServicio usuarioServicio, CentroServicio centroServicio,
			EspacioServicio espacioServicio, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.usuarioServicio = usuarioServicio;
		this.centroServicio = centroServicio;
		this.espacioServicio = espacioServicio;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Método que muestra al administrador del centro una lista de todas las altas
	 * pendientes de confirmar que haya.
	 * 
	 * @param model Modelo que usamos para pasar los datos a la plantilla
	 * @return Plantilla html que pinta la lista
	 */
	@GetMapping({ "/listarTodosPendiente" })
	public String listarTodosRegistroPendiente(Model model) {
		model.addAttribute("listaPendiente", usuarioServicio.listarUsuariosPendientes());
		return "admin/listausuariospendiente";
	}

	/**
	 * Método que confirma el registro de un usuario en el sistema.
	 * 
	 * @param id El id del usuario pendiente de confirmar el registro
	 * @return Plantilla que repinta la lista de altas pendientes de validar
	 *         actualizada.
	 */
	@GetMapping("/confirmar/{id}")
	public String confirmarRegistro(@PathVariable("id") long id) {
		usuarioServicio.validarRegistro(usuarioServicio.findById(id));
		return "redirect:/admin/listarTodosPendiente";
	}

	/**
	 * Método que permite al administrador borrar los datos de un usuario
	 * provisional en el caso de que no corresponda confirmar el alta (los datos se
	 * borran definitivamente)
	 * 
	 * @param id el id del usuario provisional
	 * @return Plantilla que repinta la lista de altas pendientes de confirmar
	 *         actualizada.
	 */
	@GetMapping("/borrar/{id}")
	public String borrar(@PathVariable("id") long id) {
		usuarioServicio.deleteById(id);
		return "redirect:/admin/listarTodosPendiente";
	}

	/**
	 * Método que muestra al administrador del centro una lista de todos los
	 * usuarios.
	 * 
	 * @param model Modelo que usamos para pasar los datos a la plantilla
	 * @return Plantilla html que pinta la lista
	 */
	@GetMapping({ "/listarTodosUsuarios" })
	public String listarTodosUsuarios(Model model) {
		model.addAttribute("listaUsuarios", usuarioServicio.listarUsuariosActivos());
		return "admin/listaUsuarios";
	}

	/**
	 * Método para editar los datos de un usuario por parte del administrador (salvo
	 * la contraseña y la fecha de alta)
	 * 
	 * @param id    el id del usuario a editar
	 * @param model Modelo para pasar los datos a la plantilla
	 * @return Plantilla del formulario con los datos a editar
	 */
	@GetMapping("/editarUsuario/{id}")
	public String mostrarFormularioEdicion(@PathVariable("id") long id, Model model) {
		Usuario usuarioEditar = usuarioServicio.findById(id);
		model.addAttribute("usuario", usuarioEditar);
		return "admin/usuario_form_admin_edit";
	}

	/**
	 * Procesa la edición de datos de un usuario por parte del administrador y lo
	 * guarda en la base de datos. Si se hubiera editado el email y el nuevo ya
	 * existiera en otro usuario, se mostrará una pantalla de error.
	 * 
	 * @param hno El objeto Usuario con los datos recogidos del formulario
	 * @return Plantilla que muestra la lista de todos los usuarios.
	 */
	@PostMapping("/editarUsuario/submit")
	public String procesarFormularioEdicion(@ModelAttribute("usuario") Usuario usuario) {
		usuarioServicio.edit(usuario);
		return "redirect:/admin/listarTodosUsuarios";
	}

	/**
	 * Método que cancela la solicitud de baja de un hermano seleccionándolo de la
	 * lista
	 * 
	 * @param id id del hermano
	 * @return Plantilla que pinta la lista de solicitudes de baja actualizada
	 */
	@GetMapping("/desactivarUsuario/{id}")
	public String desactivarUsuario(@PathVariable("id") long id) {
		usuarioServicio.desactivarUsuario(id);
		return "redirect:/admin/listarTodosUsuarios";
	}

	/**
	 * Método para registrar un nuevo usuario por parte del administrador
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/nuevoUsuarioAdmin")
	public String mostrarFormularioRegistro(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("centro", centroServicio.findAll());
		return "admin/usuario_form_admin";
	}

	/**
	 * Procesa la edición de datos de un usuario por parte del administrador y lo
	 * guarda en la base de datos. Si se hubiera editado el email y el nuevo ya
	 * existiera en otro usuario, se mostrará una pantalla de error.
	 * 
	 * @param hno El objeto Usuario con los datos recogidos del formulario
	 * @return Plantilla que muestra la lista de todos los usuarios.
	 */
	@PostMapping("/nuevoUsuarioAdmin/submit")
	public String procesarFormularioRegistro(@ModelAttribute("usuario") Usuario usuario) {
		if (usuarioServicio.findByEmail(usuario.getEmail()).isEmpty()) {
			usuario.setRegistroConfirmado(true);
			usuario.setActivo(true);
			usuario.setFechaalta(LocalDate.now());
			usuario.setPassword(passwordEncoder.encode((usuario.getPassword())));
			usuarioServicio.save(usuario);
			return "redirect:/admin/listarTodosUsuarios";
		} else {
			return "redirect:/usuarioExistente";
		}

	}

	/**
	 * Método que muestra al administrador del centro una lista de todos los
	 * espacios.
	 * 
	 * @param model Modelo que usamos para pasar los datos a la plantilla
	 * @return Plantilla html que pinta la lista
	 */
	@GetMapping({ "/listarTodosEspacios" })
	public String listarTodosEspacios(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, Model model) {

		// Evalúa el tamaño de página. Si el parámetro es "nulo", devuelve
		// el tamaño de página inicial.
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);

		// Calcula qué página se va a mostrar. Si el parámetro es "nulo" o menor
		// que 0, se devuelve el valor inicial. De otro modo, se devuelve el valor
		// del parámetro decrementado en 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Espacio> espacios = espacioServicio.findAllPageable(PageRequest.of(evalPage, evalPageSize));

		// Obtenemos la página definida por evalPage y evalPageSize de objetos de
		// nuestro modelo
		// Page<Producto> products =
		// productService.findAllPageable(PageRequest.of(evalPage, evalPageSize));
		// Creamos el objeto Pager (paginador) indicando los valores correspondientes.
		// Este sirve para que la plantilla sepa cuantas páginas hay en total, cuantos
		// botones
		// debe mostrar y cuál es el número de objetos a dibujar.
		Pager pager = new Pager(espacios.getTotalPages(), espacios.getNumber(), BUTTONS_TO_SHOW);

		model.addAttribute("espacios", espacios);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		return "admin/listaEspacios";
	}

	/**
	 * Método para registrar un nuevo usuario por parte del administrador
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/nuevoEspacio")
	public String nuevoEspacioForm(Model model) {
		model.addAttribute("espacio", new Espacio());
		model.addAttribute("centro", centroServicio.findAll());
		return "admin/espacio_form";
	}

	/**
	 * Procesa la edición de datos de un usuario por parte del administrador y lo
	 * guarda en la base de datos. Si se hubiera editado el email y el nuevo ya
	 * existiera en otro usuario, se mostrará una pantalla de error.
	 * 
	 * @param hno El objeto Usuario con los datos recogidos del formulario
	 * @return Plantilla que muestra la lista de todos los usuarios.
	 */
	@PostMapping("/nuevoEspacio/submit")
	public String procesarNuevoEspacioForm(@ModelAttribute("espacio") Espacio espacio) {
		espacioServicio.save(espacio);
		return "redirect:/admin/listarTodosEspacios";
	}

	/**
	 * Método para editar los datos de un espacio por parte del administrador (salvo
	 * el centro en el que está)
	 * 
	 * @param id    el id del espacio a editar
	 * @param model Modelo para pasar los datos a la plantilla
	 * @return Plantilla del formulario con los datos a editar
	 */
	@GetMapping("/editarEspacio/{id}")
	public String mostrarFormularioEdicionEspacio(@PathVariable("id") long id, Model model) {
		Espacio espacioEditar = espacioServicio.findById(id);
		model.addAttribute("espacio", espacioEditar);
		return "admin/espacio_form_edit";
	}

	/**
	 * Procesa la edición de datos de un espacio por parte del administrador y lo
	 * guarda en la base de datos.
	 * 
	 * @param hno El objeto Espacio con los datos recogidos del formulario
	 * @return Plantilla que muestra la lista de todos los usuarios.
	 */
	@PostMapping("/editarEspacio/submit")
	public String procesarFormularioEdicionEspacio(@ModelAttribute("espacio") Espacio espacio) {
		espacioServicio.edit(espacio);
		return "redirect:/admin/listarTodosEspacios";
	}

	/**
	 * Método que permite al administrador borrar los datos de un espacio (los datos
	 * se borran definitivamente)
	 * 
	 * @param id el id del espacio
	 * @return Plantilla que repinta la lista espacios actualizada.
	 */
	@GetMapping("/borrarEspacio/{id}")
	public String borrarEspacio(@PathVariable("id") long id) {
		espacioServicio.deleteById(id);
		return "redirect:/admin/listarTodosEspacios";
	}

	/**
	 * Método que muestra al administrador del centro una lista de todos los
	 * usuarios que hayan sido desactivados.
	 * 
	 * @param model Modelo que usamos para pasar los datos a la plantilla
	 * @return Plantilla html que pinta la lista
	 */
	@GetMapping({ "/listarTodosInactivos" })
	public String listarTodosDesactivados(Model model) {
		model.addAttribute("listaInactivos", usuarioServicio.listarUsuariosInactivos());
		return "admin/listaUsuariosInactivos";
	}

	/**
	 * Método que reactiva un usuario en el sistema.
	 * 
	 * @param id El id del usuario a reactivar
	 * @return Plantilla que lista los usuarios inactivos.
	 */
	@GetMapping("/activar/{id}")
	public String activarUsuario(@PathVariable("id") long id) {
		usuarioServicio.activarUsuario(usuarioServicio.findById(id));
		return "redirect:/admin/listarTodosInactivos";
	}
}
