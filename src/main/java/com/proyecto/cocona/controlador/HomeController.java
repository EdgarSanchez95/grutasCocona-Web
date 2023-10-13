package com.proyecto.cocona.controlador;



import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.cocona.modelo.DetalleOrden;
import com.proyecto.cocona.modelo.Orden;
import com.proyecto.cocona.modelo.Producto;
import com.proyecto.cocona.servicio.ProductoServicio;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

     @Autowired
     private ProductoServicio productoServicio;

     //para almacenar los detalles de la orden
     List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

     //datos de la orden
     Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){
       
        model.addAttribute("productos", productoServicio.findAll());
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
    public String getCart(Model model){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "/usuario/carrito";
    }

    @GetMapping("/resumenOrden")
    public String orden(){
        return "usuario/resumenorden";
    }
}
