package com.proyecto.cocona.servicio;

import java.util.Optional;

import com.proyecto.cocona.modelo.Usuario;

public interface IUsuarioServicio {
    Optional<Usuario> findById(Integer id);
}