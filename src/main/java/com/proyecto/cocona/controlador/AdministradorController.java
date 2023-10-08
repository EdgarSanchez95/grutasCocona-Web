package com.proyecto.cocona.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.servicio.ProductoServicio;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("")
    public String home(Model model){

        List<Producto> productos = productoServicio.findAll();
        model.addAttribute("productos", productos);
        return "administrador/homeAdministrador";
    }
}
