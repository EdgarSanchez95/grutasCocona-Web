package com.proyecto.cocona.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.cocona.modelo.DetalleOrden;
import com.proyecto.cocona.repository.IDetalleOrdenRepository;

@Service
public class DetalleOrdenImple implements IDetalleOrdenServicio{

    @Autowired
    private IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
       return detalleOrdenRepository.save(detalleOrden);
    }
    
}
