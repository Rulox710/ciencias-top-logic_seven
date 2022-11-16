package com.fciencias.cienciastop.models.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.cienciastop.models.entity.Producto;
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.models.service.IUsuarioService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")//ciencias-top?
/**
 * Clase controlador de usuario (aka. Administrador)
 * 
 */
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public ResponseEntity<?> verUsuarios() {
		List<Usuario> usuariosActivos = null;
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			usuariosActivos = this.usuarioService.verUsuarios();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (usuariosActivos == null || usuariosActivos.isEmpty()) {
			response.put("mensaje", "No se encontraron usuarios activos en el sistema");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(usuariosActivos,HttpStatus.OK); 
	}
	

	@GetMapping("/usuarios/{}")
	public ResponseEntity<?> buscarUsuarioNombre(@PathVariable String nombre) {
		System.out.println("Buscando usuarios con nombres " + nombre);
		List<Usuario> usuarios;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuarios = usuarioService.buscarUsuarioPorNombre(nombre);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuarios == null) {
			usuarios = new ArrayList<Usuario>();
		}
		
		if (!usuarios.isEmpty()) {
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		mensajeError = "Usuario con nombre" + nombre + " no ha sido encontrado";
		response.put("mensaje", mensajeError);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/usuarios/{correo}")
	public ResponseEntity<?> buscarUsuario(@PathVariable("correo") String correo) {
        System.out.println("Buscando usuario con correo " + correo);
		Usuario usuario= null;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuario = usuarioService.buscarUsuarioPorCorreo(correo);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        if (usuario == null) {
            System.out.println("Usuario con correo " + correo + " no ha sido encontrado");
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

	@GetMapping("/usuarios/{noCT}")
	public ResponseEntity<?> buscarUsuario(@PathVariable("noCT") Long noCT) {
        System.out.println("Buscando usuario con numero de cuenta " + noCT);
		Usuario usuario= null;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuario = usuarioService.buscarUsuarioPorNoCT(noCT);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        if (usuario == null) {
            System.out.println("Usuario con numero de cuenta " + noCT + " no ha sido encontrado");
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
	
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> agregarUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioNuevo = null;
		Map<String,Object> response = new HashMap<>();
		try {
			usuarioNuevo = usuarioService.guardar(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO: handle exception
		}
		/*if(usuarioNuevo == null){
			response.put("mensaje", "El usuario se ha reactivado con éxito. :D")
			response.put("reactivacion", ); //??
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		}*/
		response.put("mensaje", "El usuario se ha creado con éxito. :D");
		response.put("usuario", usuarioNuevo);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/usuarios/{noCT}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarUsuario(@PathVariable Long noCT) {
		usuarioService.borrar(noCT);
	}

	@PutMapping("/usuarios/{noCT}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> editarUsuario(@RequestBody Usuario usuario, @PathVariable Long noCT) {
		Usuario currentUsuario = usuarioService.buscarUsuarioPorNoCT(noCT);
		Usuario usuarioEditado = null;
		Map<String,Object> response = new HashMap<>();
		if (currentUsuario == null) {
			response.put("mensaje", "Error: no se puede editar el usuario con es noCT:".concat(noCT.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			currentUsuario.setNombre(usuario.getNombre());
			currentUsuario.setApellidos(usuario.getApellidos());
			currentUsuario.setRol(usuario.getRol());
			currentUsuario.setTelefono(usuario.getTelefono());
			usuarioEditado = usuarioService.guardar(currentUsuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos.");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha editado correctamente.");
		response.put("usuario",usuarioEditado);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	

}
