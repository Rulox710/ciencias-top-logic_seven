package com.fciencias.cienciastop.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fciencias.cienciastop.models.dao.IRentaDao;
import com.fciencias.cienciastop.models.entity.Renta;

@Service
public class RentaServiceImpl implements IRentaService {

	@Autowired
	private IRentaDao rentaDao;
	
	@Override
	public List<Renta> findAll() {
		return (List<Renta>) rentaDao.findAll();
		
	}
	
	@Transactional(readOnly=true)
	public List<Renta> verRentas() {
		return (List<Renta>) rentaDao.encontrarPorStatus(false);
	}
	
	@Override
	public Renta findByID(Long id) {
		return rentaDao.findById(id).orElse(null);
	}

	@Override
	public Renta save(Renta renta) {
		return rentaDao.save(renta);
	}

	@Override
	public void delete(Long id) {
		rentaDao.deleteById(id);

	}

	/**
	 * Regresa la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 * @return la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Object[]> topFiveConMasRentas() {
		return rentaDao.topFiveConMasRentas();
	}

	/**
	 * Regresa la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 * @return la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Object[]> topFiveMasRentados() {
		return rentaDao.topFiveMasRentados();
	}

}