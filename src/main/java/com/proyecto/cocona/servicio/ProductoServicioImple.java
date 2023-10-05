package com.proyecto.cocona.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.repository.ProductoRepository;

@Service
public class ProductoServicioImple implements ProductoServicio {

    @Autowired    //sirve para poder inyectar un objeto
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
         productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
    
}