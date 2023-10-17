package com.proyecto.cocona.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.modelo.Usuario;
import com.proyecto.cocona.servicio.IUsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    
    @Autowired
    private IUsuarioServicio usuarioServicio;

    // /usuario/registro
    @GetMapping("/registro")
    public String create(){
        return("usuario/registro");
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("usuario registro: {}", usuario);
        usuario.setTipo("USER");
        usuarioServicio.save(usuario);
        return "redirect:/";
        }
    
    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }
}
