package com.proyecto.cocona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.cocona.modelo.Orden;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{
    
}
