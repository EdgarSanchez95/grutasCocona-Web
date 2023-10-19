
package com.proyecto.cocona.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;


/**
 *
 * @author edgar
 */
@Data
@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;

    private Double total;
    
    @ToString.Exclude
    @ManyToOne
    private Usuario usuario;

    @ToString.Exclude
    @OneToMany(mappedBy = "orden") //relacion uno a uno
    private List<DetalleOrden> detalle;
    
    public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecibida, Double total, Usuario usuario,
            List<DetalleOrden> detalle) {
        this.id = id;
        this.numero = numero;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecibida = fechaRecibida;
        this.total = total;
        this.usuario = usuario;
        this.detalle = detalle;
    }

    public Orden(){
    
    }

    

    
}
