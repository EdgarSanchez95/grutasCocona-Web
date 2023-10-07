package com.proyecto.cocona.servicio;

import java.util.List;
import java.util.Optional;

import com.proyecto.cocona.modelo.Producto;

//en esta interfaz se define todos los metodos crud para la clase producto

public interface ProductoServicio {

    public Producto save(Producto producto);

    public Optional<Producto> get (Integer id);

    public void update(Producto producto);

    public void delete(Integer id);

    public List<Producto> findAll(); //metodo para mostrar la lista de productos

}
