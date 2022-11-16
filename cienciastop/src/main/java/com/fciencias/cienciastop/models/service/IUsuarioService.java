package com.fciencias.cienciastop.models.service;

import java.util.List;

import com.fciencias.cienciastop.models.entity.Usuario;

public interface IUsuarioService {
	public List<Usuario> verUsuarios();
	

	public List<Usuario> buscarUsuarioPorNombre(String nombre);

	public Usuario buscarUsuarioPorCorreo(String correo);

	public Usuario buscarUsuarioPorNoCT(Long noCT);
	
	public Usuario guardar(Usuario usuario);
	
	public int borrar(Long noCT);

	public Usuario editar(Usuario usuario);
}
