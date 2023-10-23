package com.proyecto.cocona.servicio;

import java.util.List;
import java.util.Optional;

import com.proyecto.cocona.modelo.Usuario;

public interface IUsuarioServicio {
    Optional<Usuario> findById(Integer id);

    Usuario save (Usuario usuario);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
}
