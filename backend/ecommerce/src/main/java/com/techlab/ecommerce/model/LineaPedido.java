package com.techlab.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lineas_pedido")
@Data
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private int cantidad;
    private double precioUnitario;
}