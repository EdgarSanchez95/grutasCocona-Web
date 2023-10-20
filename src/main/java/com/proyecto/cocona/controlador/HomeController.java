package com.proyecto.cocona.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.cocona.modelo.DetalleOrden;
import com.proyecto.cocona.modelo.Orden;
import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.modelo.Usuario;
import com.proyecto.cocona.servicio.IDetalleOrdenServicio;
import com.proyecto.cocona.servicio.IOrdenServicio;
import com.proyecto.cocona.servicio.IUsuarioServicio;
import com.proyecto.cocona.servicio.ProductoServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

     @Autowired
     private ProductoServicio productoServicio;

     @Autowired
     private IUsuarioServicio usuarioServicio;

     @Autowired
     private IOrdenServicio ordenServicio;

     @Autowired
     private IDetalleOrdenServicio detalleOrdenServicio;

     //para almacenar los detalles de la orden
     List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

     //datos de la orden
     Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session){
       
        log.info("Sesion del usuario: {}", session.getAttribute("idusuario"));

        model.addAttribute("productos", productoServicio.findAll());

        //session
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model){

        log.info("id producto enviado como parametro {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoServicio.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);
        
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){ //metodo para redireccionar al carrito
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();        
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoServicio.get(id);
        log.info("producto añadido: {}", optionalProducto.get());
        log.info("cantidad: {}", cantidad);
        producto = optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()* cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añade doble vez

        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId()== idProducto);

        if(!ingresado){
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    //metodo para quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String eliminarProductoCarrito(@PathVariable Integer id, Model model){

        //lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for(DetalleOrden detalleOrden : detalles){
            if(detalleOrden.getProducto().getId()!= id){
                ordenesNueva.add(detalleOrden);
            }
        }

        detalles = ordenesNueva;
        
        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        //session
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        return "/usuario/carrito";
    }

    @GetMapping("/getActividades")
    public String actividades(){
        return "/usuario/actividades";
    }

    @GetMapping("/resumenOrden")
    public String orden(Model model, HttpSession session){

        Usuario usuario = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get(); //id como prueba el valor directo del id se va a cambiar
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorden";
    }

      //guardar la orden
    @GetMapping("/saveOrden")
    public String saveOrden(HttpSession session){

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        // Luego puedes usar sqlDate en tu método setFechaCreacion
        orden.setFechaCreacion(sqlDate);
        orden.setNumero(ordenServicio.generarNumeroOrden());

        //usuario
        Usuario usuario = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        orden.setUsuario(usuario);
        ordenServicio.save(orden);

        //guardar detalles
        for(DetalleOrden dt:detalles){
            dt.setOrden(orden);
            detalleOrdenServicio.save(dt);
        }

        //limpiar lista y orden
        orden = new Orden();
        detalles.clear();
        return "redirect:/";
    }

    @PostMapping("/search")
    public String buscarProducto(@RequestParam String nombre, Model model){
        log.info("Nombre del producto: {}", nombre);
        List<Producto> productos= productoServicio.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "usuario/home";

    }

}
