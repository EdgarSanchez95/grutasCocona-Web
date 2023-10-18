package com.proyecto.cocona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.cocona.modelo.Usuario;



@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    
     Optional<Usuario> findByEmail(String email);
}
