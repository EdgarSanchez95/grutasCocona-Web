package com.proyecto.cocona.servicio;

import java.util.List;
import java.util.Optional;

import com.proyecto.cocona.modelo.Orden;
import com.proyecto.cocona.modelo.Usuario;

public interface IOrdenServicio {
    List<Orden> findAll();
    Optional<Orden> findById(Integer id);
    Orden save  (Orden orden);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);

}
