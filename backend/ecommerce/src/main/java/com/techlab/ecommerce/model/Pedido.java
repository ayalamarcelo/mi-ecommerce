package com.techlab.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId = 5L;

    private LocalDateTime fecha = LocalDateTime.now(); 

    private String estado = "PENDIENTE";
    private double total;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<LineaPedido> lineas;
}