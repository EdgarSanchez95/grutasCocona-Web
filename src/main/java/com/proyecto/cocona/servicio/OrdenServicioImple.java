package com.proyecto.cocona.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.cocona.modelo.Orden;
import com.proyecto.cocona.repository.IOrdenRepository;

@Service
public class OrdenServicioImple implements IOrdenServicio{

    @Autowired
    private IOrdenRepository ordenRepository;
    
    @Override
    public Orden save(Orden orden) {
      
        return ordenRepository.save(orden);
    }
    
}
