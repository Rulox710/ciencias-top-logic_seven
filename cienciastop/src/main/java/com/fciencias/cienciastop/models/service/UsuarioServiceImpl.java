package com.fciencias.cienciastop.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fciencias.cienciastop.models.dao.IUsuarioDao;
import com.fciencias.cienciastop.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	
	@Transactional(readOnly=true)
	public List<Usuario> verUsuarios() {
		return (List<Usuario>) usuarioDao.encontrarPorStatus(1);
	}

	
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorNoCT(Long noCT) {
		Usuario usuario = usuarioDao.encontrarPorNoCT(noCT);
		if(usuario == null) 
			return null;// excepcion no hay usuario 
		return usuario;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> buscarUsuarioPorNombre(String nombre) {
		List<Usuario> usuario = usuarioDao.encontrarPorNombre(nombre);
		if(usuario == null) 
			return null;// excepcion no hay usuario 
		return usuario;
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorCorreo(String correo) {
		Usuario usuario = usuarioDao.encontrarPorCorreo(correo);
		if(usuario == null) 
			return null;// excepcion no hay usuario 
		return usuario;
	}

	@Override
	
	@Transactional
	public Usuario guardar(Usuario usuario) {
		Usuario usuarioGuardado = usuarioDao.encontrarPorCorreo(usuario.getCorreo());		
		if(usuarioGuardado != null) {
			if(usuarioGuardado.getStatus() == 0) {				
				usuarioDao.activar(usuario.getNoCT());				
				return usuarioDao.encontrarPorNoCT(usuario.getNoCT());
			} else {
				return usuarioGuardado;
			}
		} 
		return usuarioDao.save(usuario);		
	}

	
	public int borrar(Long noCT) {
		Usuario usuarioGuardado = usuarioDao.encontrarPorNoCT(noCT);
		if(usuarioGuardado == null) {
			return 0;
		} else {
			return usuarioDao.desactivar(noCT);
		}
	}

	@Override
	@Transactional
	public Usuario editar(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

}
