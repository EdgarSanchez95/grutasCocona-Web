package com.proyecto.cocona.controlador;


import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.modelo.Producto;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
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
        return "redirect:/productos";
    }
}
