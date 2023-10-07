package com.proyecto.cocona.controlador;


import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String show(Model model){ //el parametro model lleva la informacion del backend a la vista
        model.addAttribute("productos", productoServicio.findAll());
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

    @GetMapping("/edit/{id}")     //este metodo se crea para editar un producto
    public String edit(@PathVariable Integer id, Model model){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServicio.get(id);
        producto = optionalProducto.get();

        LOGGER.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);
        return "productos/edit";
    }

    @PostMapping("/update")    //metodo para actualizar el producto
    public String update(Producto producto){

        productoServicio.update(producto);
        return "redirect:/productos";
    }
}
