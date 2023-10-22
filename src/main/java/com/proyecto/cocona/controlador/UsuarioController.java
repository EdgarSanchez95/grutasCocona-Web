package com.proyecto.cocona.controlador;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.cocona.modelo.Orden;
import com.proyecto.cocona.modelo.Usuario;
import com.proyecto.cocona.servicio.IOrdenServicio;
import com.proyecto.cocona.servicio.IUsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    
    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private IOrdenServicio ordenServicio;

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

    @PostMapping("/acceder")
    public String acceder(Usuario usuario,HttpSession session){
        logger.info("accesos: {}", usuario);

        Optional<Usuario> user = usuarioServicio.findByEmail(usuario.getEmail());
        logger.info("usuario db: {}", user.get());

          if(user.isPresent()){
            session.setAttribute("idusuario", user.get().getId());
            if(user.get().getTipo().equals("admin")){
                return "redirect:/administrador";
            }
            else{
                return "redirect:/";
            }
        }
        else{
            logger.info("Usuario no existe");
        }
        return "redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session ){
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        Usuario usuario = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        List<Orden> ordenes = ordenServicio.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model){
        logger.info("id de la orden: {}", id);
        Optional<Orden> orden = ordenServicio.findById(id);

        model.addAttribute("detalles", orden.get().getDetalle());
        //session
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){
        session.removeAttribute("idusuario");
        return "redirect:/";
    }
}
