package com.proyecto.cocona.servicio;

import java.util.List;

import com.proyecto.cocona.modelo.Orden;

public interface IOrdenServicio {
    List<Orden> findAll();
    Orden save  (Orden orden);
}
