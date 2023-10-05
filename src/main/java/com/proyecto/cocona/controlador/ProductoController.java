package com.proyecto.cocona.controlador;


import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.modelo.Usuario;
import com.proyecto.cocona.servicio.ProductoServicio;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("")
    public String show(){
        return "productos/show";  //nos redirige al show.html
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";  //nos redirige al create.html
    } 

    @PostMapping("/save")
    public String save(Producto producto){  //metodo para guardar el producto a la base de datos
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        productoServicio.save(producto);
        return "redirect:/productos";
    }
}
