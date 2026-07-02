package com.techlab.ecommerce.controllers;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.model.LineaPedido;
import com.techlab.ecommerce.services.PedidoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Endpoint para realizar la compra desde el carrito
    @PostMapping("/pedidos")
    public Pedido crearPedido(@RequestBody List<LineaPedido> lineas) {
        return pedidoService.registrarPedido(lineas);
    }

    // Endpoint para ver el historial de pedidos del escenario (Usuario 5)
    @GetMapping("/usuarios/{id}/pedidos")
    public List<Pedido> obtenerPedidosUsuario(@PathVariable Long id) {
        return pedidoService.obtenerHistorialPorUsuario(id);
    }
}