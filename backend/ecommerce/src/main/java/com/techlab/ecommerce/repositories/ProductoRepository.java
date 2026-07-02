package com.techlab.ecommerce.repositories;

import com.techlab.ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("all")
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}