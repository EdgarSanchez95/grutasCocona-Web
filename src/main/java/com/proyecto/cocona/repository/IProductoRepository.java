/*se crea una interfaz para hacer el crud de los productos */

package com.proyecto.cocona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.cocona.modelo.Producto;

@Repository                 //la interfaz extiende a JpaRepository para la clase producto
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
    
}
