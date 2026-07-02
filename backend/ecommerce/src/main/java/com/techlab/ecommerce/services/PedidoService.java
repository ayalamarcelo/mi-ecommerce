package com.techlab.ecommerce.services;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.model.LineaPedido;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.excepciones.StockInsuficienteException;
import com.techlab.ecommerce.repositories.PedidoRepository;
import com.techlab.ecommerce.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    // Inyección de dependencias por constructor
    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional // Si algo falla, revierte los cambios en la base de datos (Rollback)
    public Pedido registrarPedido(List<LineaPedido> lineas) {
        double totalPedido = 0.0;

        for (LineaPedido linea : lineas) {
            Producto producto = productoRepository.findById(linea.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + linea.getProductoId()));

            // Validación Relacional de Stock
            if (producto.getStock() < linea.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getTitle());
            }

            // Operador Aritmético: Descontar Stock
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);

            // Calcular subtotales
            linea.setPrecioUnitario(producto.getPrice());
            totalPedido += producto.getPrice() * linea.getCantidad();
        }

        Pedido pedido = new Pedido();
        pedido.setLineas(lineas);
        pedido.setTotal(totalPedido);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerHistorialPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
}