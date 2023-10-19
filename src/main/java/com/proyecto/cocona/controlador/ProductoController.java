package com.proyecto.cocona.controlador;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.modelo.Usuario;
import com.proyecto.cocona.servicio.GuardarImagenServicio;
import com.proyecto.cocona.servicio.IUsuarioServicio;
import com.proyecto.cocona.servicio.ProductoServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Autowired
    private GuardarImagenServicio guardarImagen;

    @GetMapping("")
    public String show(Model model) { // el parametro model lleva la informacion del backend a la vista
        model.addAttribute("productos", productoServicio.findAll());
        return "productos/show"; // nos redirige al show.html
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create"; // nos redirige al create.html
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException { // metodo para
                                                                                                        // guardar el
                                                                                                        // producto a la
                                                                                                        // base de datos
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario u = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(u);

        // imagen
        if (producto.getId() == null) {// cuando se crea un producto

            String nombreImagen = guardarImagen.guardarImagen(file);
            producto.setImagen(nombreImagen);
        } else {

        }
        productoServicio.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}") // este metodo se crea para editar un producto
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServicio.get(id);
        producto = optionalProducto.get();

        LOGGER.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);
        return "productos/edit";
    }

    @PostMapping("/update") // metodo para actualizar el producto
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {

        Producto p = new Producto();
        p = productoServicio.get(producto.getId()).get();

         if (file.isEmpty()) { // cuando editamos el producto pero no cambiamos la imagen
            
            producto.setImagen(p.getImagen());
        } else { // cuando se edita tambien la imagen

        // esta condicion es para eliminar cuando no sea la imagen por defecto
        if (!p.getImagen().equals("default.jpg")) {
            guardarImagen.eliminarImagen(p.getImagen());
        }

            String nombreImagen = guardarImagen.guardarImagen(file);
            producto.setImagen(nombreImagen);
        }

        producto.setUsuario(p.getUsuario());
        productoServicio.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Producto p = new Producto();
        p = productoServicio.get(id).get();

        // esta condicion es para eliminar cuando no sea la imagen por defecto
        if (!p.getImagen().equals("default.jpg")) {
            guardarImagen.eliminarImagen(p.getImagen());
        }
        productoServicio.delete(id);
        return "redirect:/productos";
    }
}
