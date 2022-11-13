package com.fciencias.cienciastop.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fciencias.cienciastop.models.entity.Usuario;

@Repository
public interface IUsuarioDao extends CrudRepository<Usuario, Integer> {
	
	@Query(value= "SELECT * FROM usuarios WHERE status = :status", nativeQuery = true)
	List<Usuario> encontrarPorStatus(@Param("status") Integer status);
	
	@Query(value= "SELECT * FROM usuarios WHERE noCT = :noCT AND status = 1", nativeQuery = true)
	Usuario encontrarPorNoCT(@Param("noCT") Integer noCT);
	
	@Query(value= "SELECT * FROM usuarios WHERE correo = :correo", nativeQuery = true)
	Usuario encontrarPorCorreo(@Param("correo") String correo);

	@Query(value= "SELECT * FROM usuarios WHERE nombre = :nombre", nativeQuery = true)
	Usuario encontrarPorNombre(@Param("nombre") String nombre);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE usuarios SET status = 1 WHERE noCT = :noCT", nativeQuery = true)
	Integer activar(@Param("noCT") Integer noCT);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE usuarios SET status = 0 WHERE noCT = :noCT", nativeQuery = true)
	Integer desactivar(@Param("noCT") Integer noCT);
}
