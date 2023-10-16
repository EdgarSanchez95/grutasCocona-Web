package com.proyecto.cocona.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.servicio.IUsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private IUsuarioServicio usuarioServicio;

    // /usuario/registro
    @GetMapping("/registro")
    public String create(){
        return("usuario/registro");
    }
}
