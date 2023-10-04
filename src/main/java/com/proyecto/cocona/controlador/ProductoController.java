package com.proyecto.cocona.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    @GetMapping("")
    public String show(){
        return "productos/show";  //nos redirige al show.html
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";  //nos redirige al create.html
    } 
}
