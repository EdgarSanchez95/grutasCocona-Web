package com.proyecto.cocona.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.servicio.IOrdenServicio;
import com.proyecto.cocona.servicio.IUsuarioServicio;
import com.proyecto.cocona.servicio.ProductoServicio;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    
    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IOrdenServicio ordenServicio;

    @GetMapping("")
    public String home(Model model){

        List<Producto> productos = productoServicio.findAll();
        model.addAttribute("productos", productos);
        return "administrador/homeAdministrador";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model){
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){
        model.addAttribute("ordenes", ordenServicio.findAll());
        return "administrador/ordenes";
    }
}
